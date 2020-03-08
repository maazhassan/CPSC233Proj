package Game;
import java.util.ArrayList;

import Pieces.*;

/**
 * A computer player.
 */

public class ComputerPlayer extends Player {
	
	/**
	 * Creates a computer player.
	 * @param invalid True if white, false otherwise.
	 * @param b The difficulty of the AI - either 1,2, or 3.
	 */

	public ComputerPlayer(boolean invalid, int b) {
		this.setWhite(invalid);
		this.setHuman(false);
		this.setDifficulty(b);
	}

	//------------------------------ METHODS FOR AI ------------------------------//

	/**
	 * The initial minimax method used to start the algorithm and find the best move.
	 * @param depth The depth to search.
	 * @param board The current board state.
	 * @param isMaximizingPlayer True if the player is maximizing, false otherwise.
	 * @param isWhite True if the player is white, false otherwise.
	 * @return The best move able to be calculated.
	 */

	public Move minimaxInit(int depth, Board board, boolean isMaximizingPlayer, boolean isWhite) {
		ArrayList<Move> availableMoves = generateMovesList(board, isWhite);		
		Move bestMove = null;
		double bestMoveScore = -99999;

		for (Move move : availableMoves) {
			Piece pieceMoved = move.getStart().getPiece();
			Piece endPiece = move.getEnd().getPiece();

			Move.makeMove(move);
			double moveScore = minimax(depth-1, board, -99999, 99999, !isMaximizingPlayer, !isWhite, isWhite);
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
	 * @param isWhite True if the player is white, false otherwise. Switches everytime the algorithm searches deeper.
	 * @param originalWhite The original color of the player - does not change when the algorithm searches deeper.
	 * @return The score of the node as a double.
	 */

	public double minimax(int depth, Board board, double alpha, double beta, boolean isMaximizingPlayer, boolean isWhite, boolean originalWhite) {
		if (depth == 0) {
			if (!originalWhite) return -evaluateBoard(board);
			else return evaluateBoard(board);
		}

		ArrayList<Move> availableMoves = generateMovesList(board, isWhite);

		if (isMaximizingPlayer) {
			double bestMoveScore = -9999;
			for (Move move : availableMoves) {
				Piece pieceMoved = move.getStart().getPiece();
				Piece endPiece = move.getEnd().getPiece();

				Move.makeMove(move);
				bestMoveScore = Math.max(bestMoveScore, minimax(depth-1, board, alpha, beta, !isMaximizingPlayer, !isWhite, originalWhite));
				Move.undoMove(move, pieceMoved, endPiece);

				alpha = Math.max(alpha, bestMoveScore);     //update alpha
				if (alpha >= beta) return bestMoveScore;    //prune
			}
			return bestMoveScore;
		}
		else {
			double bestMoveScore = 9999;
			for (Move move : availableMoves) {
				Piece pieceMoved = move.getStart().getPiece();
				Piece endPiece = move.getEnd().getPiece();

				Move.makeMove(move);
				bestMoveScore = Math.min(bestMoveScore, minimax(depth-1, board, alpha, beta, !isMaximizingPlayer, !isWhite, originalWhite));
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
	 * @return The score of the board state as a double.
	 */

	public double evaluateBoard(Board board) {
		double boardScore = 0;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardScore += getPieceValue(board.getSquare(x, y));
			}
		}
		return boardScore;
	}

	/**
	 * Calculates the value of a piece.
	 * @param square The square that the piece is on.
	 * @return The calculated value of the piece (positive if white, negative if black).
	 */

	public double getPieceValue(Square square) {
		Piece pieceOnSquare = square.getPiece();
		if (pieceOnSquare == null) return 0;
		double absolutePieceValue = getAbsolutePieceValue(square, pieceOnSquare);

		if (pieceOnSquare.isWhite()) return absolutePieceValue;
		else return -absolutePieceValue;
	}

	/**
	 * Returns the absolute value of a piece (does not take color into account, always positive).
	 * @param square The square that the piece is on.
	 * @param piece The piece to calculate the value for.
	 * @return The absolute value of the piece.
	 */

	public double getAbsolutePieceValue(Square square, Piece piece) {
		int x = square.getX();
		int y = square.getY();

		if (piece instanceof Pawn) {
			if (piece.isWhite()) return 10 + PieceValues.pawnWhiteEval[y][x];
			else return 10 + PieceValues.pawnBlackEval[y][x];
		}
		else if (piece instanceof Knight) {
			return 30 + PieceValues.knightEval[y][x];
		}
		else if (piece instanceof Bishop) {
			if (piece.isWhite()) return 30 + PieceValues.bishopWhiteEval[y][x];
			else return 30 + PieceValues.bishopBlackEval[y][x];
		}
		else if (piece instanceof Rook) {
			if (piece.isWhite()) return 50 + PieceValues.rookWhiteEval[y][x];
			else return 50 + PieceValues.rookBlackEval[y][x];
		}
		else if (piece instanceof Queen) {
			return 90 + PieceValues.queenEval[y][x];
		}
		else {
			if (piece.isWhite()) return 900 + PieceValues.kingWhiteEval[y][x];
			else return 900 + PieceValues.kingBlackEval[y][x];
		}
	}
	
	@Override
	public Move generateMove(Board board) {
		Move bestMove = minimaxInit(this.getDifficulty()+1, board, true, this.isWhite());
		System.out.printf("Chosen move: %d %d -> %d %d\n", bestMove.getStart().getX(), bestMove.getStart().getY(), bestMove.getEnd().getX(), bestMove.getEnd().getY());
		return bestMove;
	}
}
