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
		
		this.board = new Board(p1.isWhite());
		
		if (p1.isWhite()) {
			this.currentPlayer = p1;
		}
		else {
			this.currentPlayer = p2;
		}
		this.printBoard(board);
	}
	
	public void printBoard(Board board) {
		String printedBoard = "---------------------------------";
		for (int y = 0; y < 8; y++) {
			printedBoard = printedBoard + "\n";
			for (int x = 0; x < 8; x++) {
				Piece pieceOnSquare = board.getSquare(x, y).getPiece();
				if (pieceOnSquare == null) {
					printedBoard = printedBoard + '|';
					printedBoard = printedBoard + "   ";
				}
				else {
					printedBoard = printedBoard + '|';
					printedBoard = printedBoard + ' ' + pieceOnSquare.getPieceChar() + ' ';
				}
			}
			printedBoard = printedBoard + '|';
			printedBoard = printedBoard + "\n";
			printedBoard = printedBoard + "---------------------------------";
		}
		System.out.println(printedBoard);
	}
	
	public boolean playMove(Move move, Player p1, Player p2) {
		
		//The pieces involved in the move
		Piece pieceMoved = move.getStart().getPiece();
		Piece endPiece = move.getEnd().getPiece();

		//Check if the piece is valid (belongs to the player and is not null)
		if (pieceMoved == null || pieceMoved.isWhite() != currentPlayer.isWhite()) {
			System.out.println("Invalid piece!");
			return false;
		}
		
		//Check if the piece can move to that square
		if (!pieceMoved.canMove(board, move)) {
			System.out.println("That piece cannot move there!");
			return false;
		}
		
		//Check if this move castles
		if (move.isCastlingMove()) {
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
		else {    //The following do not need to be checked for a castling move, since they have already been checked by canMove() if the king is being moved.
			//Check if the player is in check at the end of their move (not allowed)
			boolean check = false;
			
			move.getStart().setPiece(null);   
			move.getEnd().setPiece(pieceMoved);    //temporarily make the move
			
			Square kingSquare = currentPlayer.findKingSquare(board);   //find the square that has the king on it
			
			if (kingSquare.getPiece().canBeCheck(board, kingSquare)) {
				check = true;
			}
			
			move.getStart().setPiece(pieceMoved);
			move.getEnd().setPiece(endPiece);    //return piece to original position
			
			if (check == true) return false;
			
			//Check if this move puts the enemy king in check
			
			//Move the piece
			move.getStart().setPiece(null);
			move.getEnd().setPiece(pieceMoved);
		}
		
		//Print the board
		this.printBoard(board);
		
		//Switch players once the move is made
		if (this.currentPlayer == p1) {
			this.currentPlayer = p2;
		}
		else {
			this.currentPlayer = p1;
		}
		
		//Check for check mate
		
		return true;
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
		//Move loop
		while (!chessGame.gameOver) {
			chessGame.playMove(chessGame.currentPlayer.generateMove(chessGame.board), p1, p2);
			
		}
	}
}
