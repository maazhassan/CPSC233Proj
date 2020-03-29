package Launcher;

import Screens.GameScreen;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * The JavaFX implementation follows the MVC pattern.
 * <br />
 * The MainGame class represents the model of the MVC. It manages the internal game state and logic.
 * <br />
 * This class represents the View of the MVC. It handles view changes, button presses, and anything related to the
 * change of the view.
 * <br />
 * The JavaFXController acts as a liaison between the View and the Model together to let the game running. The
 * Game (Model) changes will invoke the Controller, which will then pass over the changes to the View. Any changes
 * to the View will pass to the Controller, which will then update the game state.
 */

public class JavaFXApp {
    public static final int SIZE = 512;

    private JavaFXController controller;
    private char p1Color;
    private char p2Type;
    private int aiDifficulty;
    private boolean loadingGame;

    private Label status;
    private TextArea log;
    private TextArea leftMoveLog;
    private TextArea rightMoveLog;
    private Stage primaryStage = new Stage();
    private Canvas canvas = new Canvas(SIZE, SIZE); // The main canvas object that we'll be using to draw images

    private AnimationTimer timer;

    /**
     * Creates an instance of JavaFXApp.
     * @param p1Color The color of player 1.
     * @param p2Type The type of player 2.
     * @param aiDifficulty The difficulty of the AI.
     */

    public JavaFXApp(char p1Color, char p2Type, int aiDifficulty, boolean loadingGame) {
        this.p1Color = p1Color;
        this.p2Type = p2Type;
        this.aiDifficulty = aiDifficulty;
        this.loadingGame = loadingGame;
    }

    /**
     * Intitializes and runs the app. Creates the controller used to interact with 
     * the MainGame instance currently in play. Also creates the main app window.
     */

