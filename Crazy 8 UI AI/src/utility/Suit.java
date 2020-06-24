package utility;

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
