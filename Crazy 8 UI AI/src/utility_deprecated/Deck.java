package utility_deprecated;

import java.util.Random;
import gui.DeckModel;

public class Deck extends Pile1D {

	private final Random random = new Random();
	
	public static final int DEF_SIZE = 52;
	public static final int REC_MAX = 8;
	
	private final DeckModel model;
	
	public Deck() {
		super(DEF_SIZE);
		
		model = new DeckModel();
		
		//initialize Deck
		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = 0; j < Rank.values().length; j++) {
				Card card = new Card(Rank.values()[j], Suit.values()[i], State.NONE);

				this.card_set.add(card);
				model.add(card.getModel());
			}
		}
	}
	
	public DeckModel getModel() {
		return model;
	}
	
	public Card pop() {
		return this.card_set.get(this.card_set.size() - 1);
	}
	
	public void shuffle() {
		for (int i = this.card_set.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			
			Card temp = this.card_set.get(i);
			this.card_set.set(i, this.card_set.get(j));
			this.card_set.set(j, temp);
		}
		
		model.shuffle();
	}
	
	//recycle
	public void add(Dropzone dropzone) {
		
	}
}
