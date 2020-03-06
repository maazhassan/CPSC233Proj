package Launcher;

import Game.GameEventHandler;
import Game.MainGame;
import Screens.GameScreen;
import Screens.Screen;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    public static final int SIZE = 512;

    private Controller controller;
    private Screen activeScreen;

    @Override
    public void start(Stage primaryStage) throws Exception {
        controller = new Controller(this);

        VBox root = new VBox();
        Scene scene = new Scene(root); // No idea what this is for. JavaFX needs it

        // Body

        // The main container that splits the left canvas panel with the right log panel
        HBox body = new HBox();

        Canvas canvas = new Canvas(SIZE, SIZE); // The main canvas object that we'll be using to draw images
        TextArea log = new TextArea();

        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                activeScreen.onMouseEvent(mouseEvent);
            }
        });
        log.setEditable(false);
        log.setMinSize(SIZE * 0.9, SIZE * 0.5);

        body.getChildren().add(canvas);
        body.getChildren().add(log);

        // Weeeeeeee

        root.getChildren().add(createToolbar());
        root.getChildren().add(body);

        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.show();

        //
        setActiveScreen(new GameScreen());

        startGameLoop(canvas.getGraphicsContext2D());
    }

    public Pane createToolbar() {
        GridPane toolbar = new GridPane();
        toolbar.setPadding(new Insets(4, 4, 4, 4));
        toolbar.setHgap(10);

        Button undo = new Button();
        Button redo = new Button();
        ComboBox<Integer> difficulty = new ComboBox<>();

        undo.setText("Undo");
        redo.setText("Redo");
        difficulty.getItems().addAll(1, 2, 3);
        difficulty.valueProperty().addListener((o, old, newValue) -> {
            System.out.println("Item: " + newValue);
        });
        difficulty.getSelectionModel().selectFirst();

        toolbar.add(undo, 1, 0);
        toolbar.add(redo, 2, 0);
        toolbar.add(new Label("Difficulty:"), 8, 0);
        toolbar.add(difficulty, 9, 0);

        return toolbar;
    }

    public void startGameLoop(GraphicsContext c) {
        long prev = System.nanoTime();
        new AnimationTimer() {

            @Override
            public void handle(long l) {
                float delta = Math.max((float) ((System.nanoTime() - prev) / 1E9), 1.0f / 30.0f);

                activeScreen.render(delta, c);
            }
        }.start();
    }

    public void setActiveScreen(Screen s) {
        if (activeScreen != null) activeScreen.dispose();
        activeScreen = s;
        s.create();
    }
}

class Controller implements GameEventHandler {

    private MainGame game;
    private JavaFXApp window;

    public Controller(JavaFXApp window) {
        //game = new MainGame(this)
        this.window = window;
    }

    @Override
    public boolean requestShouldPlayAgain() {
        return false;
    }

    @Override
    public int[] createMove() {
        return new int[0];
    }

    @Override
    public void log(String out) {

    }
}
