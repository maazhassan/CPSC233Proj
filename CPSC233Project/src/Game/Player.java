package Game;
import Pieces.*;
import java.util.ArrayList;

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
	 * Generates an ArrayList of all possible moves for the current board state.
	 * @param board The current board state.
	 * @return An ArrayList of Move objects.
	 */

	public ArrayList<Move> generateMovesList(Board board, boolean whitePieces) {
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		//Loops through every square on the board and adds all available moves to the ArrayList
		for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
				//Get square, then piece on square
				Square square = board.getSquare(x, y);
				Piece pieceOnSquare = square.getPiece();
				//Make sure piece is not null and matches player color
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() == whitePieces) {
						//Loop through board again
    					for (int x2 = 0; x2 < 8; x2++) {
							for (int y2 = 0; y2 < 8; y2++) {
								//Create move objects for every square
								Move testMove = new Move(square, board.getSquare(x2, y2));
								//Test if the move is valid and legal
								if (pieceOnSquare.canMove(board, testMove)) {									
									//Add castling moves if player is not in check, because they have been tested already
									if (testMove.isCastlingMove() && !this.isInCheck()) {
										availableMoves.add(testMove);
									}
									//Otherwise, test if move leaves player in check
									else {
										Piece endPiece = board.getSquare(x2, y2).getPiece();
										Move.makeMove(testMove);    //temporarily make the move
										Square kingSquare = this.findKingSquare(board);
										//make sure player is not in check
										if (!kingSquare.getPiece().canBeCheck(board, kingSquare)) {
											availableMoves.add(testMove);
										}
										Move.undoMove(testMove, pieceOnSquare, endPiece);    //undo the move
									}									
								}
							}
						}
    				}
    			}
    		}
		}
		return availableMoves;
	}
	
	/**
	 * Generates a move, different behaviour depending on type of player.
	 * @param board The current board state.
	 * @return The generated move.
	 */

	public abstract Move generateMove(Board board);
	
}
