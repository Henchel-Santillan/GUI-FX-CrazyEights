package main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.StringJoiner;

public abstract class Pile1D {
	
	protected final ObservableList<Card> cardList;
	
	public Pile1D() {
		cardList = FXCollections.observableArrayList();
	}
	
	public ObservableList<Card> getCardList() {
		return cardList;
	}
	
	public boolean isEmpty() {
		return cardList.size() == 0;
	}
	
	public void push(Card card) {
		cardList.add(0, card);
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
