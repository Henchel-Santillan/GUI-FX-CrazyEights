package utility;

public class Hand extends Pile1D {
	
	public static final int DEF_SIZE = 8;
	
	private final State state;
	
	public Hand(State state) {
		super(DEF_SIZE);
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public void sortBySuit() {
		for (int i = 0; i < this.card_set.size(); i++) {
			
			Card key = this.card_set.get(i);
			int j = i - 1;
			
			while (j >= 0 && this.card_set.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				this.card_set.set(j + 1, this.card_set.get(j));
				j--;
			}
			
			this.card_set.set(j + 1, key);
		}
	}
	
	public void sortByRank() {
		for (int i = 1; i < this.card_set.size(); i++) {
			
			Card key = this.card_set.get(i);
			int j = i - 1;
			
			while (j >= 0 && this.card_set.get(j).getRank().getValue() > key.getRank().getValue()) {
				this.card_set.set(j + 1, this.card_set.get(j));
				j--;
			}
			
			this.card_set.set(j + 1, key);
		}
	}
	
	public void sortByAll() {
		for (int i =0; i < this.card_set.size(); i++) {
			
			Card key = this.card_set.get(i);
			int j = i - 1;
			
			while (j >= 0 && this.card_set.get(j).getRank().getValue() > key.getRank().getValue() &&
					this.card_set.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				this.card_set.set(j + 1, this.card_set.get(j));
				j--;
			}
				
			this.card_set.set(j + 1,  key);
		}
	}
}
