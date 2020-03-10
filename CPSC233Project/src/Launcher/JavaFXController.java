package Launcher;

import Game.Board;
import Game.GameEventHandler;
import Game.MainGame;
import Game.Square;
import Pieces.Piece;
import Screens.Screen;
import javafx.application.Platform;

public class JavaFXController implements GameEventHandler {

    private MainGame game;
    private JavaFXApp window;
    private Screen activeScreen;

    private final int[] nextMove = new int[4];

    // Reuse the object so we don't eat up the memory
    private String[][] boardState = new String[Board.SIZE][Board.SIZE];

    public JavaFXController(JavaFXApp window, char p1Color, char p2Type, int aiDifficulty) {
        game = new MainGame(this, p1Color, p2Type, aiDifficulty);
        this.window = window;

        new Thread(() -> game.start()).start();
    }

    @Override
    public boolean requestShouldPlayAgain() {
        return false;
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

    public void setNextMove(int x1, int y1, int x2, int y2) {
        synchronized(nextMove) {
            nextMove[0] = x1;
            nextMove[1] = y1;
            nextMove[2] = x2;
            nextMove[3] = y2;

            nextMove.notify();
        }
    }

    public String getCurrentPlayer() {
        return game.getCurrentPlayer();
    }

    public void undo() {
        setNextMove(8, 0, 0, 0);
    }

    public void redo() {
        setNextMove(9, 0, 0, 0);
    }

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

    public void setStatus(String s) {
        window.setStatus(s);
    }

    public void setActiveScreen(Screen s) {
        activeScreen = s;
        s.create();
    }

    public Screen getActiveScreen() {
        return activeScreen;
    }
}
