package main;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.control.Button;

public abstract class Player {
	
	private final State state;
	private final Hand hand;
	
	private BooleanProperty isSkipped, hasDrawn;
	
	/**Constructor for class Player. Instantiates a Plater with a State and a Hand.
	 * @param state - The state of the Player. See enum State.
	 * @param hand - An empty Hand for the Player. see class Hand.
	 * */
	public Player(State state, Hand hand) {
		this.state = state;
		this.hand = hand;
		
		isSkipped = new SimpleBooleanProperty(false);
		hasDrawn = new SimpleBooleanProperty(false);
	}
	
	/**Gets the State of the Player.
	 * @return State 
	 **/
	public State getState() {
		return state;
	}
	
	/**Gets the Hand of the Player.
	 * @return Hand
	 * */
	public Hand getHand() {
		return hand;
	}
	
	/**Gets the FX BooleanProperty isSkipped.
	 * @return BooleanProperty*/
	public BooleanProperty isSkippedProperty() {
		return isSkipped;
	}
	
	public boolean isSkipped() {
		return isSkipped.get();
	}
	
	public void setIsSkipped(boolean isSkipped) {
		this.isSkipped.set(isSkipped);
	}
	
	public BooleanProperty hasDrawnProperty() {
		return hasDrawn;
	}
	
	public boolean hasDrawn() {
		return hasDrawn.get();
	}
	
	public void setHasDrawn(boolean hasDrawn) {
		this.hasDrawn.set(hasDrawn);
	}
	
	public boolean isHuman() {
		return this instanceof HumanPlayer;
	}
	
	public boolean hasWon() {
		return hand.isEmpty();
	}
	
	public void markReady(Button button) {
		hand.enable(button);
	}
}
