package Pieces;

import Game.*;

public class King extends Piece {
	
    public King(boolean isWhite) {
        super(isWhite);
    }
    
    @Override
    public boolean canMove(Board board, Square start, Square end) {
        // 1. Verify that the end square isn't occupied by a piece of the same color
        if (end.getPiece().isWhite() == this.isWhite()) return false;
        
        // 2. Verify that it can move
        
        // The maximum distance it moves along the x-axis and the y-axis is 1
        if (Math.abs(end.getX() - start.getX()) <= 1 && Math.abs(end.getY() - start.getY()) <= 1) {
        	// Check if any piece on the board is able to attack the king
        	// if the king were to move to this square i.e. put the king in check
        	if (this.canBeCheck(board, end)) return false;
        	
        	return true;
        }
        else {
        	return false;
        }
    }
}
