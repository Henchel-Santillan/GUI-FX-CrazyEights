package utility;

import gui.CardModel;

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
	
	public CardModel getModel() {
		return model;
	}
	
	public boolean isFaceUp() {
		return isFaceUp;
	}
	
	public void setFaceUp(boolean isFaceUp) {
		this.isFaceUp = isFaceUp;
	}
}
