package Game;
import Pieces.*;
import java.util.Scanner;

/**
 * Main game class that puts all the other classes together.
 * Used to actually run the game.
 * 
 */

public class Game {
	
	private Board board;
	private boolean gameOver = false;
	private Player currentPlayer;
	
	
	public HumanPlayer initializeP1(char color) {
		if (color == 'w') {
			return new HumanPlayer(true);
		}
		else {
			return new HumanPlayer(false);
		}
	}
	
	public HumanPlayer initializeP2Human(char color) {
		if (color == 'w') {
			return new HumanPlayer(true);
		}
		else {
			return new HumanPlayer(false);
		}
	}
	
	public ComputerPlayer initializeP2Computer(char color) {
		if (color == 'w') {
			return new ComputerPlayer(true);
		}
		else {
			return new ComputerPlayer(false);
		}
	}
	
	public void initializeGame(Player p1, Player p2) {
		
		board.resetBoard();
		
		if (p1.isWhite()) {
			this.currentPlayer = p1;
		}
		else {
			this.currentPlayer = p2;
		}
	}
	
	public void playMove(Move move) {
		
	}
	
	public boolean isGameOver() {
		return this.gameOver;
	}
	
	
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
		
		Game chessGame = new Game();
		Scanner input = new Scanner(System.in);
		HumanPlayer p1 = null;
		Player p2 = null;
		char p1Color = 'a';
		char p2Type = 'a';
		
		//User input for opponent type/color
		while (p2Type != 'c' && p2Type != 'h') {
			System.out.println("Play against computer or another human? (enter 'c' or 'h'):");
			p2Type = input.nextLine().charAt(0);
		}
		while (p1Color != 'w' && p1Color != 'b') {
			System.out.println("Do you want to play as white or black? (enter 'w' or 'b'):");
			p1Color = input.nextLine().charAt(0);
		}
		
		//Creating the player objects depending on the input
		p1 = chessGame.initializeP1(p1Color);
		
		if (p1.isWhite()) {
			if (p2Type == 'c') {
				p2 = chessGame.initializeP2Computer('b');
			}
			else {
				p2 = chessGame.initializeP2Human('b');
			}
		}
		else {
			if (p2Type == 'c') {
				p2 = chessGame.initializeP2Computer('w');
			}
			else {
				p2 = chessGame.initializeP2Human('w');
			}
		}
		
		//Initialize the board
		chessGame.initializeGame(p1, p2);
		
		//Move loop (WIP)
		while (!chessGame.isGameOver()) {
			chessGame.playMove(chessGame.currentPlayer.generateMove(chessGame.board));
			
			if (chessGame.currentPlayer == p1) {
				chessGame.currentPlayer = p2;
			}
			else {
				chessGame.currentPlayer = p1;
			}
		}
	}
}
