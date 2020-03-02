package Game;
import java.util.ArrayList;

import Pieces.*;

/**
 * A computer player.
 */

public class ComputerPlayer extends Player {
	
	/**
	 * Creates a computer player.
	 * @param white True if white, false otherwise.
	 * @param difficulty The difficulty of the AI - either 1,2, or 3.
	 */

	public ComputerPlayer(boolean white, int difficulty) {
		this.setWhite(white);
		this.setHuman(false);
		this.setDifficulty(difficulty);
	}

	//------------------------------ METHODS FOR AI ------------------------------//

	/**
	 * Generates an ArrayList of all possible moves for the current board state.
	 * @param board The current board state.
	 * @return An ArrayList of Move objects.
	 */

	public ArrayList<Move> generateMovesList(Board board) {
		ArrayList<Move> availableMoves = new ArrayList<Move>();
		
		//Loops through every square on the board and adds all available moves to the ArrayList
		for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
				//Get square, then piece on square
				Square square = board.getSquare(x, y);
				Piece pieceOnSquare = square.getPiece();
				//Make sure piece is not null and matches player color
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() == this.isWhite()) {
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
	 * The initial minimax method used to start the algorithm and find the best move.
	 * @param depth The depth to search.
	 * @param board The current board state.
	 * @param isMaximizingPlayer True if the player is maximizing, false otherwise.
	 * @return The best move able to be calculated.
	 */

	public Move minimaxInit(int depth, Board board, boolean isMaximizingPlayer) {
		ArrayList<Move> availableMoves = generateMovesList(board);		
		Move bestMove = null;
		int bestMoveScore = -99999;

		for (Move move : availableMoves) {
			Piece pieceMoved = move.getStart().getPiece();
			Piece endPiece = move.getEnd().getPiece();

			Move.makeMove(move);
			int moveScore = minimax(depth-1, board, -99999, 99999, !isMaximizingPlayer);
			Move.undoMove(move, pieceMoved, endPiece);

			if (moveScore >= bestMoveScore) {
				bestMoveScore = moveScore;
				bestMove = move;
			}
		}
		return bestMove;
	}

	/**
	 * Recursive minimax method used to find the scores for nodes. Uses alpha-beta pruning.
	 * @param depth The depth to search.
	 * @param board The current board state.
	 * @param alpha The minimum score that the maximizing player is guaranteed.
	 * @param beta The maximum score that the minimizing player is guaranteed.
	 * @param isMaximizingPlayer True if the player is maximizing, false otherwise.
	 * @return The score of the node as an integer.
	 */

	public int minimax(int depth, Board board, int alpha, int beta, boolean isMaximizingPlayer) {
		if (depth == 0) {
			return -evaluateBoard(board);
		}

		ArrayList<Move> availableMoves = generateMovesList(board);

		if (isMaximizingPlayer) {
			int bestMoveScore = -9999;
			for (Move move : availableMoves) {
				Piece pieceMoved = move.getStart().getPiece();
				Piece endPiece = move.getEnd().getPiece();

				Move.makeMove(move);
				bestMoveScore = Math.max(bestMoveScore, minimax(depth-1, board, alpha, beta, !isMaximizingPlayer));
				Move.undoMove(move, pieceMoved, endPiece);

				alpha = Math.max(alpha, bestMoveScore);     //update alpha
				if (alpha >= beta) return bestMoveScore;    //prune
			}
			return bestMoveScore;
		}
		else {
			int bestMoveScore = 9999;
			for (Move move : availableMoves) {
				Piece pieceMoved = move.getStart().getPiece();
				Piece endPiece = move.getEnd().getPiece();

				Move.makeMove(move);
				bestMoveScore = Math.min(bestMoveScore, minimax(depth-1, board, alpha, beta, !isMaximizingPlayer));
				Move.undoMove(move, pieceMoved, endPiece);

				beta = Math.min(beta, bestMoveScore);       //update beta
				if (alpha >= beta) return bestMoveScore;    //prune
			}
			return bestMoveScore;
		}
	}

	/**
	 * Evaluates the score for the given board state.
	 * @param board The current board state.
	 * @return The score of the board state as an integer.
	 */

	public int evaluateBoard(Board board) {
		int boardScore = 0;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardScore += getPieceValue(board.getSquare(x, y).getPiece());
			}
		}
		return boardScore;
	}

	/**
	 * Calculates the value of a piece.
	 * @param piece The piece to calculate the value for.
	 * @return The calculated value of the piece (positive if white, negative if black).
	 */

	public int getPieceValue(Piece piece) {
		if (piece == null) return 0;
		int absolutePieceValue = getAbsolutePieceValue(piece);

		if (piece.isWhite()) return absolutePieceValue;
		else return -absolutePieceValue;
	}

	/**
	 * Returns the absolute value of a piece (does not take color into account, always positive).
	 * @param piece The piece to calculate the value for.
	 * @return The absolute value of the piece.
	 */

	public int getAbsolutePieceValue(Piece piece) {
		if (piece instanceof Pawn) {
			return 10;
		}
		else if (piece instanceof Knight) {
			return 30;
		}
		else if (piece instanceof Bishop) {
			return 30;
		}
		else if (piece instanceof Rook) {
			return 50;
		}
		else if (piece instanceof Queen) {
			return 90;
		}
		else return 900;
	}
	
	@Override
	public Move generateMove(Board board) {
		Move bestMove = minimaxInit(this.getDifficulty()+1, board, this.isWhite());
		System.out.printf("Chosen move: %d %d -> %d %d\n", bestMove.getStart().getX(), bestMove.getStart().getY(), bestMove.getEnd().getX(), bestMove.getEnd().getY());
		return bestMove;
	}
}
