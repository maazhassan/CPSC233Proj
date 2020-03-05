package Game;
import Pieces.*;

/**
 * Represents a chess move.
 *
 */

public class Move {
	
	private Square start;
	private Square end;
	private boolean castlingMove = false;
	private boolean promotionMove = false;
	private boolean undo = false;
	private boolean redo = false;
	
	/**
	 * Creates a chess move.
	 * @param start The starting square.
	 * @param end The ending square.
	 */
	
	public Move(Square start, Square end) {
		this.start = start;
		this.end = end;
	}
	
	/**
	 * 
	 * @return The starting Square of the move.
	 */

	public Square getStart() {
		return this.start;
	}
	
	/**
	 * 
	 * @return The ending Square of the move.
	 */

	public Square getEnd() {
		return this.end;
	}
	
	/**
	 * 
	 * @return True if the move is a castling move, false otherwise.
	 */

	public boolean isCastlingMove() {
		return this.castlingMove;
	}
	
	/**
	 * Sets the move as a castling move.
	 * @param castlingMove True or false.
	 */

	public void setCastlingMove(boolean castlingMove) {
		this.castlingMove = castlingMove;
	}

	/**
	 * 
	 * @return True if the move is a pawn promoting.
	 */

	public boolean isPromotionMove() {
		return this.promotionMove;
	}

	/**
	 * Sets the move as a promotion move.
	 * @param promotionMove True or false.
	 */

	public void setPromotionMove(boolean promotionMove) {
		this.promotionMove = promotionMove;
	}

	public boolean isUndo() {
		return this.undo;
	}

	public void setUndo(boolean undo) {
		this.undo = undo;
	}

	public boolean isRedo() {
		return this.redo;
	}

	public void setRedo(boolean redo) {
		this.redo = redo;
	}

	/**
	 * Moves the pieces involved in the move.
	 * @param move The move to be made.
	 */

	public static void makeMove(Move move) {
		Piece pieceMoved = move.getStart().getPiece();
		move.getStart().setPiece(null);
		move.getEnd().setPiece(pieceMoved);
		pieceMoved.setMoved(true);
	}

	/**
	 * Undos a move.
	 * @param move The move to be undone.
	 * @param pieceMoved The piece that was moved.
	 * @param endPiece The piece that was on the ending square of the move.
	 */

	public static void undoMove(Move move, Piece pieceMoved, Piece endPiece) {
		move.getStart().setPiece(pieceMoved);
		move.getEnd().setPiece(endPiece);
		pieceMoved.setMoved(false);
	}

	/**
	 * Performs a castling move, moving both the king and rook appropriately.
	 * @param board The current board state.
	 * @param move The move (should only be a castling move).
	 */

	public static void makeCastlingMove(Board board, Move move) {
		Piece pieceMoved = move.getStart().getPiece();
		pieceMoved.setMoved(true);
		if (move.getEnd().getX() == 6) {    //right side castling
			move.getEnd().setPiece(pieceMoved);
			move.getStart().setPiece(null);
			Piece rCastlingRook = board.getSquare(7, move.getEnd().getY()).getPiece();    //Get the rook that will move
			board.getSquare(5, move.getEnd().getY()).setPiece(rCastlingRook);
			board.getSquare(7, move.getEnd().getY()).setPiece(null);    //Move the rook
		}
		else if (move.getEnd().getX() == 2) {    //left side castling
			move.getEnd().setPiece(pieceMoved);
			move.getStart().setPiece(null);
			Piece lCastlingRook = board.getSquare(0, move.getEnd().getY()).getPiece();    //Get the rook that will move
			board.getSquare(3, move.getEnd().getY()).setPiece(lCastlingRook);
			board.getSquare(0, move.getEnd().getY()).setPiece(null);    //Move the rook
		}
	}
}
