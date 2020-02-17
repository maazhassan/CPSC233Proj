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
		
		System.out.println("Starting square x:");
		int startX = input.nextInt();
		
		System.out.println("Starting square y:");
		int startY = input.nextInt();
		
		System.out.println("Ending square x:");
		int endX = input.nextInt();
		
		System.out.println("Ending square y:");
		int endY = input.nextInt();
		
		Square startSquare = board.getSquare(startX, startY);
		Square endSquare = board.getSquare(endX, endY);
		
		Move move = new Move(startSquare, endSquare);
		return move;
	}
	
}
