package main;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javafx.collections.ListChangeListener;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.geometry.Insets;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;

import main.Card.Rank;
import main.Card.Suit;

public class Deck extends Pile1D {
	
	public static final int DECK_SIZE = 52;
	public static final int MIN_CAPACITY = 13;
	
	//use a fadetransition to make deck blink for prompt
	//if draw OR a move is made, set isOnPrompt to false
	//use a Binding to track change in state of isOnPrompt
	//if true play fadetransition; if false then call stop()
	
	//MAKE A toDraw field to indicate how much to deal
	private IntegerProperty toDeal;
	private BooleanProperty isOnPrompt;
	private final StackPane model;
	private final Label deckCounter;
	
	public Deck() {
		isOnPrompt = new SimpleBooleanProperty(true);
		model = new StackPane();
		
		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = 0; j < Rank.values().length; j++) {
				
				Card card = new Card(Rank.values()[j], Suit.values()[i], State.NONE);
				cardList.add(card);
				model.getChildren().add(card.getModel());
			}
		}
		
		//TODO: set CSS styling
		deckCounter = new Label(String.valueOf(cardList.size()));
		//add a listener to size everytime it changes
		cardList.addListener((ListChangeListener<Card>) c -> {
			deckCounter.setText(String.valueOf(c.getList().size()));
		});
		
		model.backgroundProperty().bind(Bindings.when(isOnPrompt).then(new Background(
				new BackgroundFill(Color.rgb(0, 255, 255), CornerRadii.EMPTY, Insets.EMPTY))).otherwise(
						new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY))));
		
		toDeal = new SimpleIntegerProperty(0);
	}
	
	public StackPane getModel() {
		return model;
	}
	
	public Label getCounter() {
		return deckCounter;
	}
	
	public IntegerProperty toDealProperty() {
		return toDeal;
	}
	
	public int toDeal() {
		return toDeal.get();
	}
	
	public void setToDeal(int toDeal) {
		this.toDeal.set(toDeal);
	}
	
	public BooleanProperty isOnPromptProperty() {
		return isOnPrompt;
	}
	
	public boolean isOnPrompt() {
		return isOnPrompt.get();
	}
	
	public void setIsOnPrompt(boolean isOnPrompt) {
		this.isOnPrompt.set(isOnPrompt);
	}
	
	@Override
	public void push(Card card) {
		card.setFaceDown();
		super.push(card);	//add card to bottom instead of the top
		model.getChildren().add(0, card.getModel());
	}
	
	@Override
	public void pushAll(List<Card> cardList) {
		Collections.shuffle(cardList);
	
		for (Card card : cardList) {
			this.push(card);
		}
	}
	
	//used for normal draw
	//TODO: add audio for draw
	public Card pop() {
		model.getChildren().remove(cardList.size() - 1);
		return cardList.remove(cardList.size() - 1);
	}
	
	public Card top() {
		return cardList.get(cardList.size() - 1);
	}
	
	//used when drawing more than one card: only case is when draw effects are resolved
	public List<Card> popAll() {
		int fromIndex = cardList.size() - 1;
		int toIndex = fromIndex - this.toDeal();	//toDeal() = Dropzone.getDrawCount()
		
		List<Card> popAllList = new ArrayList<>(cardList.subList(fromIndex, toIndex));
		
		model.getChildren().removeAll(model.getChildren().subList(fromIndex, toIndex));
		cardList.removeAll(popAllList);
		
		return popAllList;
	}
	
	//TODO: add animation for shuffle + sync with audio
	public void shuffle() {
		Collections.shuffle(cardList);
		
		for (int i = 0; i < model.getChildren().size(); i++) {
			model.getChildren().set(i, cardList.get(i).getModel());
		}
		
		try {
			AudioClip clip = new AudioClip(getClass().getResource(
					"/AUDIO/cardShuffle.wav").toURI().toString());
			clip.setCycleCount(1);
			clip.play();
			
		} catch (URISyntaxException e) {
			Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
}
