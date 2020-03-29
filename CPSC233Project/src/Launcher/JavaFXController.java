package Launcher;

import Game.Board;
import Game.GameEventHandler;
import Game.MainGame;
import Game.Player;
import Pieces.Piece;
import Screens.Screen;
import javafx.application.Platform;

/**
 * The controller for the JavaFX version of the game. Implements the handler,
 * so this class can be passed to a MainGame instance to control it.
 */

public class JavaFXController implements GameEventHandler {

    private MainGame game;
    private JavaFXApp window;
    private Screen activeScreen;

    private final int[] nextMove = new int[4];

    // Reuse the object so we don't eat up the memory
    private String[][] boardState = new String[Board.SIZE][Board.SIZE];

    /**
     * Creates an instance of the controller. Also creates a MainGame instance, and
     * passes itself to it.
     * @param window The JavaFXApp window that contains this controller.
     * @param p1Color The color of player 1.
     * @param p2Type The type of player 2.
     * @param aiDifficulty The difficulty of the AI.
     */

    public JavaFXController(JavaFXApp window, char p1Color, char p2Type, int aiDifficulty) {
        game = new MainGame(this, p1Color, p2Type, aiDifficulty);
        this.window = window;

        new Thread(() -> game.start()).start();
    }

    @Override
    public boolean requestShouldPlayAgain(String winMessage) {
        return window.endGameOptions(winMessage);
    }

    @Override
    public int[] createMove() {
        synchronized (nextMove) {
            try {
                nextMove.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return nextMove;
    }

    @Override
    public void log(String out) {
        Platform.runLater(() -> window.writeToLog(out));
    }

    @Override
    public void moveLog(String move, boolean left) {
        Platform.runLater(() -> window.writeMoveLog(move, true));
    }

    /**
     * Creates the next move selected by the player. The player chooses moves by clicking on
     * the squares of the chess board while the thread waits. Notifies the waiting thread
     * once the user has selected a move, and passes the move to the createMove() method.
     * @param x1 The first x coordinate.
     * @param y1 The first y coordinate.
     * @param x2 The second x coordinate.
     * @param y2 The second y coordinate.
     */
    
    public void setNextMove(int x1, int y1, int x2, int y2) {
        synchronized(nextMove) {
            nextMove[0] = x1;
            nextMove[1] = y1;
            nextMove[2] = x2;
            nextMove[3] = y2;

            nextMove.notify();
        }
    }

    /**
     * 
     * @return The current player's color as a string.
     */

    public String getCurrentPlayerColor() {
        return game.getCurrentPlayerColor();
    }

    /**
     * 
     * @return The current player instance.
     */

    public Player getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    /**
     * Creates the next move as one that will have its undo variable set to true.
     * When the move is passed up to playMove() in the MainGame class, it will 
     * know to invoke the appropriate method in order to undo the last move.
     */

    public void undo() {
        setNextMove(8, 0, 0, 0);
    }

    /**
     * Creates the next move as one that will have its redo variable set to true.
     * When the move is passed up to playMove() in the MainGame class, it will 
     * know to invoke the appropriate method in order to redo the last move.
     */

    public void redo() {
        setNextMove(9, 0, 0, 0);
    }

    /**
     * Creates a 2D array representing the current state of the board.
     * This 2D array is used to draw the pieces onto the board, by
     * using the elements as keys for a HashMap.
     * @return A 2D array representing the current board state.
     */

    public String[][] getBoardState() {
        for (int x = 0;  x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                Piece piece = game.getBoard().getSquare(x, y).getPiece();

                if (piece == null) {
                    boardState[x][y] = null;
                    continue;
                }

                char color = piece.isWhite() ? 'w' : 'b';
                char pieceChar = piece.getPieceChar();
                String combined = String.valueOf(color) + pieceChar;

                boardState[x][y] = combined;
            }
        }

        return boardState;
    }

    /**
     * Used to set the status in the game window.
     */

    public void setStatus(String s) {
        window.setStatus(s);
    }

    /**
     * Creates and sets the active screen.
     * @param s The screen to be set.
     */

    public void setActiveScreen(Screen s) {
        activeScreen = s;
        s.create();
    }

    /**
     * 
     * @return The current active screen.
     */

    public Screen getActiveScreen() {
        return activeScreen;
    }
    
    /**
     * 
     * @return The current game instance.
     */

    public MainGame getCurrentGame() {
        return game;
    }
}
