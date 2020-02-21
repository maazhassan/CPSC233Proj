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
		
		if (startX == endX) return false;
		if (startY == endY) return false;
		
        int directionX = (endX - startX) / (int) Math.abs(endX - startX);
        int directionY = (endY - startY) / (int) Math.abs(endY - startY);
		
        for (int x = directionX, y = directionY;
        		((x + startX <= endX) ^ (x + startX >= endX)) && 
        		((y + startY <= endY) ^ (y + startY >= endY));
        		x += directionX, y += directionY ){
          	if (board.getSquare(startX + x, startY + y).getPiece() != null) {
            	return false;
            }
        }
		
		// Check if it can move
		if (Math.abs(startX - endX) / Math.abs(startY - endY) == 1) {
			return true;
		}
		
		return false;
	}
}
