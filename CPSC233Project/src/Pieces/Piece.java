package Pieces;
import Game.*;

/**
 * Represents a piece on the board. 6 different types:
 * pawn, knight, bishop, rook, queen, king.
 */

public abstract class Piece {
	private boolean white;

	/**
	 * Creates a piece.
	 * @param isWhite True if the piece is white, false otherwise.
	 */

    public Piece(boolean isWhite) {
        this.setWhite(isWhite);
    }
	
	/**
	 * 
	 * @return True if the piece is white, false otherwise.
	 */

    public boolean isWhite() {
    	return this.white;
    }
	
	/**
	 * Sets the color of the piece.
	 * @param isWhite True if the piece is white, false otherwise.
	 */

    public void setWhite(boolean isWhite) {
    	this.white = isWhite;
    }
	
	/**
	 * 
	 * @return The character representation of the piece.
	 */

    public abstract char getPieceChar();
	
	/**
	 * Checks to see if the current board state results in a check.
	 * Called when making a move to test for legality.
	 * @param board The current board state.
	 * @param kingSquare The square that the king is on, for pieces of this color.
	 * @return True if a check is found, false otherwise.
	 */

    public boolean canBeCheck(Board board, Square kingSquare) {
    	for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			Square square = board.getSquare(x, y);
    			Piece pieceOnSquare = square.getPiece();
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() != this.isWhite()) {
                        //System.out.println(x + "," + y);
    					Move checkTestMove = new Move(square, kingSquare);
                        //System.out.printf("Move: %d %d -> %d %d\n", square.getX(), square.getY(), kingSquare.getX(), kingSquare.getY());
    					if (pieceOnSquare.canMove(board, checkTestMove)) return true;
    				}
    			}
    		}
    	}
    	return false;
    }

	/**
	 * Determines whether a piece is able to perform the given move.
	 * Does not take move legality into account - for example, does not
	 * check whether the move would leave the player in check. Only checks 
	 * whether the piece is able to move to that square based on what type
	 * of piece it is.
	 * @param board The current board state.
	 * @param move The move to be checked.
	 * @return True if the piece can perform the move, false otherwise.
	 */

	public abstract boolean canMove(Board board, Move move);
}
