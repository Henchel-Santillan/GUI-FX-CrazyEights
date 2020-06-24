package test_gui;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.binding.Bindings;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.PerspectiveCamera;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image;

import utility.Card;
import utility.Rank;
import utility.Suit;
import utility.State;
import gui.CardModel;

import javafx.util.Duration;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.transform.Rotate;

public class CardRotation extends Application {
	
	BooleanProperty showFront = new SimpleBooleanProperty(true);
	
	Scene scene;
	Pane pane;
	Rectangle2D screen;
	Image front, back;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new Pane();
		
		scene = new Scene(pane, screen.getWidth(), screen.getHeight(), true, SceneAntialiasing.BALANCED);
		scene.setCamera(new PerspectiveCamera());
		
		Card card1 = new Card(Rank.ACE, Suit.DIAMONDS, State.P_ONE);
		card1.getModel().set(screen.getWidth() * 0.5 - CardModel.MODEL_WIDTH,
				screen.getHeight() * 0.5 - CardModel.MODEL_HEIGHT);
		card1.getModel().setFaceUp();
	
		Card card2 = new Card(Rank.KING, Suit.SPADES, State.P_ONE);
		card2.getModel().set(screen.getWidth() * 0.5 + CardModel.MODEL_WIDTH, 
				screen.getHeight() * 0.5 - CardModel.MODEL_HEIGHT);
		card2.getModel().setFaceDown();
		
		pane.getChildren().addAll(card1.getModel().get(), card2.getModel().get());
		
		front = card1.getModel().getImageView().getImage();
		back = card2.getModel().getImageView().getImage();
		
		resetCard(card1, showFront, front, back);
		RotateTransition rotatorFront = createRotator(card1, 0, 90);
		rotatorFront.setOnFinished(event -> showFront.set(false));
		
		RotateTransition rotatorBack = createRotator(card1, 90, 180);
		//rotatorBack.setOnFinished(event -> showFront.set(true));
		
		//RotateTransition rotateFinal = createRotator(card1, 180, 360);
		
		SequentialTransition seq = new SequentialTransition(card1.getModel().get(), rotatorFront, rotatorBack);
		seq.setCycleCount(1);
		seq.play();
		
		stage.setScene(scene);
		stage.setTitle("Card Rotation Test");
		stage.show();
	}
	
	private RotateTransition createRotator(Card card, double fromAngle, double toAngle) {
		RotateTransition rotator = new RotateTransition(Duration.seconds(2.0
				/*Math.abs((fromAngle - toAngle) / 360)*/), 
				card.getModel().get());
		
		rotator.setAxis(Rotate.Y_AXIS);
		rotator.setFromAngle(fromAngle);
		rotator.setToAngle(toAngle);
		rotator.setInterpolator(Interpolator.LINEAR);
		
		return rotator;
	}
	
	private void resetCard(Card card, BooleanProperty showFront, Image front, Image back) {
		card.getModel().getImageView().imageProperty().bind(
				Bindings.when(showFront).then(front).otherwise(back));
		
		card.getModel().getImageView().scaleXProperty().bind(
				Bindings.when(showFront).then(1.0d).otherwise(-1.0d));
	}

	public static void main(String[] args) {
		launch(args);
	}

}
