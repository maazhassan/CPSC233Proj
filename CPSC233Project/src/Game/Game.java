package Game;
import Pieces.*;

/**
 * Main game class that puts all the other classes together.
 * Used to actually run the game.
 * 
 */

public class Game {
	
	//main method, contains pseudocode right now, everyone contribute to this
	public static void main(String[] args) {
		
		/*
		 * - Initialize player sides/colors
		 * 
		 * - Initialize board
		 * 
		 * - Ask white player to make a move
		 * 
		 * - Determine multiple things:
		 * 		- Validity of move:
		 * 			- can this type of piece actually move to that spot?
		 * 			- is there a piece in the way?
		 * 			- does this move put your own king in check?
		 * 			- is your king currently in check? if so, the next move must remove the check
		 * 		- Does this move capture a piece?
		 * 		- Does this move put the enemy king in check?
		 * 		- Is it a pawn moving to the back rank?
		 * 		- Does the move result in a checkmate/stalemate?
		 * 		- Is it a castling move?
		 * 
		 * - Once all of the above is dealt with, actually make the move
		 * 
		 * - Switch to the black player
		 * 
		 * - Ask the black player to make a move
		 * 
		 * - Recheck everything for that move.
		 * 
		 * - Repeat until checkmate/stalemate, then end the game.
		 */
		
	}

}
