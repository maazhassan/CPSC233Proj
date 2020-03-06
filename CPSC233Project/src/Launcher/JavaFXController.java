package Launcher;

import Game.GameEventHandler;
import Game.MainGame;
import Screens.Screen;

public class JavaFXController implements GameEventHandler {

    private MainGame game;
    private JavaFXApp window;
    private Screen activeScreen;

    public JavaFXController(JavaFXApp window) {
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

    public void setActiveScreen(Screen s) {
        if (activeScreen != null) activeScreen.dispose();
        activeScreen = s;
        s.create();
    }

    public Screen getActiveScreen() {
        return activeScreen;
    }
}
