package Game;
import java.util.ArrayList;
import java.util.Random;

import Pieces.*;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(boolean white) {
		this.setWhite(white);
		this.setHuman(false);
	}
	
	@Override
	public Move generateMove(Board board) {

		ArrayList<Square> squares = new ArrayList<Square>();
		ArrayList<Move> moves = new ArrayList<Move>();
		Random rand = new Random();
		
		//Loops through every square on the board
		//Finds the squares that have available pieces on them (not null, same color as player)
		//Adds the square to the squares ArrayList
		for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			Piece pieceOnSquare = board.getSquare(x, y).getPiece();
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() == this.isWhite()) {
    					squares.add(board.getSquare(x, y));
    				}
    			}
    		}
		}
		
		//Generates a random index number
		//Returns the Square object at that index in the squares ArrayList
		//Then gets the piece that sits on that square
		int randSquareIndex = rand.nextInt(squares.size());
		Square randSquare = squares.get(randSquareIndex);
		Piece randPiece = randSquare.getPiece();
		
		//Loops through every square and checks if the piece can move to it
		//If it can, then adds a Move object to the moves ArrayList
		for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			if (randPiece.canMove(board, randSquare, board.getSquare(x, y))) {
    				moves.add(new Move(randSquare, board.getSquare(x, y)));
    			}
    		}
		}
		
		//Generates a random index number, and returns the move at that index in the moves ArrayList
		int randMoveIndex = rand.nextInt(moves.size());
		return moves.get(randMoveIndex);
	}
}
