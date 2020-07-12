package main;

import java.util.List;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.control.ScrollPane;

public abstract class Player {
	
	protected final State state;
	protected final Hand hand;
	
	protected BooleanProperty isSkipped, hasDrawn;
	
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
	
	public ScrollPane getHandModel() {
		return hand.getModel();
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
	
	//card = deck.pop()
	public void draw(Card card) {
		hand.push(card);
	}
	
	public void drawAll(List<Card> cardList) {
		hand.pushAll(cardList);
	}
	
	public Card play() {
		return hand.pop();
	}
	
	public List<Card> playAll() {
		return hand.popAll();
	}
	
	public void scan(Card lastIn) {
		hand.markAllEligible(lastIn);
	}
	
	public boolean isHuman() {
		return this instanceof HumanPlayer;
	}
	
	public boolean hasWon() {
		return hand.isEmpty();
	}
}
