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
	private static IntegerProperty drawCount, skipCount;
	private static BooleanProperty suitChangeRequest;
	
	//TODO: Create model for Deck. Model for Dropzone is an inverted Deck model.
	public Dropzone() {
		drawCount = new SimpleIntegerProperty(0);
		skipCount = new SimpleIntegerProperty(0);
		suitChangeRequest = new SimpleBooleanProperty(false);
		
		model = new StackPane();
	}
	
	public StackPane getModel() {
		return model;
	}
	
	public static IntegerProperty drawCountProperty() {
		return drawCount;
	}
	
	public static int getDrawCount() {
		return drawCount.get();
	}
	
	public static IntegerProperty skipCountProperty() {
		return skipCount;
	}
	
	public static int getSkipCount() {
		return skipCount.get();
	}
	
	public static BooleanProperty suitChangeRequestProperty() {
		return suitChangeRequest;
	}
	
	public static boolean getSuitChangeRequest() {
		return suitChangeRequest.get();
	}
	
	@Override
	public void pushAll(List<Card> cardList) {
		for (Card card : cardList) {
			
			switch (card.getRank()) {
				case JACK:
					skipCount.set(skipCount.get() + 1);;
					break;
				case TWO:
					drawCount.set(drawCount.get() + 2);;
					break;
				case QUEEN:
					if (card.getSuit() == Suit.SPADES) drawCount.set(drawCount.get() + 5);
					break;
				case EIGHT:
					suitChangeRequest.set(true);
					break;
				default: break;
			}
			
			this.push(card);
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
	public int depthSearch() {
		int count = 1;
		int index = cardList.size() - 1;
		
		while (cardList.get(index).getRank() == cardList.get(index + 1).getRank()) {
			count++;
		}
		
		return count;
	}
}
