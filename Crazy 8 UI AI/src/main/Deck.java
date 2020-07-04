package main;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

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
	public static final int MIN_CAPACITY = 8;
	
	//use a fadetransition to make deck blink for prompt
	//if draw OR a move is made, set isOnPrompt to false
	//use a Binding to track change in state of isOnPrompt
	//if true play fadetransition; if false then call stop()
	private BooleanProperty isOnPrompt;
	private final StackPane model;
	private final Label counter;	//TODO: customize label for increased visibility
	
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
		
		counter = new Label(String.valueOf(DECK_SIZE));
		model.getChildren().add(counter);
		
		model.backgroundProperty().bind(Bindings.when(isOnPrompt).then(new Background(
				new BackgroundFill(Color.rgb(0, 255, 255), CornerRadii.EMPTY, Insets.EMPTY))).otherwise(
						new Background(new BackgroundFill(Color.rgb(0, 0, 0), CornerRadii.EMPTY, Insets.EMPTY))));
	}
	
	public StackPane getModel() {
		return model;
	}
	
	public BooleanProperty isOnPromptProperty() {
		return isOnPrompt;
	}
	
	public boolean isOnPrompt() {
		return isOnPrompt.get();
	}
	
	public void setisOnPrompt(boolean isOnPrompt) {
		this.isOnPrompt.set(isOnPrompt);
	}
	
	@Override
	public void push(Card card) {
		model.getChildren().remove(counter);
		card.setFaceDown();
		super.push(card);
		counter.setText(String.valueOf(Double.valueOf(counter.getText()) + 1));
		model.getChildren().add(counter);
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
	
	//used when drawing more than one card: only case is when draw effects are resolved
	public List<Card> popAll() {
		int fromIndex = cardList.size() - 1;
		int toIndex = Dropzone.getDrawCount();
		model.getChildren().removeAll(model.getChildren().subList(fromIndex, toIndex));
		return new ArrayList<Card>(cardList.subList(fromIndex, toIndex));
	}
	
	//TODO: add audio + animation for shuffle
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
