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

		
		
		if (startY == endY) {
			// horizontally
			if (startX < endX) {
				// move right
				for (int i = startX + 1; i <= endX; i++) {
					if (board.getSquare(startX, i).getPiece() != null) {
						return false;
					}
				}
			} else {
				// move left
				for (int i = startX - 1; i >= endX; i--) {
					if (board.getSquare(startX, i).getPiece() != null) {
						return false;
					}
				}
			}
			
		} else if (startX == endX) {
			//vertical
			if (startY < endY) {
				// move down
				for (int i = startY + 1; i <= endY; i++) {
					if (board.getSquare(i, startY).getPiece() != null) {
						return false;
					}
				}
			} else {
				// move forward
				for (int i = startY - 1; i >= endY; i--) {
					if (board.getSquare(i, startY).getPiece() != null) {
						return false;
					}
				}
			}
		}

        // check if it can move
		if (Math.abs(startX - endX) < 8 - startX && Math.abs(startY - endY) < 8 - startY) {
			return true;
		}
				
		return false;
	}
	
}
