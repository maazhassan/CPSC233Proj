package Pieces;
import Game.*;

public abstract class Piece {
	private boolean white;
	private char pieceChar = ' ';

    public Piece(boolean isWhite) {
        this.setWhite(isWhite);
    }
    
    public boolean isWhite() {
    	return this.white;
    }
    
    public void setWhite(boolean isWhite) {
    	this.white = isWhite;
    }
    
    public char getPieceChar() {
    	return this.pieceChar;
    }
    
    public void setPieceChar(char pieceChar, boolean white) {
    	this.pieceChar = pieceChar;
    	if (white) {
    		this.pieceChar = Character.toUpperCase(this.pieceChar);
    	}
    }

    public boolean canBeCheck(Board board, Square kingSquare) {
    	for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			Square square = board.getSquare(x, y);
    			Piece pieceOnSquare = square.getPiece();
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() != this.isWhite()) {
    					Move checkTestMove = new Move(square, kingSquare);
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
