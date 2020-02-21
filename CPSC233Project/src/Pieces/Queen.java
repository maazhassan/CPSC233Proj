package Pieces;

import Game.*;

public class Queen extends Piece {

	// Constructor
	public Queen(boolean isWhite) {
		super(isWhite);
	}

    @Override
    public char getPieceChar() {
        return 'q';
    }

	@Override
	public boolean canMove(Board board, Move move) {
		Bishop queenDiag = new Bishop(isWhite());
		Rook queenStraight = new Rook(isWhite());
		
		if (move.getStart().getX() == move.getEnd().getX() ^ move.getStart().getY() == move.getEnd().getY()) {
            System.out.println("rook");
		// Straight logic (same as rook)
		return queenStraight.canMove(board, move);
		}
		else {
            System.out.println("Diag");
		// Diagonal logic (same as bishop)
		return queenDiag.canMove(board, move);
		}
	}
}
