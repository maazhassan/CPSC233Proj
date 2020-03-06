package Screens;

import javafx.scene.canvas.GraphicsContext;

public interface Screen {
    void create();
    void render(float delta, GraphicsContext g);
    void dispose();
}
