package main;

import java.util.ArrayList;
import java.util.List;

public abstract class Pile1D {
	
	protected final List<Card> cardList;
	
	public Pile1D() {
		cardList = new ArrayList<>();
	}
	
	public List<Card> getCardList() {
		return cardList;
	}
	
	public void push(Card card) {
		cardList.add(card);
	}
	
	public void pushAll(List<Card> cardList) {
		for (Card card : cardList) {
			this.push(card);
		}
	}
	
	public abstract Card pop();
	public abstract List<Card> popAll();
}
