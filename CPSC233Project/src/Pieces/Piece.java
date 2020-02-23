package Pieces;
import Game.*;

public abstract class Piece {
	private boolean white;

    public Piece(boolean isWhite) {
        this.setWhite(isWhite);
    }
    
    public boolean isWhite() {
    	return this.white;
    }
    
    public void setWhite(boolean isWhite) {
    	this.white = isWhite;
    }
    
    public abstract char getPieceChar();
    
    public boolean canBeCheck(Board board, Square kingSquare) {
    	for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			Square square = board.getSquare(x, y);
    			Piece pieceOnSquare = square.getPiece();
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() != this.isWhite()) {
                        //System.out.println(x + "," + y);
    					Move checkTestMove = new Move(square, kingSquare);
                        //System.out.printf("Move: %d %d -> %d %d\n", square.getX(), square.getY(), kingSquare.getX(), kingSquare.getY());
    					if (pieceOnSquare.canMove(board, checkTestMove)) return true;
    				}
    			}
    		}
    	}
    	return false;
    }

	//override this class in each subclass
	public abstract boolean canMove(Board board, Move move);
}
