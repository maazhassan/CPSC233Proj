package Pieces;

import Game.*;

public class Queen extends Piece {

	// Constructor
	public Queen(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public boolean canMove(Board board, Square start, Square end) {
		// Diagonal logic (same as bishop)
		Bishop queenDiag = new Bishop(isWhite());
		queenDiag.canMove(board,start,end);
		
		// Straight logic (same as rook)
		Rook queenStraight = new Rook(isWhite());
		queenStraight.canMove(board,start,end);
		
	}

}
