package Game;
import Pieces.*;

/**
 * Represents the chess board containing data of the pieces.
 * (0, 0) represents the top-left of the board. (7, 7) represents bottom-right.
 */

public class Board {

    public static final int SIZE = 8;
    private final Square[][] data = new Square[SIZE][SIZE]; // data[x][y]

    private boolean isBottomWhite;
    
    /**
     * 
     * @return True if the bottom player (player 1) is white. False otherwise.
     */

    public boolean getIsBottomWhite() {
    	return this.isBottomWhite;
    }

    /**
     * Creates the board with empty Squares.
     * @param isBottomWhite True if the bottom player is white. False otherwise.
     */

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

    /**
     * Creates a copy of the given board.
     * @param toCopy The board to copy.
     */

    public Board(Board toCopy) {
        this.isBottomWhite = toCopy.isBottomWhite;

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.data[x][y] = new Square(toCopy.data[x][y].getX(), toCopy.data[x][y].getY(), toCopy.data[x][y].getPiece());
            }
        }
    }

    /**
     * Copies the information from one instantiated board to another.
     * @param other The board to copy information from.
     */

    public void copyFrom(Board other) {
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                this.data[x][y].setPiece(other.data[x][y].getPiece());
            }
        }
    }

    /**
     * Returns a square on the board.
     * @param x The x-value of the square, as an integer.
     * @param y The y-value of the square, as an integer.
     * @return The Square object at (x,y) on the board.
     */
	
	public Square getSquare(int x, int y) {
        if (!(x >= 0 && x < SIZE && y >= 0 && y < SIZE)) return null;
		return data[x][y];
	}

    /**
     * Resets the board, returning all pieces to their starting location.
     */
    
	public void resetBoard() {
		for (int y = 0; y < SIZE; y++) {
            for (int x = 0; x < SIZE; x++) {
                // Determine if the bottom-half of the board is a white piece
                // or a black piece
            	boolean isWhite;
            	if (y >=4) {
            		isWhite = isBottomWhite;
            	}
            	else {
            		isWhite = !isBottomWhite;
            	}

                Piece p = null;

                if (y == 0 || y == SIZE-1) {
                    switch(x) {
                        case 0:
                        case 7:
                            p = new Rook(isWhite);
                            break;
                        case 1:
                        case 6:
                            p = new Knight(isWhite);
                            break;
                        case 2:
                        case 5:
                            p = new Bishop(isWhite);
                            break;
                       case 3:
                            p = new Queen(isWhite);
                            break;
                       case 4:
                            p = new King(isWhite);
                            break;
                       default:
                            throw new RuntimeException("x should never be " + x + "!");
                    }

                } else if (y == 1 || y == (SIZE-1) - 1) {
                    p = new Pawn(isWhite);
                }
                
                data[x][y].setPiece(p);
            }
        }
	}

}
