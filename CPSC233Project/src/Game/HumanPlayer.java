package Game;

import java.util.Scanner;

public class HumanPlayer extends Player {
	
	public HumanPlayer(boolean white) {
		this.setWhite(white);
		this.setHuman(true);
	}
	
	@Override
	public Move generateMove(Board board) {
		Scanner input = new Scanner(System.in);
		int startX;
		int startY;
		int endX;
		int endY;
		
		while (true) {
			System.out.println("Starting square x:");
			startX = input.nextInt();
			if (startX < 0 || startX > 7) {
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
