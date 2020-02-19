package Pieces;

import Game.*;

public class Bishop extends Piece {

	// Constructor
	public Bishop(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public boolean canMove(Board board, Square start, Square end) {
		// Make sure the end square is not the same color as the piece.
		if (end.getPiece().isWhite() == this.isWhite()) {
			return false;
		}
		// Coordinates
		int startX = start.getX();
		int endX = end.getX();
		int startY = start.getY();
		int endY = end.getY();
		
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
