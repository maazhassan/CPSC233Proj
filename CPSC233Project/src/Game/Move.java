package Game;
import Pieces.*;

/**
 * Represents a move.
 *
 */

public class Move {
	
	private Square start;
	private Square end;
	private boolean castlingMove = false;
	private boolean promotionMove = false;
	
	/**
	 * Creates a chess move.
	 * @param start The starting square.
	 * @param end The ending square.
	 */
	
	public Move(Square start, Square end) {
		this.start = start;
		this.end = end;
	}
	
	public Square getStart() {
		return this.start;
	}
	
	public Square getEnd() {
		return this.end;
	}
	
	public boolean isCastlingMove() {
		return this.castlingMove;
	}
	
	public void setCastlingMove(boolean castlingMove) {
		this.castlingMove = castlingMove;
	}

	public boolean isPromotionMove() {
		return this.promotionMove;
	}

	public void setPromotionMove(boolean promotionMove) {
		this.promotionMove = promotionMove;
	}

	public static void makeMove(Move move) {
		Piece pieceMoved = move.getStart().getPiece();
		move.getStart().setPiece(null);
		move.getEnd().setPiece(pieceMoved);
	}

	public static void undoMove(Move move, Piece pieceMoved, Piece endPiece) {
		move.getStart().setPiece(pieceMoved);
		move.getEnd().setPiece(endPiece);
	}

	public static void makeCastlingMove(Board board, Move move) {
		Piece pieceMoved = move.getStart().getPiece();
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
