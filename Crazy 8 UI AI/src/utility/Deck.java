package utility;

import java.util.Random;

public class Deck extends Pile1D {

	private final Random random = new Random();
	
	public static final int DEF_SIZE = 52;
	public static final int REC_MAX = 8;
	
	public Deck() {
		super(DEF_SIZE);
		
		//initialize Deck
		for (int i = 0; i < Card.Suit.values().length; i++) {
			for (int j = 0; j < Card.Rank.values().length; j++) {
				Card card = new Card(Card.Rank.values()[j], Card.Suit.values()[i], State.NONE);
				card.setFaceUp(false);
				
				this.card_set.add(card);
			}
		}
	}
	
	public void deal(Hand other) { 
		this.put(other, this.card_set.get(this.card_set.size() - 1));
	}
	
	public void deal(Hand other, int toAdd) {   
		for (int i = 0; i < toAdd; i++) {
			this.deal(other);
		}
	}
	
	public void shuffle() {
		for (int i = this.card_set.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			
			Card temp = this.card_set.get(i);
			this.card_set.set(i, this.card_set.get(j));
			this.card_set.set(j, temp);
		}
	}
}
