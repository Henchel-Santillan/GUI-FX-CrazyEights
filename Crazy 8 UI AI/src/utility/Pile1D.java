package utility;

import java.util.List;
import java.util.ArrayList;

public abstract class Pile1D {
	
	protected final List<Card> cardList;
	
	public Pile1D() {
		cardList = new ArrayList<>();
	}
	
	public List<Card> getCardList() {
		return cardList;
	}
	
	public abstract void push(Card card);
	public abstract void pushAll(List<Card> cardList);
	public abstract Card pop();
	public abstract List<Card> popAll();
}
