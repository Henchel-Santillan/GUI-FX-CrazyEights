package main;

public enum State {
	ONE("ONE"), TWO("TWO"), THREE("THREE"), FOUR("FOUR"), NONE("NONE");
	
	private final String identifier;
	
	private State(String identifier) {
		this.identifier = identifier;
	}
	
	public String getID() {
		return identifier;
	}
}

//will only be NONE if in Deck or Dropzone, since card will not belong to any of the players in this case only.

