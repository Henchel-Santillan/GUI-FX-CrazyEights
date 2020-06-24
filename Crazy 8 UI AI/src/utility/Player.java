package utility;

public abstract class Player {
	
	private final State state;
	private final Hand hand;
	private boolean hasDrawn;
	
	public Player(State state) {
		this.state = state;
		
		hand = new Hand();
	}
	
	public State getState() {
		return state;
	}
	
	public Hand getHand() {
		return hand;
	}
	
	public boolean hasDrawn() {
		return hasDrawn;
	}
	
	public void setHasDrawn(boolean hasDrawn) {
		this.hasDrawn = hasDrawn;
	}
	
	public boolean isHuman() {
		return (this instanceof HumanPlayer);
	}

	public abstract void move();
}
