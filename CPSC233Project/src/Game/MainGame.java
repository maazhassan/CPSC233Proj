package Game;

import Pieces.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import Launcher.JavaFXController;

/**
 * Main game class that controls the game in the back end.
 */

public class MainGame {

	private Board board;
	private Board temp;

	private boolean gameOver = false;
	private Player currentPlayer;
	private Player p1 = null;
	private Player p2 = null;
	private ArrayList<Board> boardStates = new ArrayList<Board>();
	private ArrayList<Board> undoneBoardStates = new ArrayList<Board>();
	Piece pieceMoved;
	Piece endPiece;

	private GameEventHandler handler;


	/**
	 * Constructor for game instance.
	 * @param handler The handler to be used for this instance. Could be command line or JavaFX handler.
	 * @param p1Color The color of player 1.
	 * @param p2Type The type of player 2.
	 * @param aiDifficulty The difficulty of the AI.
	 */

	public MainGame(GameEventHandler handler, char p1Color, char p2Type, int aiDifficulty) {
		this.handler = handler;
		initializeGame(p1Color, p2Type, aiDifficulty);
	}
	
	/**
	 * Method that intitializes the players.
	 * @param isP1 True if player 1 is being initialized.
	 * @param p2Type The type of player 2.
	 * @param p1Color The color of player 1.
	 * @param aiDifficulty The difficulty of the AI.
	 * @return The player instance.
	 */

	public Player initializePlayers(boolean isP1, char p2Type, char p1Color, int aiDifficulty) {
		boolean p2isWhite;
		if (p1Color == 'w') p2isWhite = false;
		else p2isWhite = true;

		if (isP1) return initializeP1(p1Color);
		else {
			if (p2Type == 'c') return new ComputerPlayer(p2isWhite, aiDifficulty);
			else return new HumanPlayer(handler, p2isWhite);
		}
	}

	/**
	 * Initializes player 1.
	 * @param color The color of player 1.
	 * @return A player one instance.
	 */

	public HumanPlayer initializeP1(char color) {
		if (color == 'w') {
			return new HumanPlayer(handler, true);
		}
		else {
			return new HumanPlayer(handler,false);
		}
	}

	/**
	 * Setup the game.
	 * @param p1Color The color of player 1.
	 * @param p2Type The type of player 2.
	 * @param aiDifficulty The difficulty of the AI.
	 */

	public void initializeGame(char p1Color, char p2Type, int aiDifficulty) {
		p1 = initializePlayers(true, p2Type, p1Color, aiDifficulty);
		p2 = initializePlayers(false, p2Type, p1Color, aiDifficulty);

		reset();
	}

	/**
	 * Resets the board, setting it up for the game.
	 */

