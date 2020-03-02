package Game;
import java.util.ArrayList;

import Pieces.*;

/**
 * A computer player.
 */

public class ComputerPlayer extends Player {

	private int counter = 0;
	
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
	 * The initial minimax method used to start the algorithm and find the best move.
	 * @param depth The depth to search.
	 * @param board The current board state.
	 * @param isMaximizingPlayer True if the player is maximizing, false otherwise.
	 * @return The best move able to be calculated.
	 */

	public Move minimaxInit(int depth, Board board, boolean isMaximizingPlayer) {
		ArrayList<Move> availableMoves = generateMovesList(board, isMaximizingPlayer);		
		Move bestMove = null;
		int bestMoveScore = -99999;

		for (Move move : availableMoves) {
			Piece pieceMoved = move.getStart().getPiece();
			Piece endPiece = move.getEnd().getPiece();

			Move.makeMove(move);
			int moveScore = minimax(depth-1, board, -99999, 99999, !isMaximizingPlayer);
			System.out.println(moveScore);
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
			this.counter = this.counter + 1;
			return -evaluateBoard(board);
		}

		ArrayList<Move> availableMoves = generateMovesList(board, isMaximizingPlayer);

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
				boardScore += getPieceValue(board.getSquare(x, y));
			}
		}
		return boardScore;
	}

	/**
	 * Calculates the value of a piece.
	 * @param piece The piece to calculate the value for.
	 * @return The calculated value of the piece (positive if white, negative if black).
	 */

	public int getPieceValue(Square square) {
		Piece pieceOnSquare = square.getPiece();
		if (pieceOnSquare == null) return 0;
		int absolutePieceValue = getAbsolutePieceValue(pieceOnSquare);

		if (pieceOnSquare.isWhite()) return absolutePieceValue;
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
		System.out.println("Number of positions evaluated: " + this.counter);
		return bestMove;
	}
}
