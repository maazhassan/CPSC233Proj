package Screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameScreen extends BaseScreen {
    @Override
    public void create() {

    }

    @Override
    public void render(float delta, GraphicsContext g) {
        // Clear the background
        clear(g);

        g.fillText("Lol", 256, 256);
    }

    @Override
    public void dispose() {

    }
}
