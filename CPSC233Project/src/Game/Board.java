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
    
    public boolean getIsBottomWhite() {
    	return this.isBottomWhite;
    }

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
        if (!(x >= 0 && x < SIZE && y >= 0 && y < SIZE)) return null;
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
