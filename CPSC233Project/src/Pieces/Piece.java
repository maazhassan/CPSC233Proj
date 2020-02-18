package Pieces;
import Game.*;

public abstract class Piece {
	private boolean white;
	private boolean alive;

    public Piece(boolean isWhite) {
        this.setWhite(isWhite);
    }
    
    public boolean isWhite() {
    	return this.white;
    }
    
    public void setWhite(boolean isWhite) {
    	this.white = isWhite;
    }

	//override this class in each subclass
	public abstract boolean canMove(Board board, Square start, Square end);
}
