package Screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

/**
 * An interface for methods related to screens.
 */

public interface Screen {

    /**
     * Method that builds/intitializes the screen.
     */

    void create();

    /**
     * Method that renders the screen.
     * @param delta 
     * @param g The GraphicsContext associated with the Canvas being used.
     */

    void render(float delta, GraphicsContext g);

    /**
     * Method that handles mouse events (clicks).
     * @param event The MouseEvent to handle.
     */

    void onMouseEvent(MouseEvent event);
}