	public void reset() {
		this.board = new Board(p1.isWhite());
		temp = new Board(board);
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

	/**
	 * Switches the current player to the other player.
	 */

	public void switchPlayers() {
		if (this.currentPlayer == p1) this.currentPlayer = p2;
		else this.currentPlayer = p1;
	}

	/**
	 * Alters the board state, performing and undo/redo operation.
	 * This is accomplished by checking 2 flag variables attached to
	 * the passed move object, and then performing the appropriate operation.
	 * @param move The move object, with either isUndo() or isRedo() being true.
	 */

	public void alterBoardState(Move move) {
		if (move.isUndo()) {
			if (p2.isHuman()) {
				this.board = new Board(boardStates.get(boardStates.size()-2));      //Set board one state before the current
				undoneBoardStates.add(boardStates.remove(boardStates.size()-1));    //Remove the current board state  
			}
			else {
				this.board = new Board(boardStates.get(boardStates.size()-3));      //Set board two states before the current
				undoneBoardStates.add(boardStates.remove(boardStates.size()-1));    //Remove the current board state
				undoneBoardStates.add(boardStates.remove(boardStates.size()-1));    //Remove the board state before the current as well
				switchPlayers();    //Since start() will switch them, and we want it to be the current players turn, we switch
			}
			handler.log("Move undone.");
		}
		else {
			if (p2.isHuman()) {
				boardStates.add(undoneBoardStates.remove(undoneBoardStates.size()-1));    //Restore the last state to the main list
				this.board = new Board(boardStates.get(boardStates.size()-1));            //Set the board to that state
			}
			else {
				boardStates.add(undoneBoardStates.remove(undoneBoardStates.size()-1));
				boardStates.add(undoneBoardStates.remove(undoneBoardStates.size()-1));    //Restore the last 2 states to the main list
				this.board = new Board(boardStates.get(boardStates.size()-1));            //Set the board to that state
				switchPlayers();
			}
			handler.log("Move redone.");
		}
	}

	/**
	 * Prints the board, used for the command line version.
	 * @param board The board to print.
	 */

	public void printBoard(Board board) {
		String printedBoard = "     a   b   c   d   e   f   g   h" + "\n";
		printedBoard = printedBoard + "   ---------------------------------";
		for (int y = 0; y < 8; y++) {

			printedBoard = printedBoard + "\n";
			printedBoard = printedBoard + (8-y) + "  |";
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
		if (!(handler instanceof JavaFXController)) handler.log(printedBoard);
	}

	/**
	 * Plays the given move after performing all appropriate checks.
	 * Used for moves inputted by a human player only.
	 * @param move The move to play.
	 * @return True if the move is valid -> the move is then played automatically.
	 * 		   If false is returned, the move is not played.
	 */

	public boolean playMove(Move move) {

		if((move.isUndo() && boardStates.size() > 1) || (move.isRedo()) && undoneBoardStates.size() > 0) {
			alterBoardState(move);
			return true;
		}
		else if (move.isUndo() || move.isRedo()) {
			if (!(handler instanceof JavaFXController)) handler.log("Turn " + currentPlayer.printColor() + ".");
			return false;
		}

		temp.copyFrom(board);
		Square tempStartSquare = temp.getSquare(move.getStart().getX(), move.getStart().getY());
		Square tempEndSquare = temp.getSquare(move.getEnd().getX(), move.getEnd().getY());
		Move tempMove = new Move(tempStartSquare, tempEndSquare);

		//The pieces involved in the move
		pieceMoved = move.getStart().getPiece();
		endPiece = move.getEnd().getPiece();

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
			if (!pieceMoved.canMove(temp, move)) {
				this.printBoard(board);
				handler.log("That piece cannot move there.");
				if (!(handler instanceof JavaFXController)) handler.log("Turn " + currentPlayer.printColor() + ".");
				return false;
			}

			if (!move.isCastlingMove() && !(move.getStart().getPiece() instanceof King)) {
				// Make sure the player is not in check at the end of their move
				Move.makeMove(tempMove);   //temporarily make the move

				Square ownKingSquare = currentPlayer.findKingSquare(temp);   //find the square that has the king belonging to the player on it

				if (ownKingSquare.getPiece().canBeCheck(temp, ownKingSquare)) {
					check = true;
				}

				Move.undoMove(tempMove, pieceMoved, endPiece);    //return piece to original position

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
			undoneBoardStates.clear();
			return true;
		}
		else if (!move.isCastlingMove()) {
			//Move the piece
			Move.makeMove(move);

			//Pawn promotion
			if (move.isPromotionMove()) move.getEnd().setPiece(new Queen(currentPlayer.isWhite()));

			boardStates.add(new Board(board));
			undoneBoardStates.clear();
			return true;
		}
		else return false;
	}

	/**
	 * 
	 * @return The main game board instance.
	 */

	public Board getBoard() {
		return this.board;
	}
	
	/**
	 * 
	 * @return The player 2 instance.
	 */

	public Player getPlayer2() {
		return p2;
	}

	/**
	 * 
	 * @return The current player instance.
	 */

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	/**
	 * 
	 * @return The current player's color as a string.
	 */

	public String getCurrentPlayerColor() {
		// If we are to implement 3 or 4-player chess, then we should use and return enums instead.
		return currentPlayer.isWhite() ? "White" : "Black";
	}

	/**
	 * 
	 * @return The number of the current player as an integer.
	 */

	public int getCurrentPlayerNumber() {
		return currentPlayer == p1 ? 1 : 2;
	}

	/**
	 * A method that writes the current state of the game to a file using the
	 * given file writer.
	 * @param writer The file writer to be used.
	 */

	public void writeToFile(FileWriter writer) {
		try {
			char p1Color = p1.isWhite() ? 'w' : 'b';
			char p2Type = p2.isHuman() ? 'h' : 'c';
			writer.write(p1Color + "\n");
			writer.write(p2Type + "\n");
			writer.write(p2.getDifficulty() + "\n");
			writer.write(getCurrentPlayerNumber() + "\n");
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Piece pieceOnSquare = board.getSquare(x, y).getPiece();
					if (pieceOnSquare == null) writer.write("null\n");
					else {
						String line = Character.toString(pieceOnSquare.getPieceChar()) + "," + Boolean.toString(pieceOnSquare.isWhite());
						writer.write(line + "\n");
					}
				}
			}
			handler.log("Saved!");
			writer.close();
		}
		catch (IOException e) {
			throw new RuntimeException("Cannot find file.");
		}
	}

