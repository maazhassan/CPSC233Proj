package Game;

public abstract class Player {
	private boolean white;
	private boolean human;
	
	public boolean isWhite() {
		return this.white;
	}
	
	public void setWhite(boolean white) {
		this.white = white;
	}
	
	public boolean isHuman() {
		return this.human;
	}
	
	public void setHuman(boolean human) {
		this.human = human;
	}
	
	public abstract Move generateMove(Board board);
	
}
