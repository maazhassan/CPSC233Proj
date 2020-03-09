package Launcher;

import Game.GameEventHandler;
import Game.MainGame;
import Screens.GameScreen;
import Screens.Screen;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Optional;

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

    private TextArea log;

    public JavaFXApp(char p1Color, char p2Type, int aiDifficulty) {
        this.p1Color = p1Color;
        this.p2Type = p2Type;
        this.aiDifficulty = aiDifficulty;
    }

    public void run() {
        Stage primaryStage = new Stage();

        VBox root = new VBox();
        Scene scene = new Scene(root); // No idea what this is for. JavaFX needs it

        // Body

        // The main container that splits the left canvas panel with the right log panel
        HBox body = new HBox();

        Canvas canvas = new Canvas(SIZE, SIZE); // The main canvas object that we'll be using to draw images
        log = new TextArea();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.getActiveScreen().onMouseEvent(mouseEvent);
            }
        });
        log.setEditable(false);
        log.setMinSize(SIZE * 0.9, SIZE * 0.5);

        body.getChildren().add(canvas);
        body.getChildren().add(log);

        // Weeeeeeee

        root.getChildren().add(createToolbar(primaryStage));
        root.getChildren().add(body);

        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller = new JavaFXController(this, p1Color, p2Type, aiDifficulty);
        controller.setActiveScreen(new GameScreen(controller));
        startGameLoop(canvas.getGraphicsContext2D());
    }

    public Pane createToolbar(Stage primaryStage) {
        GridPane toolbar = new GridPane();
        toolbar.setPadding(new Insets(4, 4, 4, 4));
        toolbar.setHgap(10);

        Button restart = new Button();
        Button undo = new Button();
        Button redo = new Button();

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

        toolbar.add(restart, 0, 0);
        toolbar.add(undo, 5, 0);
        toolbar.add(redo, 6, 0);

        return toolbar;
    }

    public void startGameLoop(GraphicsContext c) {
        long prev = System.nanoTime();
        new AnimationTimer() {

            @Override
            public void handle(long l) {
                float delta = Math.max((float) ((System.nanoTime() - prev) / 1E9), 1.0f / 30.0f);

                controller.getActiveScreen().render(delta, c);
            }
        }.start();
    }

    public void writeToLog(String text) {
        log.appendText(text + '\n');
    }
}