	/**
	 * A method that creates a file writer, links it to the save.dat file
	 * and then writes the current state of the game to that file.
	 */

	public void saveGame() {
		try {
			FileWriter writer = new FileWriter("CPSC233Project/save.dat", false);
			writeToFile(writer);
		}
		catch (IOException e) {
			try {
				FileWriter writer = new FileWriter("save.dat", false);
				writeToFile(writer);
			}
			catch (IOException a) {
				throw new RuntimeException("Cannot find file.");
			}
		}
	}

	/**
	 * A method that loads the game saved in the save.dat file.
	 */

	public void loadGame() {
		Scanner fileScanner;
		try {
			fileScanner = new Scanner(new File("CPSC233Project/save.dat"));
		}
		catch (FileNotFoundException e) {
			try {
				fileScanner = new Scanner(new File("save.dat"));
			}
			catch (FileNotFoundException a) {
				throw new RuntimeException("File not found.");
			}
		}
		fileScanner.nextLine();
		fileScanner.nextLine();
		fileScanner.nextLine();
		currentPlayer = fileScanner.nextLine().equals("1") ? p1 : p2;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				String squareInfo = fileScanner.nextLine();
				Square square = board.getSquare(x, y);
				square.setPiece(null);
				if (!squareInfo.equals("null")) {
					String[] squareInfoArray = squareInfo.split(",");
					String pieceChar = squareInfoArray[0];
					Boolean isWhite = Boolean.parseBoolean(squareInfoArray[1]);

					switch(pieceChar) {
						case "p":
							square.setPiece(new Pawn(isWhite));
							break;
						case "n":
							square.setPiece(new Knight(isWhite));
							break;
						case "b":
							square.setPiece(new Bishop(isWhite));
							break;
						case "r":
							square.setPiece(new Rook(isWhite));
							break;
						case "q":
							square.setPiece(new Queen(isWhite));
							break;
						case "k":
							square.setPiece(new King(isWhite));
							break;
					}
				}
			}
		}
		boardStates.clear();
		boardStates.add(new Board(board));
	}

	public String moveNotation(Piece pieceMoved, Piece endPiece, Move move) {
		if (move.isCastlingMove()) return "O-O";

		char pieceChar = Character.toUpperCase(pieceMoved.getPieceChar());
		String first = pieceMoved instanceof Pawn ? "" : Character.toString(pieceChar);

		String kill = endPiece != null ? "x" : "";

		Square startSquare = move.getStart();
		Square endSquare = move.getEnd();

		char secondLetter = (char)(endSquare.getX() + 'a');
		int secondNumber = 8 - endSquare.getY();

		if (pieceMoved instanceof Pawn && !kill.equals("")) {
			char pawnLetter = (char)(startSquare.getX() + 'a');
			kill = pawnLetter + "x";
		}

		//String checkPlus = check == true ? "+" : "";

		return first + kill + secondLetter + secondNumber;
	}

	/**
	 * Start method, entry point for the back end game instance.
	 */
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
				if (!(handler instanceof JavaFXController)) handler.log("Turn " + currentPlayer.printColor() + ".");
				if (!currentPlayer.isHuman() && !(handler instanceof JavaFXController)) handler.log("Calculating...");

				//Ask for/play move
				boolean validMove = false;
				Move move = null;
				while (validMove == false) {
					currentPlayer.setCheck(false);
					move = currentPlayer.generateMove(board);
					validMove = playMove(move);
					if (currentPlayer.isInCheck()) {
						printBoard(board);
						handler.log("Cannot leave/put your king in check.");
						if (!(handler instanceof JavaFXController)) handler.log("Turn " + currentPlayer.printColor() + ".");
					}
				}

				String moveLog = moveNotation(pieceMoved, endPiece, move);

				//Switch player
				switchPlayers();

				//Check if this move puts the enemy king in check
				Square enemyKingSquare = currentPlayer.findKingSquare(board);
				if (enemyKingSquare.getPiece().canBeCheck(board, enemyKingSquare)) {
					currentPlayer.setCheck(true);
					moveLog += "+";
				}

				handler.moveLog(moveLog, currentPlayer != p1);

				//Check if game is over
				boolean originalCheckState = currentPlayer.isInCheck();
				boolean checkMate = false;
				String winMessage;
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
					winMessage = "Checkmate. " + currentPlayer.printColor().replace(currentPlayer.printColor().charAt(0),
								Character.toUpperCase(currentPlayer.printColor().charAt(0))) + " wins.";
					else winMessage = "Stalemate.";
					//Ask to play again
					playAgain = handler.requestShouldPlayAgain(winMessage);
				}
				currentPlayer.setCheck(originalCheckState);
			}
		}
	}
}
