package Game;

public class ComputerPlayer extends Player {
	
	public ComputerPlayer(boolean white) {
		this.setWhite(white);
		this.setHuman(false);
	}
	
	@Override
	public Move generateMove(Board board) {
		return null;
	}
}
