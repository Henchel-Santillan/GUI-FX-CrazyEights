package gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import utility.Card;
import utility.Rank;
import utility.Suit;
import utility.State;
import gui.CardModel;

public class CardImagePatternTest extends Application {

	Scene scene;
	Pane pane;
	Rectangle2D screen;
	ImagePattern imageP;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new Pane();
		
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		Card card1 = new Card(Rank.ACE, Suit.DIAMONDS, State.P_ONE);
		card1.getModel().set(screen.getWidth() * 0.5 - CardModel.MODEL_WIDTH,
				screen.getHeight() * 0.5 - CardModel.MODEL_HEIGHT);
		card1.getModel().setFaceUp();
	
		Card card2 = new Card(Rank.KING, Suit.SPADES, State.P_ONE);
		card2.getModel().set(screen.getWidth() * 0.5 + CardModel.MODEL_WIDTH, 
				screen.getHeight() * 0.5 - CardModel.MODEL_HEIGHT);
		card2.getModel().setFaceDown();
		
		Rectangle pat_test = new Rectangle(CardModel.MODEL_WIDTH, CardModel.MODEL_WIDTH);
		pat_test.setX(CardModel.MODEL_WIDTH);
		pat_test.setY(CardModel.MODEL_HEIGHT);
		
		try {
			imageP = new ImagePattern(new Image(
					getClass().getResource("/IMAGE/JH.png").toURI().toString()));
			pat_test.setFill(imageP);
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CardImagePatternTest.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardImagePatternTest.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		pane.getChildren().addAll(card1.getModel().get(), card2.getModel().get(), pat_test);
		
		stage.setScene(scene);
		stage.setTitle("Card Rotation Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
