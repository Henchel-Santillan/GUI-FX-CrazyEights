package main;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public abstract class Pile1D {
	
	protected final List<Card> cardList;
	
	public Pile1D() {
		cardList = new ArrayList<>();
	}
	
	public List<Card> getCardList() {
		return cardList;
	}
	
	public boolean isEmpty() {
		return cardList.size() == 0;
	}
	
	public void push(Card card) {
		cardList.add(card);
	}
	
	public void pushAll(List<Card> cardList) {
		for (Card card : cardList) {
			this.push(card);
		}
	}
	
	@Override
	public String toString() {
		StringJoiner joiner = new StringJoiner(",");
		
		for (Card card : cardList) {
			joiner.add(card.toString());
		}
		
		return joiner.toString();
	}
	
	public abstract Card pop();
	public abstract List<Card> popAll();
}
