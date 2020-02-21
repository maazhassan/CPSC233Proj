package Pieces;

import Game.*;

public class Queen extends Piece {

	// Constructor
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public boolean canMove(Board board, Move move) {
		Bishop queenDiag = new Bishop(isWhite());
		Rook queenStraight = new Rook(isWhite());
		
		if (move.getStart().getX() == move.getEnd().getX() || move.getStart().getY() == move.getEnd().getY()) {
		// Straight logic (same as rook)
		return queenStraight.canMove(board, move);
		}
		else {
		// Diagonal logic (same as bishop)
		return queenDiag.canMove(board, move);
		}
	}
}
