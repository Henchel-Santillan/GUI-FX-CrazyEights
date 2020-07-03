package utility;

import java.util.List;
import java.util.ArrayList;

import gui.HandModel;

public class Hand extends Pile1D {

	public static final int DEFAULT_SIZE = 8;
	
	private final HandModel model;
	private final List<Card> toPop;	//secondary list for prepop setup
	
	public Hand() {
		super();
		toPop = new ArrayList<>();
		model = new HandModel();
	}
	
	public HandModel getModel() {
		return model;
	}
	
	public void push(Card card) {
		cardList.add(card);
		//model.push(card.getModel())
	}
	
	public void pushAll(List<Card> cardList) {
		for (Card card : cardList) {
			this.push(card);
		}
	}
	
	public Card pop() {
		return toPop.get(0);
	}
	
	public List<Card> popAll() {
		return toPop;
	}
	
	public void sortBySuit() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				cardList.set(j + 1, cardList.get(j));
				j--;
			}
			
			cardList.set(j + 1, key);
			//model.refresh(cardList);
		}
	}
	
	public void sortByRank() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getRank().getValue() > key.getRank().getValue()) {
				cardList.set(j + 1, cardList.get(j));
				j--;
			}
			
			cardList.set(j + 1, key);
			//this.getModel().refresh(cardList);
		}
	}
	
	public void sortByAll() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getRank().getValue() > key.getRank().getValue() &&
					cardList.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				cardList.set(j + 1, cardList.get(j));
				j--;
			}
				
			cardList.set(j + 1,  key);
			//this.getModel().refresh(cardList);
		}
	}
}
