package Game;
import Pieces.*;

import java.util.ArrayList;

/**
 * Main game class that puts all the other classes together.
 * Used to actually run the game.
 */

public class MainGame {

	private Board board;
	private boolean gameOver = false;
	private Player currentPlayer;
	private Player p1 = null;
	private Player p2 = null;
	private ArrayList<Board> boardStates = new ArrayList<Board>();
	private ArrayList<Board> undoneBoardStates = new ArrayList<Board>();

	private GameEventHandler handler;


	public MainGame(GameEventHandler handler, char p1Color, char p2Type, int aiDifficulty) {
		this.handler = handler;
		initializeGame(p1Color, p2Type, aiDifficulty);
	}

	public Player initializePlayers(boolean isP1, char p2Type, char p1Color, int AIdifficulty) {
		boolean p2isWhite;
		if (p1Color == 'w') p2isWhite = false;
		else p2isWhite = true;

		if (isP1) return initializeP1(p1Color);
		else {
			if (p2Type == 'c') return new ComputerPlayer(p2isWhite, AIdifficulty);
			else return new HumanPlayer(handler, p2isWhite);
		}
	}

	public HumanPlayer initializeP1(char color) {
		if (color == 'w') {
			return new HumanPlayer(handler, true);
		}
		else {
			return new HumanPlayer(handler,false);
		}
	}

	public void initializeGame(char p1Color, char p2Type, int aiDifficulty) {
		p1 = initializePlayers(true, p2Type, p1Color, aiDifficulty);
		p2 = initializePlayers(false, p2Type, p1Color, aiDifficulty);

		reset();
	}

	public void reset() {
		this.board = new Board(p1.isWhite());
		boardStates.clear();
		boardStates.add(new Board(this.board));

		if (p1.isWhite()) {
			this.currentPlayer = p1;
		}
		else {
			this.currentPlayer = p2;
		}

		p1.setCheck(false);
		p2.setCheck(false);

		gameOver = false;
	}

	public void switchPlayers() {
		if (this.currentPlayer == p1) this.currentPlayer = p2;
		else this.currentPlayer = p1;
	}

	public void alterBoardState(Move move) {
		if (move.isUndo()) {
			if (p2.isHuman()) {
				this.board = new Board(boardStates.get(boardStates.size()-2));
				undoneBoardStates.add(boardStates.remove(boardStates.size()-1));
			}
			else {
				this.board = new Board(boardStates.get(boardStates.size()-3));
				undoneBoardStates.add(boardStates.remove(boardStates.size()-1));
				boardStates.remove(boardStates.size()-2);
				switchPlayers();
			}
		}
		else {
			this.board = new Board(undoneBoardStates.get(undoneBoardStates.size()-1));
			boardStates.add(undoneBoardStates.remove(undoneBoardStates.size()-1));
			if (!p2.isHuman()) switchPlayers();
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
		handler.log(printedBoard);
	}

	public boolean playMove(Move move) {

		if((move.isUndo() && boardStates.size() > 1) || (move.isRedo()) && undoneBoardStates.size() > 0) {
			alterBoardState(move);
			return true;
		}
		else if (move.isUndo() || move.isRedo()) {
			handler.log("Turn " + currentPlayer.printColor() + ".");
			return false;
		}

		//The pieces involved in the move
		Piece pieceMoved = move.getStart().getPiece();
		Piece endPiece = move.getEnd().getPiece();

		boolean check = false;

		if (currentPlayer.isHuman()) {
			//Check if the piece is valid (belongs to the player and is not null)
			if (pieceMoved == null || pieceMoved.isWhite() != currentPlayer.isWhite()) {
				this.printBoard(board);
				handler.log("Invalid piece.");
				handler.log("Turn " + currentPlayer.printColor() + ".");
				return false;
			}

			//Check if the piece can move to that square
			if (!pieceMoved.canMove(board, move)) {
				this.printBoard(board);
				handler.log("That piece cannot move there.");
				handler.log("Turn " + currentPlayer.printColor() + ".");
				return false;
			}

			if (!move.isCastlingMove() && !(move.getStart().getPiece() instanceof King)) {
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
			boardStates.add(new Board(board));
			return true;
		}
		else if (!move.isCastlingMove()) {
			//Move the piece
			Move.makeMove(move);

			//Pawn promotion
			if (move.isPromotionMove()) move.getEnd().setPiece(new Queen(currentPlayer.isWhite()));

			boardStates.add(new Board(board));
			return true;
		}
		else return false;
	}

	//main method, runs the game
	public void start() {

		boolean playAgain = true;

		while (playAgain) {

			reset();

			//Move loop
			while (!gameOver) {

				//Print the board
				printBoard(board);

				//Print other info
				if (currentPlayer.isInCheck()) handler.log("Check.");
				handler.log("Turn " + currentPlayer.printColor() + ".");
				if (!currentPlayer.isHuman()) handler.log("Calculating...");

				//Ask for/play move
				boolean validMove = false;
				while (validMove == false) {
					currentPlayer.setCheck(false);
					validMove = playMove(currentPlayer.generateMove(board));
					if (currentPlayer.isInCheck()) {
						printBoard(board);
						handler.log("Cannot leave/put your king in check.");
						handler.log("Turn " + currentPlayer.printColor() + ".");
					}
				}

				//Switch player
				switchPlayers();

				//Check if this move puts the enemy king in check
				Square enemyKingSquare = currentPlayer.findKingSquare(board);
				if (enemyKingSquare.getPiece().canBeCheck(board, enemyKingSquare)) {
					currentPlayer.setCheck(true);
				}

				//Check if game is over
				boolean originalCheckState = currentPlayer.isInCheck();
				boolean checkMate = false;
				if (currentPlayer.generateMovesList(board, currentPlayer.isWhite()).size() == 0) {
					//Update game status to stop loop
					gameOver = true;

					//Determine if it is checkmate or stalemate
					if (currentPlayer.isInCheck()) checkMate = true;

					//Switch player
					switchPlayers();

					//Print board
					printBoard(board);

					//Print winning message
					if (checkMate)
					handler.log("Checkmate. " + currentPlayer.printColor().replace(currentPlayer.printColor().charAt(0),
										Character.toUpperCase(currentPlayer.printColor().charAt(0))) + " wins.");
					else handler.log("Stalemate.");
					//Ask to play again
					playAgain = handler.requestShouldPlayAgain();
				}
				currentPlayer.setCheck(originalCheckState);
			}
		}
	}
}
