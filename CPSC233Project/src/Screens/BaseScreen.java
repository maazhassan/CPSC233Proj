package Screens;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public abstract class BaseScreen implements Screen {
    public final void clear(GraphicsContext g, Color color) {
        Paint temp = g.getFill();
        g.setFill(color);
        g.fillRect(0, 0, 512, 512);
        g.setFill(temp);
    }

    public final void clear(GraphicsContext g) {
        clear(g, Color.WHITE);
    }
}
