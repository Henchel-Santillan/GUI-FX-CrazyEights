package utility;

import gui.CardModel;
import javafx.scene.layout.StackPane;

public class Card {

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
	
	public enum Suit {
		SPADES(1, "S"), CLUBS(2, "C"), 
		DIAMONDS(3, "D"), HEARTS(4, "H");
		
		private final int iterator;
		private final String identifier;
		
		private Suit(int iterator, String identifier) {
			this.iterator = iterator;
			this.identifier = identifier;
		}
		
		public int getIterator() {
			return iterator;
		}
		
		public String getID() {
			return identifier;
		}
	}
	
	private final Rank rank;
	private final Suit suit;
	private final CardModel model;
	
	private State state;
	
	public Card(Rank rank, Suit suit, State state) {
		this.rank = rank;
		this.suit = suit;
		this.state = state;
		
		model = new CardModel(rank, suit);
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
	
	public StackPane getModelSkin() {
		return model.getSkin();
	}
}
