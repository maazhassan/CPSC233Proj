import Game.GameEventHandler;
import Screens.GameScreen;
import Screens.Screen;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private Controller controller;
    private Screen activeScreen;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller(this);

        VBox root = new VBox();
        Scene scene = new Scene(root); // No idea what this is for. JavaFX needs it

        // The main container that splits the left canvas panel with the right log panel
        HBox body = new HBox();

        // The main canvas object that we'll be using to draw images
        Canvas canvas = new Canvas(512, 512);
        body.getChildren().add(canvas);

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
        HBox toolbar = new HBox();

        Button undo = new Button();
        Button redo = new Button();

        undo.setText("Undo");
        redo.setText("Redo");

        toolbar.getChildren().add(undo);
        toolbar.getChildren().add(redo);

        return toolbar;
    }

    public void startGameLoop(GraphicsContext c) {
        long prev = System.nanoTime();
        new AnimationTimer() {

            @Override
            public void handle(long l) {
                float delta = Math.max((float) ((System.nanoTime() - prev) / 1E9), 1.0f/30.0f);

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

    private JavaFXApp window;

    public Controller(JavaFXApp window) {
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
