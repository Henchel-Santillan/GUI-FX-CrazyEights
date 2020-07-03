package main;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.URISyntaxException;

import javafx.geometry.Insets;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

import javafx.util.Duration;
import javafx.scene.transform.Rotate;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Interpolator;

public class Card {
	
	public static final double SKIN_WIDTH = 80.0d;
	public static final double SKIN_HEIGHT = 120.0d;
	
	public enum Rank {
		ACE(1, "A"), TWO(2, "2"), THREE(3, "3"), FOUR(4, "4"), FIVE(5, "5"),
		SIX(6, "6"), SEVEN (7, "7"), EIGHT(8, "8"), NINE(9, "9"), TEN(10, "10"), 
		JACK(11, "J"), QUEEN(12, "Q"), KING(13, "K");
		
		private final int value;
		private final String identifier;
		
		private Rank(int value, String identifier) {
			this.value = value;
			this.identifier = identifier;
		}
		
		public int getValue() {
			return value;
		}
		
		public String getID() {
			return identifier;
		}
	}
	
	public enum Suit {
		SPADES(1, "S"), CLUBS(2, "C"), 
		DIAMONDS(3, "D"), HEARTS(4, "H");
		
		private final int iterator;
		private final String identifier;
		
		private Suit(int iterator, String identifier) {
			this.iterator = iterator;
			this.identifier = identifier;
		}
		
		public int getIterator() {
			return iterator;
		}
		
		public String getID() {
			return identifier;
		}
	}
	
	//utility fields
	private final Rank rank;
	private final Suit suit;
	private State state;
	
	//GUI-related fields
	
	private final StackPane model;
	private final ImageView skin;
	private final Background entered, exited, clicked;
	private BooleanProperty isClicked, isFaceUp, isPlayable;
	
	public Card(Rank rank, Suit suit, State state) {
		this.rank = rank;
		this.suit = suit;
		this.state = state;
		
		skin = new ImageView();
		skin.setFitHeight(SKIN_HEIGHT);
		skin.setFitWidth(SKIN_WIDTH);
		skin.setSmooth(true);
		
		model = new StackPane(skin);
		model.setMaxWidth(SKIN_WIDTH);
		StackPane.setMargin(skin, new Insets(2.0d));
		
		isClicked = new SimpleBooleanProperty(false);
		isFaceUp = new SimpleBooleanProperty(false);
		isPlayable = new SimpleBooleanProperty(false);
		
		entered = new Background(new BackgroundFill(
				Color.rgb(178, 34, 34), CornerRadii.EMPTY, Insets.EMPTY));
		exited = new Background(new BackgroundFill(
				Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY));
		clicked = new Background(new BackgroundFill(
				Color.rgb(50, 205, 50), CornerRadii.EMPTY, Insets.EMPTY));
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
	}
	
	public State getState() {
		return state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public StackPane getModel() {
		return model;
	}
	
	public BooleanProperty isClickedProperty() {
		return isClicked;
	}
	
	public boolean isClicked() {
		return isClicked.get();
	}
	
	public void clicked() {
		try {
			AudioClip clip = new AudioClip(getClass().getResource(
					"/AUDIO/cardSlide1.wav").toURI().toString());
			clip.setCycleCount(1);
			clip.play();
			model.setBackground(clicked);
			
		} catch (URISyntaxException e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	public BooleanProperty isFaceUpProperty() {
		return isFaceUp;
	}
	
	public boolean isFaceUp() {
		return isFaceUp.get();
	}
	
	public void setFaceUp(){
		isFaceUp.set(true);
		
		try {
			skin.setImage(new Image(getClass().getResource(
					"/IMAGE/".concat(rank.getID() + suit.getID() + ".png")).toURI().toString()));
	
		} catch (URISyntaxException e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
    public void setFaceDown(){
    	isFaceUp.set(false);
    	
    	try {	//image should be set to "cardback" in the event a user changes design in the Library
			skin.setImage(new Image(getClass().getResource(
					"/IMAGE/card_back.png").toURI().toString()));
		} catch (URISyntaxException e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
		}
    }
    
	public BooleanProperty isPlayableProperty() {
		return isPlayable;
	}
	
	public boolean isPlayable() {
		return isPlayable.get();
	}
	
	public void setPlayable(boolean isPlayable) {
		this.isPlayable.set(isPlayable);
	}
	
	public void entered() { //card slide for enter, chip place for click?
		model.setBackground(entered);
	}
	
	public void exited() {
		model.setBackground(exited);
	}
	
	public void flip(){
		try {
			AudioClip clip = new AudioClip(getClass().getResource(
					"/AUDIO/flip.mp3").toURI().toString());
			clip.setCycleCount(1);
			clip.setRate(0.5);
			clip.play();
			
			skin.imageProperty().bind(Bindings.when(isFaceUp).then(
					new Image(getClass().getResource(
							"/IMAGE/".concat(rank.getID() + suit.getID() + ".png")).toURI().toString())).otherwise(
									new Image(getClass().getResource(
							"/IMAGE/card_back.png").toURI().toString())));
			
			skin.scaleXProperty().bind(Bindings.when(isFaceUp).then(1.0d).otherwise(-1.0d));
			
			RotateTransition rotateToSide = generateRotator(0, 90);
			
			if (isFaceUp.get()) {
				rotateToSide.setOnFinished(e -> this.isFaceUp.set(false));
				
			} else {
				rotateToSide.setOnFinished(e -> this.isFaceUp.set(true));
			}
			
			RotateTransition rotateToOpposite = generateRotator(90, 180);
			SequentialTransition seq = new SequentialTransition(skin, rotateToSide, rotateToOpposite);
			seq.setCycleCount(1);
			seq.play();
			
		} catch (URISyntaxException e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Card.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	 
	private RotateTransition generateRotator(double fromAngle, double toAngle) {
		RotateTransition rotator = new RotateTransition(Duration.seconds(0.5d), skin);
		
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setInterpolator(Interpolator.LINEAR);
		rotator.setFromAngle(fromAngle);
		rotator.setToAngle(toAngle);
		
		return rotator;
	}
}
