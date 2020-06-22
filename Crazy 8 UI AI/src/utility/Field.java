package utility;

import java.util.List;
import java.util.ArrayList;

public class Field {
	
	private final Deck deck;
	private final Dropzone dropzone;
	private final List<Hand> hand_list;
	
	public Field(Deck deck, Dropzone dropzone) {
		this.deck = deck;
		this.dropzone = dropzone;
		
		hand_list = new ArrayList<>();
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Dropzone getDropzone() {
		return dropzone;
	}
	
	public List<Hand> getHandList() {
		return hand_list;
	}
}
