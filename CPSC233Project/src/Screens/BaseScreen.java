package Screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * A class representing a generic base screen.
 */

public abstract class BaseScreen implements Screen {

    /**
     * Fills the screen with the color passed to the method.
     * @param g The GraphicsContext associated with the Canvas being used.
     * @param color The color to fill the screen with.
     */

    public final void clear(GraphicsContext g, Color color) {
        Paint temp = g.getFill();
        g.setFill(color);
        g.fillRect(0, 0, 512, 512);
        g.setFill(temp);
    }

    /**
     * Fills the screen with white.
     * @param g The GraphicsContext associated with the Canvas being used.
     */

    public final void clear(GraphicsContext g) {
        clear(g, Color.WHITE);
    }
}
