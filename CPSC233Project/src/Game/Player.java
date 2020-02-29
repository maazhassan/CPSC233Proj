package Game;
import Pieces.*;

/**
 * Represents a player in the game.
 */

public abstract class Player {
	private boolean white;
	private boolean human;
	private boolean inCheck = false;
	private int difficulty = 0;
	
	/**
	 * 
	 * @return True if the player is white, false otherwise.
	 */

	public boolean isWhite() {
		return this.white;
	}
	
	/**
	 * Sets the color of the player.
	 * @param white True if the player is white, false otherwise.
	 */

	public void setWhite(boolean white) {
		this.white = white;
	}
	
	/**
	 * 
	 * @return True if the player is human, false otherwise.
	 */

	public boolean isHuman() {
		return this.human;
	}
	
	/**
	 * Sets the player as a human or computer.
	 * @param human True if the player is human, false otherwise.
	 */

	public void setHuman(boolean human) {
		this.human = human;
	}
	
	/**
	 * 
	 * @return True if the player is in check, false otherwise.
	 */

	public boolean isInCheck() {
		return this.inCheck;
	}
	
	/**
	 * Sets the check status of the player.
	 * @param check 
	 */

	public void setCheck(boolean check) {
		this.inCheck = check;
	}

	/**
	 * 
	 * @return The difficulty chosen for the AI.
	 */

	public int getDifficulty() {
		return this.difficulty;
	}

	/**
	 * Sets the AI for the Computer Player.
	 * @param difficulty The difficulty chosen by the player.
	 */

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	
	/**
	 * 
	 * @return The color of the player, as a string.
	 */

	public String printColor() {
		if (this.isWhite()) return "white";
		else return "black";
	}
	
	/**
	 * Finds the square that the king is on for the player called for.
	 * @param board The current board state.
	 * @return The Square object that this player's king is on.
	 */

	public Square findKingSquare(Board board) {
		Square kingSquare = null;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square square = board.getSquare(x, y);
				Piece pieceOnSquare = square.getPiece();
				if (pieceOnSquare != null) {
					if (pieceOnSquare instanceof King && pieceOnSquare.isWhite() == this.isWhite()) {
						kingSquare = square;
					}
				}
			}
		}
		
		return kingSquare;
	}
	
	/**
	 * Generates a move, different behaviour depending on type of player.
	 * @param board The current board state.
	 * @return The generated move.
	 */

	public abstract Move generateMove(Board board);
	
}
