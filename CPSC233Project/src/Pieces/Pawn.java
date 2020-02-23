package Pieces;

import Game.*;

public class Pawn extends Piece {
	
	// Constructor
	public Pawn(boolean isWhite) {
		super(isWhite);
	}

    @Override
    public char getPieceChar() {
        return 'p';
    }

	@Override
	public boolean canMove(Board board, Move move) {
		// Get the end piece and its color and see if it is the same color as the piece
		// If the same color, then the piece can't move to that spot.
		if (move.getEnd().getPiece() != null && move.getEnd().getPiece().isWhite() == this.isWhite()) {
			return false;			
		}
		
		// Coordinates
		int startX = move.getStart().getX();
		int endX = move.getEnd().getX();
		int startY = move.getStart().getY();
		int endY = move.getEnd().getY();
		
		// Used to determine which direction the pawn is allowed to move
		boolean isWhite = move.getStart().getPiece().isWhite();
		boolean isBottomWhite = board.getIsBottomWhite();
		boolean movingUp;
		
		// Check direction
		if (startY - endY > 0) {    // moving upwards
			movingUp = true;
			if (isWhite != isBottomWhite) return false;
		}
		else if (startY - endY < 0) {    // moving downwards
			movingUp = false;
			if (isWhite == isBottomWhite) return false;
		}
		else {
			return false;
		}
					
		// Moving straight
		if (startX - endX == 0) {
			
			// Check end piece
			if (move.getEnd().getPiece() != null) return false;
			
			// Check move
			if (Math.abs(startY - endY) == 2) {    // moving 2 squares (first move)
				if (movingUp) {
					if (startY != 6 || board.getSquare(startX, 5).getPiece() != null) return false;
				}
				else {
					if (startY != 1 || board.getSquare(startX, 2).getPiece() != null) return false;
				}
			}
			else if (Math.abs(startY - endY) != 1) {
				return false;
			}
		}
		
		// Moving diagonal
		else if (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 1) {

			// Must be a killing move
			if (move.getEnd().getPiece() == null) return false;
		}
		else {
			return false;
		}
		
		return true;
	}
}

