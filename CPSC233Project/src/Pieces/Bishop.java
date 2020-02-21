package Pieces;

import Game.*;

public class Bishop extends Piece {

	// Constructor
	public Bishop(boolean isWhite) {
		super(isWhite);
	}

    @Override
    public char getPieceChar() {
        return 'b';
    }

	@Override
	public boolean canMove(Board board, Move move) {
		// Make sure the end square is not the same color as the piece.
		if (move.getEnd().getPiece() != null && move.getEnd().getPiece().isWhite() == this.isWhite()) {
			return false;
		}
		// Coordinates
		int startX = move.getStart().getX();
		int endX = move.getEnd().getX();
		int startY = move.getStart().getY();
		int endY = move.getEnd().getY();
		
		// Make sure squares leading up to the end square is not occupied.
		// Diagonal going like "/"
		for (int x = 0 ; x < 7 - endX ;x++) {
			for (int y = 0; y < 7 - endY ; y++) {
				int currentX = startX + x;
				int currentY = startY + y;
				if (board.getSquare(currentX, currentY).getPiece() != null) {
					return false;
				}
			}
		
		}
		
		// Diagonal going like "\"
		for (int x = 0 ; x < 7 - endX ;x++) {
			for (int y = 0; y < 7 - endY ; y++) {
				int currentX = startX - x;
				int currentY = startY + y;
				if (board.getSquare(currentX, currentY).getPiece() != null) {
					return false;
				}
			}
		
		}
		
		// Backwards
		// "/"
		for (int x = 0 ; x < 7 - endX ;x++) {
			for (int y = 0; y < 7 - endY ; y++) {
				int currentX = startX - x;
				int currentY = startY - y;
				if (board.getSquare(currentX, currentY).getPiece() != null) {
					return false;
				}
			}
		
		}
		
		// "\"
		for (int x = 0 ; x < 7 - endX ;x++) {
			for (int y = 0; y < 7 - endY ; y++) {
				int currentX = startX + x;
				int currentY = startY - y;
				if (board.getSquare(currentX, currentY).getPiece() != null) {
					return false;
				}
			}
		
		}
		
		// Check if it can move
		if (Math.abs(startX - endX) < 8 - startX && Math.abs(startY - endY) < 8 - startY) {
			return true;
		}
		
		return false;
	}
}
