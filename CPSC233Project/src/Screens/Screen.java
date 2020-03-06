package Screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

public interface Screen {
    void create();
    void render(float delta, GraphicsContext g);
    void dispose();

    void onMouseEvent(MouseEvent event);
}
