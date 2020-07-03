package gui;

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

import utility.Card.Rank;
import utility.Card.Suit;

public class CardModel {
	
	public static final double PRINT_WIDTH = 80.0d;
	public static final double PRINT_HEIGHT = 120.0d;
	
	private final Rank modelRank;
	private final Suit modelSuit;
	
	private final StackPane skin;
	private final ImageView print;
	
	private BooleanProperty isClicked;
	private BooleanProperty isFaceUp;
	
	public CardModel(Rank modelRank, Suit modelSuit) {
		this.modelRank = modelRank;
		this.modelSuit = modelSuit;
		
		print = new ImageView();
		print.setFitWidth(PRINT_WIDTH);
		print.setFitHeight(PRINT_HEIGHT);
		print.setSmooth(true);
		
		skin = new StackPane(print);
		skin.setMaxWidth(PRINT_WIDTH);
		
		StackPane.setMargin(print, new Insets(2.0d));
		
		isClicked = new SimpleBooleanProperty(false);
		isFaceUp = new SimpleBooleanProperty(false);
	}
	
	Rank getModelRank() {
		return modelRank;
	}
	
	Suit getModelSuit() {
		return modelSuit;
	}
	
	public StackPane getSkin() {
		return skin;
	}
	
	public BooleanProperty isClickedProperty() {
		return isClicked;
	}
	
	public boolean isClicked() {
		return isClicked.get();
	}
	
	public void setClicked(boolean isClicked) {
		this.isClicked.set(isClicked);
	}
	
	public BooleanProperty isFaceUpProperty() {
		return isFaceUp;
	}
	
	public boolean isFaceUp() {
		return isFaceUp.get();
	}
	
	
}


