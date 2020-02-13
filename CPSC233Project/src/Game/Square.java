package Game;
import Pieces.*;

/**
 * Represents a square on the chess board.
 *
 */

public class Square {
	
	private int x;
	private int y;
	private Piece piece;
	
	/**
	 * Creates a square with the given information.
	 * @param x The x-coordinate of the square.
	 * @param y THe y-coordinate of the square.
	 * @param piece The piece that sits on the square, if any.
	 */
	
	public Square(int x, int y, Piece piece) {
		this.x = x;
		this.y = y;
		this.piece = piece;
	}
	
	/**
	 * Gets the x-value for a square.
	 * @return The x value of a square as an integer.
	 */
	
	public int getX() {
		return this.x;
	}
	
	/**
	 * Sets the x-value for a square.
	 * @param x The x value as an integer.
	 */
	
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Gets the y-value for a square.
	 * @return The y value of a square as an integer.
	 */
	
	public int getY() {
		return this.y;
	}
	
	/**
	 * Sets the y-value for a square.
	 * @param y The y value as an integer.
	 */
	
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Gets the piece that sits on a square.
	 * @return The piece that sits on a square.
	 */
	
	public Piece getPiece() {
		return this.piece;
	}
	
	/**
	 * Sets the piece that sits on a square.
	 * @param p The piece to be set on a square.
	 */
	
	public void setPiece(Piece p) {
		this.piece = p;
	}
}
