package utility;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Card {
	
	//needs image resource links
	public enum Rank {
		ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"),
		SIX(6, "6"), SEVEN (7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"), 
		JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");
		
		private final int value;
		private final String identifier;
		
		private Rank(int value, String identifier) {
			this.value = value;
			this.identifier = identifier;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getID() {
			return identifier;
		}
	}
	
	//Each should have own image (Buffer)
	public enum Suit {
		SPADES(Color.BLACK, 1), CLUBS(Color.BLACK, 2), DIAMONDS(Color.RED, 3), HEARTS(Color.RED, 4);
		
		private final Color color;
		private final int iterator;
		
		private Suit(Color color, int iterator) {
			this.color = color;
			this.iterator = iterator;
		}
		
		public Color getColor() {
			return color;
		}
		
		public int getIterator() {
			return iterator;
		}
	}
	
	private final Rank rank;
	private final Suit suit;
	private State state;
	private boolean isFaceUp;
	private final Rectangle model;
	
	public Card(Rank rank, Suit suit, State state) {
		this.rank = rank;
		this.suit = suit;
		this.state = state;
		
		model = new Rectangle();
		
		model.setFill(Color.WHITE);
		model.setStroke(Color.BLACK);
		model.setStrokeWidth(2.0);
		
		
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
	
	public Rectangle getModel() {
		return model;
	}
	
	/* Card class should have:
	 * design effects apply methods (e.g. on_hover, image render, on click or press, on drag, etc.)
	 * 
	 * */
}
