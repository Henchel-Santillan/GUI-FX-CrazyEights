package gui;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.net.URISyntaxException;

import javafx.geometry.Insets;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import utility.Rank;
import utility.Suit;

public class CardModel {

	public static final double PRINT_WIDTH = 80.0d;
	public static final double PRINT_HEIGHT = 120.0d;
	
	private final StackPane skin;
	private final ImageView print;
	private final Background onEnter, onExit, onClick;
	
	private final Rank rank;
	private final Suit suit;
	
	private BooleanProperty isClicked;
	
	public CardModel(Rank rank, Suit suit) {

		this.rank = rank;
		this.suit = suit;
		
		print = new ImageView();
		
		skin = new StackPane(print);
		skin.setMaxWidth(PRINT_WIDTH);
		
		try {
			print.setImage(new Image(getClass().getResource(
					"/IMAGE/".concat(rank.getID() + suit.getID() + ".png")).toURI().toString()));
			
			print.setFitWidth(PRINT_WIDTH);
			print.setFitHeight(PRINT_HEIGHT);
			print.setSmooth(true);
			
			StackPane.setMargin(print, new Insets(2.0d));
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		isClicked = new SimpleBooleanProperty(false);
		
		onEnter = new Background(new BackgroundFill(
				Color.rgb(178, 34, 34), CornerRadii.EMPTY, Insets.EMPTY));
		onExit = new Background(new BackgroundFill(
				Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY));
		onClick = new Background(new BackgroundFill(
				Color.rgb(50, 205, 50), CornerRadii.EMPTY, Insets.EMPTY));
	}
	
	public Rank getRank() {
		return rank;
	}
	
	public Suit getSuit() {
		return suit;
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
	
	public void setOnEnter() {
		if(!isClicked.get()) {
			skin.setBackground(onEnter);
		}
	}
	
	public void setOnExit() {
		if (!isClicked.get()) {
			skin.setBackground(onExit);	//change to rgb of background
		}
	}
	
	public void setOnClick() {
		isClicked.set(true);
		skin.setBackground(onClick);
	}
	
	public void flip() {
		
	}
}
