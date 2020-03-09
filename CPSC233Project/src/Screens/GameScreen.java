package Screens;

import Game.Board;
import Launcher.JavaFXApp;
import Launcher.JavaFXController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class GameScreen extends BaseScreen {

    private JavaFXController controller;
    private Color light = Color.rgb(241, 217, 182);
    private Color dark = Color.rgb(181, 137, 99);

    private HashMap<String, Image> images = new HashMap<>();

    // If a piece is currently being selected to move to another location
    private boolean selected = false;
    private int startX = 0, startY = 0;

    public GameScreen(JavaFXController controller) {
        this.controller = controller;
    }

    @Override
    public void create() {
        // Fast lazy way of loading all assets.
        char[] colors = {'w', 'b'};
        char[] pieces = {'b', 'k', 'n', 'p', 'q', 'r'};
        for (char c : colors) {
            for (char p : pieces) {
                String key = String.valueOf(c) + p;
                images.put(key, loadPng(key));
            }
        }
    }

    @Override
    public void render(float delta, GraphicsContext g) {
        // Clear the background
        clear(g, light);
        drawBoard(g);
        drawPieces(g);

        String status = "It's " + controller.getCurrentPlayer() + "'s turn!";
        if (selected) {
            char xcoord = (char)(startX + 'a');
            status = String.format("%s Selected: (%c%d)", status, xcoord, 8-startY);
        }
        controller.setStatus(status);
    }

    public void drawBoard(GraphicsContext g) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // Draw white checkered squares

                if ((r + c) % 2 == 0) continue;

                float squareSize = JavaFXApp.SIZE / 8.0f;

                Paint temp = g.getFill();
                g.setFill(dark);
                g.fillRect(r * squareSize + (2 * Math.random() - 1.0f) * 10 + Math.cos(a) * 20 * Math.min(a/2, 7.0f) * 2, Math.sin(a) * 10 +c * squareSize + (2 * Math.random() - 1.0f) * 10, squareSize, squareSize);
                g.setFill(temp);
            }
        }
    }
    
    float a = 0.0f;

    public void drawPieces(GraphicsContext g) {
      a += 0.07;
        String[][] state = controller.getBoardState();
        int cellSize = JavaFXApp.SIZE / Board.SIZE;

        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                String imageKey = state[x][y];

                
                g.drawImage(images.get(imageKey), x * cellSize + 7/a + x * x * y /  15 - Math.cos(a + y * y * x * a) * 15 + (2 * Math.random() - 1.0f) * 3, y * cellSize - Math.sin(x * a + x * y) * 15 + (2 * Math.random() - 1.0f) * 3);
            }
        }
    }

    @Override
    public void onMouseEvent(MouseEvent event) {
        int x = getChessCoordinateX(event.getX());
        int y = getChessCoordinateY(event.getY());

        if (!selected) {
            startX = x;
            startY = y;
            selected = true;

            controller.log("Selected piece: " + x + " " + y);
            return;
        }

        // At this part of the code, the user has clicked on a square for the second time
        controller.setNextMove(startX, startY, x, y);
        selected = false;
    }

    @Override
    public void dispose() {
        // Thought we had to dispose loaded images after. I guess not.
    }

    private int getChessCoordinateX(double mouseX) {
        return (int)(mouseX / (JavaFXApp.SIZE / 8.0));
    }

    private int getChessCoordinateY(double mouseY) {
        return (int)(mouseY / (JavaFXApp.SIZE / 8.0));
    }

    private Image loadPng(String name) {
        return loadImage(name, ".png");
    }

    /**
     * Loads an image with the specified path.
     * <br />
     * Images that do not exist will throw an exception.
     * @param name The image name
     * @param extension The extension type
     * @return A new image type.
     */
    private Image loadImage(String name, String extension) {
        File img = new File("assets/" + name + extension);
        try {
            return new Image(new FileInputStream(img));
        } catch (FileNotFoundException e) {
            // If it crashes here, then maybe the working directory of the Java program might be off.
            // Try editing the line File img = new File(...) so that the "assets/" will point to the assets folder.
            throw new RuntimeException("Cannot find image: " + img.getAbsolutePath());
        }
    }
}
