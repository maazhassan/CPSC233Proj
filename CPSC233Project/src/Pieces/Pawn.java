package Pieces;

import Game.*;

public class Pawn extends Piece {
	
	// Constructor
	public Pawn(boolean isWhite) {
		super(isWhite);
	}

	@Override
	public boolean canMove(Board board, Square start, Square end) {
		
		// Get the end piece and its color and see if it is the same color as the piece
		// If the same color, then the piece can't move to that spot.
		if (end.getPiece().isWhite() == this.isWhite()) {
			return false;			
		}
		
		// Coordinates
		int startX = start.getX();
		int endX = end.getX();
		int startY = start.getY();
		int endY = end.getY();
		
		// First pawn move (Two spaces forward)
		if (Math.abs(startY - endY) == 2 && Math.abs(startX - endX)== 0 && startY == 1 || startY == 6) {
			return true;
		}
		
		// Rest of pawn moves (One space forward)
		if (Math.abs(startY - endY) == 1 && Math.abs(startX - endX) == 0) {
			return true;
		}
		// If there is an opponent's piece diagonal to the pawn.
		if (end.getPiece() != null && end.getPiece().isWhite() != this.isWhite()) {
			if (Math.abs(startY - endY) == 1 && (startX-endX == 1 || startX-endX == -1)) {
				return true;
			}
		// If the pawn is at the end (can't move forward)
		if (startY == 0 || startY == 7) {
			return false;
			}
		}
		
		return false;
	}
}	
