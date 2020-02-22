package Pieces;

import Game.*;

public class Rook extends Piece {

	public Rook(boolean isWhite) {
		super(isWhite);
	}

    @Override
    public char getPieceChar() {
        return 'r';
    }
	
	@Override
	public boolean canMove(Board board, Move move) {
		// Make sure end square is not the same color as the piece.
		if (move.getEnd().getPiece() != null && move.getEnd().getPiece().isWhite() == this.isWhite()) {
			return false;
		}
		
		// coordinates
		int startX = move.getStart().getX();
		int endX = move.getEnd().getX();
		int startY = move.getStart().getY();
		int endY = move.getEnd().getY();
		
		if (startX == endX ^ startY == endY) {    //same square OR not along file
			if (startY == endY) {    // horizontally
				if (startX < endX) {    // move right
					for (int i = startX + 1; i < endX; i++) {
						if (board.getSquare(i, startY).getPiece() != null) {
							return false;
						}
					}
				}
				else {    //move left
					for (int i = startX - 1; i > endX; i--) {
						if (board.getSquare(i, startY).getPiece() != null) {
							return false;
						}
					}
				}
			}
			else if (startX == endX) {    //vertically
				if (startY > endY) {    //move up
					for (int i = startY - 1; i > endY; i--) {
						if (board.getSquare(startX, i).getPiece() != null) {
							return false;
						}
					}
				}
				else {    //move down
					for (int i = startY + 1; i < endY; i++) {
						if (board.getSquare(startX, i).getPiece() != null) {
							return false;
						}
					}
				}
			}
			return true;
		}
		else {
			return false;
		}
	}
}
