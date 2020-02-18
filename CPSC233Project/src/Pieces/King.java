package Pieces;

import Game.*;

public class King extends Piece {
    public King(boolean isWhite) {
        super(isWhite);
    }

    public boolean canMove(Board board, Square start, Square end) {
        // 1. Verify that the end square isn't occupied
        if (end.getPiece() != null) return false;
        
        // 2. Verify that it can move
        
        // The maximum distance it moves along the x-axis and the y-axis is 1
        return Math.abs(end.getX() - start.getX()) <= 1 && Math.abs(end.getY() - start.getY()) <= 1;
    }
}
