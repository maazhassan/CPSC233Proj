package Game;

/**
 * Represents a move.
 *
 */

public class Move {
	
	private Square start;
	private Square end;
	private boolean castlingMove;
	
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
}
