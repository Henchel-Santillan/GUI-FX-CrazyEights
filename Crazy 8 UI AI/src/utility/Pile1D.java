package utility;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Pile1D {

	protected final List<Card> card_set;
	protected final int initialSize;
	
	public Pile1D(int initialSize) {
		card_set = new ArrayList<Card>();
		
		this.initialSize = initialSize;
	}
	
	public List<Card> getCardSet() {
		return card_set;
	}
	
	public int getInitialSize() {
		return initialSize;
	}
	
	//Transfer a Card from one pile to another
	public void put(Pile1D other, Card card) {
		this.getCardSet().remove(card);
		((ArrayList<Card>) card_set).removeIf(Objects::isNull);
		((ArrayList<Card>) card_set).trimToSize();
		
		other.card_set.add(card);
	}
	
	//Transfer multiple cards from one pile to another
	public void putAll(Pile1D other, Card... cards) {
		for (int i = 0; i < cards.length; i++) {
			this.put(other, cards[i]);
		}
	}
	
	public void putAll(Pile1D other, List<Card> other_card_set) {
		for (int i = 0; i < other_card_set.size(); i++) {
			this.put(other, other_card_set.get(i));
		}
	}
	
}
