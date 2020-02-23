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
		for (int x = 0; x < 8; x++) {
    		for (int y = 0; y < 8; y++) {
    			//Finds the squares that have available pieces on them (not null, same color as player)
    			Piece pieceOnSquare = board.getSquare(x, y).getPiece();
    			if (pieceOnSquare != null) {
    				if (pieceOnSquare.isWhite() == this.isWhite()) {
    					//Adds the square to the squares ArrayList
    					squares.add(board.getSquare(x, y));
    				}
    			}
    		}
		}
		
		Move randMove = null;
		
		while (randMove == null) {
			//Generates a random index number
			int randSquareIndex = rand.nextInt(squares.size());
			
			//Returns the Square object at that index in the squares ArrayList
			Square randSquare = squares.remove(randSquareIndex);
			Piece randPiece = randSquare.getPiece();	//Then gets the piece that sits on that square

			//Loops through every square and checks if the piece can move to it
			//If it can, then adds a Move object to the moves ArrayList
			for (int x = 0; x < 8; x++) {
				for (int y = 0; y < 8; y++) {
					Move moveForRandomPiece = new Move(randSquare, board.getSquare(x, y));
					if (randPiece.canMove(board, moveForRandomPiece)) {
						moves.add(moveForRandomPiece);
					}
				}
			}
			
			//If there are no moves for the chosen random piece, choose another and try again
			if (moves.size() == 0) continue;
			
			
			//Generates a random index number, and returns the move at that index in the moves ArrayList
			int randMoveIndex = rand.nextInt(moves.size());
			randMove = moves.get(randMoveIndex);   //update condition
		}
		System.out.printf("Chosen move: %d %d -> %d %d\n", randMove.getStart().getX(), randMove.getStart().getY(), randMove.getEnd().getX(), randMove.getEnd().getY());
		return randMove;
	}
}
