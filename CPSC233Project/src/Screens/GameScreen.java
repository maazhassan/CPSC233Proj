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

/**
 * The game screen that displays the chess board and pieces. The user interacts
 * with this screen in order to play the GUI version of the game.
 */

public class GameScreen extends BaseScreen {

    private JavaFXController controller;
    private Color light = Color.rgb(241, 217, 182);
    private Color dark = Color.rgb(181, 137, 99);

    private HashMap<String, Image> images = new HashMap<>();
    private HashMap<String, int[]> coords = new HashMap<>();

    // If a piece is currently being selected to move to another location
    private boolean selected = false;
    private int startX = 0, startY = 0;

    /**
     * Creates a GameScreen instance.
     * @param controller The JavaFXController instance connected to this GameScreen.
     */

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

        // Adding coords of squares
        final int WIDTH = 64;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String key = Integer.toString(i) + Integer.toString(j);
                int[] value = {WIDTH * i, WIDTH * j};
                coords.put(key, value);
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

        Color select = Color.rgb(175, 132, 181);
        String key = Integer.toString(startX) + Integer.toString(startY);
        int[] coordsArray = coords.get(key);
        Color original = ((startX + startY) % 2) == 0 ? light : dark;

        if (selected) {
            char xcoord = (char)(startX + 'a');
            status = String.format("%s Selected: (%c%d)", status, xcoord, 8-startY);

            g.setFill(select);
            g.fillRect(coordsArray[0], coordsArray[1], 64, 64);
            drawPieces(g);
        }
        else {
            g.setFill(original);
            g.fillRect(coordsArray[0], coordsArray[1], 64, 64);
            drawPieces(g);
        }
        controller.setStatus(status);
    }

    /**
     * Draws the chess board by filling in the dark squares.
     * @param g The GraphicsContext associated with the Canvas being used.
     */

    public void drawBoard(GraphicsContext g) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                // Draw white checkered squares

                if ((r + c) % 2 == 0) continue;

                float squareSize = JavaFXApp.SIZE / 8.0f;

                Paint temp = g.getFill();
                g.setFill(dark);
                g.fillRect(r * squareSize, c * squareSize, squareSize, squareSize);
                g.setFill(temp);
            }
        }
    }

    /**
     * Draws the pieces onto the chess board.
     * @param g The GraphicsContext associated with the Canvas being used.
     */

    public void drawPieces(GraphicsContext g) {
        String[][] state = controller.getBoardState();
        int cellSize = JavaFXApp.SIZE / Board.SIZE;

        for (int x = 0; x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                String imageKey = state[x][y];

                g.drawImage(images.get(imageKey), x * cellSize, y * cellSize);
            }
        }
    }

    @Override
    public void onMouseEvent(GraphicsContext g, MouseEvent event) {
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

    /**
     * Calculates and returns the x coordinate of the square clicked by the user.
     * @param mouseX The x coordinate of the mouse click relative to the JavaFXApp window size.
     * @return The x coordinate of the mouse click relative to our chess coordinate system.
     */

    private int getChessCoordinateX(double mouseX) {
        return (int)(mouseX / (JavaFXApp.SIZE / 8.0));
    }

    /**
     * Calculates and returns the y coordinate of the square clicked by the user.
     * @param mouseY The y coordinate of the mouse click relative to the JavaFXApp window size.
     * @return The y coordinate of the mouse click relative to our chess coordinate system.
     */

    private int getChessCoordinateY(double mouseY) {
        return (int)(mouseY / (JavaFXApp.SIZE / 8.0));
    }

    /**
     * Loads a PNG image, used to load the chess piece assets.
     * @param name The image name.
     * @return The PNG image.
     */

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
            img = new File("CPSC233Project/assets/" + name + extension);
            try {
                return new Image(new FileInputStream(img));
            }
            catch (FileNotFoundException a) {
                throw new RuntimeException("Cannot find image: " + img.getAbsolutePath());
            }
        }   
    }
}