    public void run() {
        VBox root = new VBox();
        Scene scene = new Scene(root); // No idea what this is for. JavaFX needs it

        //Handlers for when mouse hovers a button
		EventHandler<MouseEvent> enterEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				scene.setCursor(Cursor.HAND);
			}
		};

		EventHandler<MouseEvent> exitEvent = new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mouseEvent) {
				scene.setCursor(Cursor.DEFAULT);
			}
		};

        // Body

        // The main container that splits the left canvas panel with the right log panel
        HBox body = new HBox();
        VBox rightPanel = new VBox();

        status = new Label();
        log = new TextArea();
        log.setPrefWidth(330);
        log.setPrefHeight(312);

        leftMoveLog = new TextArea();
        leftMoveLog.setPrefWidth(165);
        leftMoveLog.setPrefHeight(155);

        rightMoveLog = new TextArea();
        rightMoveLog.setPrefWidth(165);
        rightMoveLog.setPrefHeight(155);

        HBox moveLog = new HBox(leftMoveLog, rightMoveLog);

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.getActiveScreen().onMouseEvent(canvas.getGraphicsContext2D(), mouseEvent);
            }
        });

        canvas.setOnMouseEntered(enterEvent);
        canvas.setOnMouseExited(exitEvent);

        status.setFont(new Font(30.0));
        log.setEditable(false);
        leftMoveLog.setEditable(false);
        rightMoveLog.setEditable(false);

        rightPanel.getChildren().addAll(status, log, moveLog);

        body.getChildren().add(canvas);
        body.getChildren().add(rightPanel);

        root.getChildren().add(createToolbar(enterEvent, exitEvent));
        root.getChildren().add(body);

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller = new JavaFXController(this, p1Color, p2Type, aiDifficulty);
        if (loadingGame) controller.getCurrentGame().loadGame();
        controller.setActiveScreen(new GameScreen(controller));
        startGameLoop(canvas.getGraphicsContext2D());
    }

    /**
     * Creates the toolbar displayed at the top of the main app window.
     * @param primaryStage The primary stage.
     * @return The toolbar.
     */

    public Pane createToolbar(EventHandler<MouseEvent> enterEvent, EventHandler<MouseEvent> exitEvent) {
        GridPane toolbar = new GridPane();
        toolbar.setPadding(new Insets(4, 4, 4, 4));
        toolbar.setHgap(10);

        Button restart = new Button();
        Button undo = new Button();
        Button redo = new Button();
        Button difficulty = new Button();
        Button save = new Button();
        restart.setOnMouseEntered(enterEvent);
        restart.setOnMouseExited(exitEvent);
        undo.setOnMouseEntered(enterEvent);
        undo.setOnMouseExited(exitEvent);
        redo.setOnMouseEntered(enterEvent);
        redo.setOnMouseExited(exitEvent);
        difficulty.setOnMouseEntered(enterEvent);
        difficulty.setOnMouseExited(exitEvent);
        save.setOnMouseEntered(enterEvent);
        save.setOnMouseExited(exitEvent);

        undo.setOnAction(actionEvent -> controller.undo());
        redo.setOnAction(actionEvent -> controller.redo());

        restart.setText("Restart Game");
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Confirmation dialog box 
                String confirmText = "Are you sure you want to restart the application?";
                Alert confirmAlert = new Alert(AlertType.CONFIRMATION, confirmText, ButtonType.YES, ButtonType.NO);
                confirmAlert.setTitle("Restart Confirmation");
                confirmAlert.setHeaderText(null);
                confirmAlert.setGraphic(null);
                confirmAlert.setResizable(false);
                Optional<ButtonType> result = confirmAlert.showAndWait();
                if(result.get() == ButtonType.YES) {
                    primaryStage.close();
                    new JavaFXMainMenu().start(new Stage());
                }
            }
        });

        undo.setText("Undo");
        redo.setText("Redo");

        String difficultyText = "Difficulty: " + aiDifficulty;
        difficulty.setText(difficultyText);
        difficulty.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (aiDifficulty == 3) aiDifficulty = 1;
                else aiDifficulty += 1;
                controller.getCurrentGame().getPlayer2().setDifficulty(aiDifficulty);
                String difficultyText = "Difficulty: " + aiDifficulty;
                difficulty.setText(difficultyText);
                
            }
        });

        save.setText("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                controller.getCurrentGame().saveGame();
            }
        });

        toolbar.add(restart, 0, 0);
        toolbar.add(undo, 5, 0);
        toolbar.add(redo, 6, 0);
        toolbar.add(difficulty, 11, 0);
        if (p2Type != 'c') difficulty.setDisable(true);
        toolbar.add(save, 16, 0);

        return toolbar;
    }

    /**
     * Starts the game loop -> checks for updates and renders them on the Canvas.
     * @param c The GraphicsContext associated with the Canvas being used.
     */

    public void startGameLoop(GraphicsContext c) {
        //long prev = System.nanoTime();
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                //float delta = Math.max((float) ((System.nanoTime() - prev) / 1E9), 1.0f / 30.0f);

                controller.getActiveScreen().render(c);
            }
        };

        timer.start();
    }

    /**
     * Stops the game loop.
     */

    public void stopGameLoop() {
        timer.stop();
    }

    /**
     * Prints text to log.
     * @param text String containing text to log.
     */

    public void writeToLog(String text) {
        log.appendText(text + '\n');
    }

    /**
     * Updates game status.
     * @param str String containing game status.
     */

    public void setStatus(String str) {
        status.setText(str);
    }

    public void writeMoveLog(String move, boolean left) {
        if (left) leftMoveLog.appendText(move + '\n');
        else rightMoveLog.appendText(move + '\n');
    }

    /**
     * Creates the popup alert and handles the actions that take place
     * after the game is over, including asking the user if they want
     * to play again, playing a sound and updating the status.
     * @param winMessage The status message to be set.
     * @return True if the user wants to play again, false otherwise.
     */

    public boolean endGameOptions(String winMessage) {
        String confirmText = "Do you want to play again?";

        final FutureTask<Boolean> playAgain = new FutureTask<Boolean>(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                playWinSound();
                controller.getActiveScreen().render(canvas.getGraphicsContext2D());
                setStatus(winMessage);
                stopGameLoop();
                Alert confirmAlert = new Alert(AlertType.CONFIRMATION, confirmText, ButtonType.YES, ButtonType.NO);
                confirmAlert.setTitle("Restart Confirmation");
                confirmAlert.setHeaderText(null);
                confirmAlert.setGraphic(null);
                confirmAlert.setResizable(false);
                Optional<ButtonType> result = confirmAlert.showAndWait();
                return result.get() == ButtonType.YES ? true : false;
            }
        });
        Platform.runLater(playAgain);
        try {
            Boolean result = playAgain.get();
            if (!result) {
                Platform.runLater( () -> {
                    primaryStage.close();
                });
            }
            else startGameLoop(canvas.getGraphicsContext2D());
            return result;
        }
        catch (Exception e) {
            System.out.println("Exception!" + e.getMessage());
            return false;
        }
    }

    /**
     * Plays the sound file win.wav. To be used when the game is over.
     */

    public void playWinSound() {
        File winSound = new File("CPSC233Project/assets/win.wav");
        try {
            Media sound = new Media(winSound.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(sound);
            mediaPlayer.play();
        }
        catch (Exception e) {
            winSound = new File("assets/win.wav");
            try {
                Media sound = new Media(winSound.toURI().toString());
                MediaPlayer mediaPlayer = new MediaPlayer(sound);
                mediaPlayer.play();
            }
            catch (Exception a) {
                System.out.println("Cannot find sound file:" + winSound.getAbsolutePath());
            }
        }
    }
}

