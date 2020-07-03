package main;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

import main.Card.Rank;
import main.Card.Suit;

public class Deck extends Pile1D {
	
	private final Random random = new Random();
	
	public static final int DECK_SIZE = 52;
	public static final int MIN_CAPACITY = 8;
	
	//use a fadetransition to make deck blink for prompt
	//if draw OR a move is made, set isOnPrompt to false
	//use a Binding to track change in state of isOnPrompt
	//if true play fadetransition; if false then call stop()
	private BooleanProperty isOnPrompt;

	private final StackPane model;
	
	public Deck() {
		isOnPrompt = new SimpleBooleanProperty(false);
		model = new StackPane();
		
		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = 0; j < Rank.values().length; j++) {
				
				Card card = new Card(Rank.values()[j], Suit.values()[i], State.NONE);
				cardList.add(card);
				model.getChildren().add(card.getModel());
			}
		}
	}
	
	public StackPane getModel() {
		return model;
	}
	
	@Override
	public void pushAll(List<Card> cardList) {
		
		for (int i = cardList.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			
			Card temp = cardList.get(i);
			cardList.set(i, cardList.get(j));
			cardList.set(j, temp);
		}
	
		super.pushAll(cardList);
	}
	
	//used for normal draw
	//TODO: add audio for draw
	public Card pop() {
		model.getChildren().remove(cardList.size() - 1);
		return cardList.remove(cardList.size() - 1);
	}
	
	//used when drawing more than one card: only case is when draw effects are resolved
	//TODO: Add audio for dealing
	public List<Card> popAll() {
		int fromIndex = cardList.size() - 1;
		int toIndex = Dropzone.getDrawCount();
		model.getChildren().removeAll(model.getChildren().subList(fromIndex, toIndex));
		return new ArrayList<Card>(cardList.subList(fromIndex, toIndex));
	}
	
	//TODO: add audio + animation for shuffle
	public void shuffle() {
		for (int i = cardList.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			
			Card temp = cardList.get(i);
			cardList.set(i, cardList.get(j));
			cardList.set(j, temp);
		}
		
		//shuffle animation for the model
	}
}
