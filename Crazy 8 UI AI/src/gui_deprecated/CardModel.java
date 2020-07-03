package gui_deprecated;

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
import utility_deprecated.Rank;
import utility_deprecated.Suit;
import javafx.scene.transform.Rotate;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Interpolator;

public class CardModel {

	public static final double PRINT_WIDTH = 80.0d;
	public static final double PRINT_HEIGHT = 120.0d;
	
	private final StackPane skin;
	private final ImageView print;
	private final Background onEnter, onExit, onClick;
	
	private final Rank rank;
	private final Suit suit;
	
	private BooleanProperty isClicked;
	private BooleanProperty isFaceUp;
	
	public CardModel(Rank rank, Suit suit) {

		this.rank = rank;
		this.suit = suit;
		
		print = new ImageView();
		print.setFitWidth(PRINT_WIDTH);
		print.setFitHeight(PRINT_HEIGHT);
		print.setSmooth(true);
		
		skin = new StackPane(print);
		skin.setMaxWidth(PRINT_WIDTH);
		
		StackPane.setMargin(print, new Insets(2.0d));
		
		isClicked = new SimpleBooleanProperty(false);
		isFaceUp = new SimpleBooleanProperty(false);
		
		this.setFaceDown();
		
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
	
	public void setClicked(boolean isClicked) {
		this.isClicked.set(isClicked);
	}
	
	public BooleanProperty isFaceUpProperty() {
		return isFaceUp;
	}
	
	public boolean isFaceUp() {
		return isFaceUp.get();
	}
	
	public void setFaceUp() {
		isFaceUp.set(true);
		
		try {
			print.setImage(new Image(getClass().getResource(
					"/IMAGE/".concat(rank.getID() + suit.getID() + ".png")).toURI().toString()));
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
	
	public void setFaceDown() {
		isFaceUp.set(false);
		
		try {	//image should be set to "cardback" in the event a user changes design in the Library
			print.setImage(new Image(getClass().getResource(
					"/IMAGE/card_back.png").toURI().toString()));
		} catch (URISyntaxException e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}
	
	public void setOnEnter() {
		skin.setBackground(onEnter);
	}
	
	public void setOnExit() {
		skin.setBackground(onExit);	//change to rgb of background
	}
	
	public void setOnClick() {
		
		try {
			AudioClip clip = new AudioClip(getClass().getResource(
					"/AUDIO/cardSlide1.wav").toURI().toString());
			clip.setCycleCount(1);
			clip.play();
			skin.setBackground(onClick);
		} catch (Exception e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	public void flip() {
	
		try {
			AudioClip clip = new AudioClip(getClass().getResource(
					"/AUDIO/flip.mp3").toURI().toString());
			clip.setCycleCount(1);
			clip.setRate(0.5);
			clip.play();
			
			print.imageProperty().bind(Bindings.when(isFaceUp).then(this.faceUp()).otherwise(this.faceDown()));
			print.scaleXProperty().bind(Bindings.when(isFaceUp).then(1.0d).otherwise(-1.0d));
			
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
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModel.class.getName()).log(Level.SEVERE, null, e);
			
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
	
	private Image faceUp() throws URISyntaxException {
		return new Image(getClass().getResource(
				"/IMAGE/".concat(rank.getID() + suit.getID() + ".png")).toURI().toString()); 
	}
	
	private Image faceDown() throws URISyntaxException {
		return new Image(getClass().getResource("/IMAGE/card_back.png").toURI().toString());
	}
}
