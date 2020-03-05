package Game;

import java.util.Scanner;

/**
 * Represents a human player.
 */

public class HumanPlayer extends Player {

	private Scanner input = new Scanner(System.in);
	
	/**
	 * Creates a human player.
	 * @param white True if white, false otherwise.
	 */

	public HumanPlayer(boolean white) {
		this.setWhite(white);
		this.setHuman(true);
	}
	
	@Override
	public Move generateMove(Board board) {
		
		int startX;
		int startY;
		int endX;
		int endY;
		
		while (true) {
			System.out.println("Type '8' to undo, '9' to redo.\n");
			System.out.println("Starting square x:");
			startX = input.nextInt();
			if (startX == 8) { 
				Move undoMove = new Move(board.getSquare(0, 0), board.getSquare(0, 0));
				undoMove.setUndo(true);
				return undoMove;
			}
			else if (startX == 9) { 
				Move redoMove = new Move(board.getSquare(0, 0), board.getSquare(0, 0));
				redoMove.setRedo(true);
				return redoMove;
			}
			else if (startX < 0 || startX > 7) {
				System.out.println("Invalid index.");
				continue;
			}
			
			System.out.println("Starting square y:");
			startY = input.nextInt();
			if (startY < 0 || startY > 7) {
				System.out.println("Invalid index.");
				continue;
			}
			
			System.out.println("Ending square x:");
			endX = input.nextInt();
			if (endX < 0 || endX > 7) {
				System.out.println("Invalid index.");
				continue;
			}
			
			System.out.println("Ending square y:");
			endY = input.nextInt();
			if (endY < 0 || endY > 7) {
				System.out.println("Invalid index.");
				continue;
			}
			break;
		}
		
		Square startSquare = board.getSquare(startX, startY);
		Square endSquare = board.getSquare(endX, endY);
		
		Move move = new Move(startSquare, endSquare);
		return move;
	}
	
}
