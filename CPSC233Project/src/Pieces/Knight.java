package Pieces;
import Game.*;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }
    
    @Override
    public boolean canMove(Board board, Square start, Square end) {
        // 1. Verify that the end square isn't occupied
        if (end.getPiece().isWhite() == this.isWhite()) return false;
        
        // 2. Verify that it can move
        
        int deltaX = Math.abs(end.getX() - start.getX());
        int deltaY = Math.abs(end.getY() - start.getY());

        // A knight moves only by a ratio of 2:1 or 1:2. We'll divide the max
        // delta by the min delta to see if the ratio is 2:1 (which is 2).

        return (Math.max(deltaX, deltaY) / Math.min(deltaX, deltaY)) == 2;
    }
}
