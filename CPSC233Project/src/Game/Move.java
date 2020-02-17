package Game;
import Pieces.*;

/**
 * Represents a move. (unfinished)
 *
 */

public class Move {
	
	private Square start;
	private Square end;
	
	//not sure about these yet
	private boolean valid;
	private boolean pieceKilled;
	
	/**
	 * Creates a chess move.
	 * @param start The starting square.
	 * @param end The ending square.
	 * @param player The player making the move.
	 */
	
	public Move(Square start, Square end) {
		this.start = start;
		this.end = end;
	}

}
