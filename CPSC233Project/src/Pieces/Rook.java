package Pieces;

import Game.*;

public class Rook extends Piece {

	public Rook(boolean isWhite) {
		super(isWhite);
	}

    @Override
    public char getPieceChar() {
        return 'r';
    }
	
	@Override
	public boolean canMove(Board board, Move move) {
		// checking end square
		if (move.getEnd().getPiece() != null && move.getEnd().getPiece().isWhite() == this.isWhite()) {
			return false;
		}
		
		// coordinates
		int startX = move.getStart().getX();
		int endX = move.getEnd().getX();
		int startY = move.getStart().getY();
		int endY = move.getEnd().getY();
		
		// check if it can move
		if (Math.abs(startX - endX) < 8 - startX && Math.abs(startY - endY) < 8 - startY) {
			return true;
		}
		
		if (startX == endX) {
			// horizontally
			if (startY < endY) {
				// move right
				for (int i = startY + 1; i <= endY; i++) {
					if (board.getSquare(startY, i).getPiece() != null) {
						return false;
					}
				}
			} else {
				// move left
				for (int i = startY - 1; i >= endY; i++) {
					if (board.getSquare(startY, i).getPiece() != null) {
						return false;
					}
				}
			}
			
		} else if (startY == endY) {
			//vertical
			if (startX < endX) {
				// move backward
				for (int i = startX + 1; i <= endX; i++) {
					if (board.getSquare(i, startX).getPiece() != null) {
						return false;
					}
				}
			} else {
				// move forward
				for (int i = startX - 1; i >= endX; i++) {
					if (board.getSquare(i, startX).getPiece() != null) {
						return false;
					}
				}
			}
		}
				
		return false;
	}
	
}
