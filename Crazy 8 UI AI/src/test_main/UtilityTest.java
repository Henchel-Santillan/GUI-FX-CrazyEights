package test_main;

import main.Hand;
import main.Card;
import main.Card.Suit;
import main.Card.Rank;
import main.Deck;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;

public class UtilityTest extends Application {

	Scene scene;
	BorderPane pane;
	Deck deck;
	Hand hand;
	
	@Override
	public void start(Stage stage) {
		pane = new BorderPane();
		deck = new Deck();
		hand = new Hand();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
