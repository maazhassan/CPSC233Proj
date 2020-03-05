import Game.GameEventHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFXApp extends Application {
    private Controller controller;

    @Override
    public void start(Stage primaryStage) throws Exception{
        controller = new Controller(this);

        primaryStage.setTitle("Hello World");
        primaryStage.show();
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
