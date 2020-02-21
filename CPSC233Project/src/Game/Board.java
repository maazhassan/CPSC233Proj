package Game;
import Pieces.*;

/*
 * Represents the chess board containing data of the pieces.
 * (0, 0) represents the top-left of the board. (7, 7) represents bottom-right.
 */
public class Board {

    final int SIZE = 8;
    private final Square[][] data = new Square[SIZE][SIZE]; // data[x][y]

    private boolean isBottomWhite;

    public Board(boolean isBottomWhite) {
        this.isBottomWhite = isBottomWhite;

        // Initialization of some stuff
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                data[x][y] = new Square(x, y, null);
            }
        }

        resetBoard();
    }


	//return a square on the board
	public Square getSquare(int x, int y) {
		return data[x][y];
	}

	//reset the board
	public void resetBoard() {
		for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                // I know there's a better way of doing this but not quite sure.
                //  Making new instances every reset isn't the most optimal way
                //  to start a new game, but it reduces the complexity of code
                //  and plus, it's not an animation game so we don't need
                //  to squeeze out the possible memory of the computer


                // Determine if the bottom-half of the board is a white piece
                // or a black piece
                boolean isWhite = y >= 4 & isBottomWhite;

                int distance = Math.abs(SIZE/2 - y); // Distance from center to y-coordinate
                if (distance <= 2) {
                    data[x][y].setPiece(null);
                    continue;
                } else if (distance == 3) {
                    data[x][y].setPiece(new Pawn(isWhite));
                    data[x][y].getPiece().setPieceChar('p', isWhite);
                    continue;
                }

                Piece p;
                char pieceChar;
                switch(x) {
                    case 0:
                    case 7:
                        p = new Rook(isWhite);
                        pieceChar = 'r';
                        break;
                    case 1:
                    case 6:
                        p = new Knight(isWhite);
                        pieceChar = 'n';
                        break;
                    case 2:
                    case 5:
                        p = new Bishop(isWhite);
                        pieceChar = 'b';
                        break;
                   case 3:
                        p = new Queen(isWhite);
                        pieceChar = 'q';
                        break;
                   case 4:
                        p = new King(isWhite);
                        pieceChar = 'k';
                        break;
                   default:
                        throw new RuntimeException("x should never be " + x + "!");
               }

                data[x][y].setPiece(p);
                data[x][y].getPiece().setPieceChar(pieceChar, isWhite);
            }
        }
	}

}
