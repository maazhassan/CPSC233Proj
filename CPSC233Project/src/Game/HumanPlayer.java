package Game;

/**
 * Represents a human player.
 */

public class HumanPlayer extends Player {

	GameEventHandler handler;

	/**
	 * Creates a human player.
	 * @param white True if white, false otherwise.
	 */

	public HumanPlayer(GameEventHandler handler, boolean white) {
		this.handler = handler;

		this.setWhite(white);
		this.setHuman(true);
	}
	
	@Override
	public Move generateMove(Board board) {
		int[] moves;

		main: while (true) {
			moves = handler.createMove();

			for (int n : moves) {
				if (n < 0 || n > 7) {
					handler.log("Invalid move");
					continue main;
				}
			}
			break;
		}
		
		Square startSquare = board.getSquare(moves[0], moves[1]);
		Square endSquare = board.getSquare(moves[2], moves[3]);

		return new Move(startSquare, endSquare);
	}
	
}
