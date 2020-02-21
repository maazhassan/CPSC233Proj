package Pieces;
import Game.*;

public class Knight extends Piece {
    public Knight(boolean isWhite) {
        super(isWhite);
    }
  
    @Override
    public char getPieceChar() {
        return 'n';
    }
    
    @Override
    public boolean canMove(Board board, Move move) {
        // 1. Verify that the end square isn't occupied
        if (move.getEnd().getPiece() != null && move.getEnd().getPiece().isWhite() == this.isWhite()) return false;
        
        // 2. Verify that it can move
        
        int deltaX = Math.abs(move.getEnd().getX() - move.getStart().getX());
        int deltaY = Math.abs(move.getEnd().getY() - move.getStart().getY());

        // A knight moves only by a ratio of 2:1 or 1:2. We'll divide the max
        // delta by the min delta to see if the ratio is 2:1 (which is 2).

        return (Math.max(deltaX, deltaY) / Math.min(deltaX, deltaY)) == 2;
    }
}
