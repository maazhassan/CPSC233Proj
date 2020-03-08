package Launcher;

import Game.Board;
import Game.GameEventHandler;
import Game.MainGame;
import Game.Square;
import Pieces.Piece;
import Screens.Screen;

public class JavaFXController implements GameEventHandler {

    private MainGame game;
    private JavaFXApp window;
    private Screen activeScreen;

    // Reuse the object so we don't eat up the memory
    private String[][] boardState = new String[Board.SIZE][Board.SIZE];

    public JavaFXController(JavaFXApp window, char p1Color, char p2Type, int aiDifficulty) {
        game = new MainGame(this, p1Color, p2Type, aiDifficulty);
        this.window = window;
    }

    @Override
    public boolean requestShouldPlayAgain() {
        return false;
    }

    @Override
    public int[] createMove() {
        return new int[0];
    }

    @Override
    public void log(String out) {

    }

    public String[][] getBoardState() {
        for (int x = 0;  x < Board.SIZE; x++) {
            for (int y = 0; y < Board.SIZE; y++) {
                Piece piece = game.getPiece(x, y);

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

    public void setActiveScreen(Screen s) {
        if (activeScreen != null) activeScreen.dispose();
        activeScreen = s;
        s.create();
    }

    public Screen getActiveScreen() {
        return activeScreen;
    }
}
