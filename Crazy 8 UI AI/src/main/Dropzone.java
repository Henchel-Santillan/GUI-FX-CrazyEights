package main;

import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.layout.StackPane;

import main.Card.Suit;

public class Dropzone extends Pile1D {

	private final StackPane model;
	private IntegerProperty drawCount, skipCount;
	private BooleanProperty requestSuitChange;
	
	private Suit changedSuit;
	
	//TODO: Create model for Deck. Model for Dropzone is an inverted Deck model.
	public Dropzone() {
		drawCount = new SimpleIntegerProperty(0);
		skipCount = new SimpleIntegerProperty(0);
		requestSuitChange = new SimpleBooleanProperty(false);
		
		model = new StackPane();
	}
	
	public StackPane getModel() {
		return model;
	}
	
	public Suit getChangedSuit() {
		return changedSuit;
	}
	
	public void setChangedSuit(Suit changedSuit) {
		this.changedSuit = changedSuit;
	}
	
	public IntegerProperty drawCountProperty() {
		return drawCount;
	}
	
	public int getDrawCount() {
		return drawCount.get();
	}
	
	public IntegerProperty skipCountProperty() {
		return skipCount;
	}
	
	public int getSkipCount() {
		return skipCount.get();
	}
	
	public BooleanProperty requestSuitChangeProperty() {
		return requestSuitChange;
	}
	
	public boolean requestSuitChange() {
		return requestSuitChange.get();
	}
	
	public void setRequestSuitChange(boolean requestSuitChange) {
		this.requestSuitChange.set(requestSuitChange);
	}
	
	@Override
	public void push(Card card) {
		super.push(card);
		int count = this.depthSearch();	//resets count everytime a card is pushed
		
		switch (card.getRank()) {
			case JACK:
				skipCount.set(count);
				break;
			case TWO:
				drawCount.set(2 * count);
				break;
			case QUEEN:
				if (card.getSuit() == Suit.SPADES) drawCount.set(drawCount.get() + 5);
				break;
			case EIGHT:
				requestSuitChange.set(true);
				break;
			default: break;
		}
	}
	
	//NOTE: must switch settings to off, then restart using depthSearch.
	@Override
	public void pushAll(List<Card> cardList) {
		int count = cardList.size();
		
		//do not push any cards yet, must conduct depthSearch on the pop() card
		//all cards in the cardList (playList) will have the same rank anyways
		if (this.pop().getRank() == cardList.get(0).getRank()) {
			count += this.depthSearch();
		}
		
		switch (cardList.get(0).getRank()) {
			case JACK:
				skipCount.set(count);
				break;
			case TWO:
				skipCount.set(2 * count);
				break;
			case QUEEN:
				if (cardList.get(cardList.size() - 1).getSuit() == Suit.SPADES) skipCount.set(skipCount.get() + 5);
				break;
			case EIGHT:
				requestSuitChange.set(true);
			default: break;
		}
		
		for (Card card : cardList) {
			super.push(card);
		}
	}
	
	public Card pop() {
		return cardList.get(cardList.size() - 1); //topmost card; not a traditional pop in the sense that it returns 
												  //but does not remove
	}
	
	//fromIndex = 0; toIndex = cardList.size() - this.depthSearch()
	public List<Card> popAll() {
		int fromIndex = 0;
		int toIndex = cardList.size() - this.depthSearch();
		model.getChildren().removeAll(model.getChildren().subList(fromIndex, toIndex));
		
		return new ArrayList<Card>(cardList.subList(fromIndex, toIndex));
	}
	
	//MAXIMUM NUMBER OF DUPLICATES IS 4: can create constraints to limit view into parent list
	/**Defines the maximum card depth from the top of the Dropzone where a rank discrepancy is first noted.*/
	public int depthSearch() {
		int count = 1;						//start at one since lastIn counts as 1 repetition + always 1 card in d-zone
		int index = cardList.size() - 2;	//index begins at lastIn + 1, or size - 2
		Card lastIn = this.pop();			//the card at the top of the Dropzone pile 
		
		//if index < 0 (i.e. there are less than 2 cards, the count will not run)
		while (index >= 0 && cardList.get(index).getRank() == lastIn.getRank()) {
			count++;
			index--;
		}
		
		return count;
	}
}
