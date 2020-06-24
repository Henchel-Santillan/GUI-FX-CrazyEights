package utility;

import gui.CardModel;
import javafx.scene.Group;

public class Card {
	
	private final Rank rank;
	private final Suit suit;
	private State state;
	private boolean isFaceUp;
	private final CardModel model;
	
	public Card(Rank rank, Suit suit, State state) {
		this.rank = rank;
		this.suit = suit;
		this.state = state;
		
		model = new CardModel(rank, suit);
		
		/* Customize the model
		 * Bevelled or rounded edges
		 * Border-width and colour, shadowing
		 * White Background colour (contrast against scene, which will be green)
		 * buffer the image of the card and clip to rectangle model
		 * Height and width properties
		 * */
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public State getState() {
		return state;
	}
	
	//NOTE: tilt describes the 2D direction of the card relative to its face (faceup or facedown)
	public boolean isFaceUp() {
		return isFaceUp;
	}
	
	public void setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}
	
	public CardModel getModel() {
		return model;
	}
	
	/* Card class should have:
	 * design effects apply methods (e.g. on_hover, image render, on click or press, on drag, etc.)
	 * 
	 * */
}
