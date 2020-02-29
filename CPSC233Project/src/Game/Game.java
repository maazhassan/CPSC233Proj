package Game;
import Pieces.*;

import java.util.ArrayList;
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
	private Scanner input = new Scanner(System.in);
	
	public char askP2Type() {
		char p2Type = 'a';
		while (p2Type != 'c' && p2Type != 'h') {
			System.out.println("Play against computer or another human? (enter 'c' or 'h'):");
			p2Type = input.nextLine().charAt(0);
		}
		return p2Type;
	}

	public int askAIDifficulty(boolean p2IsComp) {
		int difficulty = 0;
		if (p2IsComp) {
			while (difficulty != 1 && difficulty != 2 && difficulty != 3) {
				System.out.println("Select computer difficulty (1,2,3):");
				difficulty = input.nextInt();
			}
		}
		return difficulty;
	}

	public char askP1Color() {
		char p1Color = 'a';
		while (p1Color != 'w' && p1Color != 'b') {
			System.out.println("Do you want to play as white or black? (enter 'w' or 'b'):");
			p1Color = input.next().charAt(0);
		}
		return p1Color;
	}

	public Player initializePlayers(boolean isP1, char p2Type, char p1Color, int AIdifficulty) {
		boolean p2isWhite;
		if (p1Color == 'w') p2isWhite = false;
		else p2isWhite = true;

		if (isP1) return initializeP1(p1Color);
		else {
			if (p2Type == 'c') return new ComputerPlayer(p2isWhite, AIdifficulty);
			else return new HumanPlayer(p2isWhite);
		}
	}

	public HumanPlayer initializeP1(char color) {
		if (color == 'w') {
			return new HumanPlayer(true);
		}
		else {
			return new HumanPlayer(false);
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
	}
	
	public void printBoard(Board board) {
		String printedBoard = "     0   1   2   3   4   5   6   7" + "\n";
		printedBoard = printedBoard + "   ---------------------------------";
		for (int y = 0; y < 8; y++) {
			
			printedBoard = printedBoard + "\n";
			printedBoard = printedBoard + y + "  |";
			for (int x = 0; x < 8; x++) {
				Piece pieceOnSquare = board.getSquare(x, y).getPiece();
				
				
				
				if (pieceOnSquare == null) {
					printedBoard = printedBoard + "   ";
					printedBoard = printedBoard + '|';
				}
				else {
					char pieceChar = pieceOnSquare.getPieceChar();
					if (pieceOnSquare.isWhite()) pieceChar = Character.toUpperCase(pieceChar);
					printedBoard = printedBoard + ' ' + pieceChar + ' ';
					printedBoard = printedBoard + '|';
				}
			}
			
			printedBoard = printedBoard + "\n";
			printedBoard = printedBoard + "   ---------------------------------";
		}
		System.out.println(printedBoard);
	}
	
	public boolean checkMate(Player otherPlayer) {
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square square = board.getSquare(x, y);
				Piece pieceOnSquare = square.getPiece();
				if (pieceOnSquare != null && pieceOnSquare.isWhite() == otherPlayer.isWhite()) {
					for (int x2 = 0; x2 < 8; x2++) {
						for (int y2 = 0; y2 < 8; y2++) {
							Move testMove = new Move(square, board.getSquare(x2, y2));
							if (pieceOnSquare.canMove(board, testMove) && !testMove.isCastlingMove()) {
								Piece pieceMoved = testMove.getStart().getPiece();
								Piece endPiece = testMove.getEnd().getPiece();
								if(playMove(testMove)) {
									availableMoves.add(testMove);
									Move.undoMove(testMove, pieceMoved, endPiece);
								}
							}
						}
					}
				}
			}
		}

		return (availableMoves.size() == 0);
	}

	public boolean playMove(Move move) {
		
		//The pieces involved in the move
		Piece pieceMoved = move.getStart().getPiece();
		Piece endPiece = move.getEnd().getPiece();
		
		boolean check = false;

		if (currentPlayer.isHuman()) {
			//Check if the piece is valid (belongs to the player and is not null)
			if (pieceMoved == null || pieceMoved.isWhite() != currentPlayer.isWhite()) {
				this.printBoard(board);
				System.out.println("Invalid piece.");
				System.out.println("Turn " + currentPlayer.printColor() + ".");
				return false;
			}
			
			//Check if the piece can move to that square
			if (!pieceMoved.canMove(board, move)) {
				this.printBoard(board);
				System.out.println("That piece cannot move there.");
				System.out.println("Turn " + currentPlayer.printColor() + ".");
				return false;
			}

			if (!move.isCastlingMove()) {
				// Make sure the player is not in check at the end of their move
				Move.makeMove(move);   //temporarily make the move
					
				Square ownKingSquare = currentPlayer.findKingSquare(board);   //find the square that has the king belonging to the player on it
				
				if (ownKingSquare.getPiece().canBeCheck(board, ownKingSquare)) {
					check = true;
				}
				
				Move.undoMove(move, pieceMoved, endPiece);    //return piece to original position
				
				if (check == true) {
					currentPlayer.setCheck(true);
					return false;
				}
			}
		}
		
		//Check if this move castles
		if (move.isCastlingMove() && !currentPlayer.isInCheck()) {
			Move.makeCastlingMove(board, move);
			return true;
		}
		else if (!move.isCastlingMove()) {
			//Move the piece
			Move.makeMove(move);

			//Pawn promotion
			if (move.isPromotionMove()) move.getEnd().setPiece(new Queen(currentPlayer.isWhite()));

			return true;
		}
		else return false;
	}
	
	//main method, runs the game
	public static void main(String[] args) {

		boolean playAgain = true;
		
		while (playAgain) {
			Game chessGame = new Game();
			Player p1 = null;
			Player p2 = null;
			char playAgainChar = 'a';
			char p2Type = chessGame.askP2Type();
			int AIdifficulty = chessGame.askAIDifficulty(p2Type == 'c');
			char p1Color = chessGame.askP1Color();
			
			//Creating the player objects
			
			p1 = chessGame.initializePlayers(true, p2Type, p1Color, AIdifficulty);
			p2 = chessGame.initializePlayers(false, p2Type, p1Color, AIdifficulty);
			
			//Initialize the board
			chessGame.initializeGame(p1, p2);
			
			//Move loop
			while (!chessGame.gameOver) {
				
				//Print the board
				chessGame.printBoard(chessGame.board);
				
				//Print other info
				if (chessGame.currentPlayer.isInCheck()) System.out.println("Check.");
				System.out.println("Turn " + chessGame.currentPlayer.printColor() + ".");
				if (!chessGame.currentPlayer.isHuman()) System.out.println("Calculating...");
				
				//Ask for/play move
				boolean validMove = false;
				while (validMove == false) {
					chessGame.currentPlayer.setCheck(false);
					validMove = chessGame.playMove(chessGame.currentPlayer.generateMove(chessGame.board));
					if (chessGame.currentPlayer.isInCheck()) {
						chessGame.printBoard(chessGame.board);
						System.out.println("Cannot leave/put your king in check.");
						System.out.println("Turn " + chessGame.currentPlayer.printColor() + ".");
					}
				}
				
				//Switch player
				if (chessGame.currentPlayer == p1) chessGame.currentPlayer = p2;
				else chessGame.currentPlayer = p1;
				
				//Check if this move puts the enemy king in check
				Square enemyKingSquare = chessGame.currentPlayer.findKingSquare(chessGame.board);
				if (enemyKingSquare.getPiece().canBeCheck(chessGame.board, enemyKingSquare)) {
					chessGame.currentPlayer.setCheck(true);
				}
				
				//Check for check mate
				boolean originalCheckState = chessGame.currentPlayer.isInCheck();
				if (chessGame.checkMate(chessGame.currentPlayer)) {
					//Update game status to stop loop
					chessGame.gameOver = true;
					// Switch player
					if (chessGame.currentPlayer == p1) chessGame.currentPlayer = p2;
					else chessGame.currentPlayer = p1;
					chessGame.printBoard(chessGame.board);
					//Print winning message
					System.out.println("Checkmate. " + chessGame.currentPlayer.printColor().replace(chessGame.currentPlayer.printColor().charAt(0), 
										Character.toUpperCase(chessGame.currentPlayer.printColor().charAt(0))) + " wins.");
					//Ask to play again
					System.out.println("\nDo you want to play again? (enter 'y' or 'n'):");
					while (playAgainChar != 'y' && playAgainChar != 'n') {
						playAgainChar = chessGame.input.nextLine().charAt(0);
					}
					if (playAgainChar == 'n') playAgain = false;
					System.out.println("\n");
				}
				chessGame.currentPlayer.setCheck(originalCheckState);
			}
		}
	}
}
