package test_gui;

import javafx.application.Application;
import javafx.stage.Stage;
import utility_deprecated.Deck;
import utility_deprecated.Hand;
import javafx.stage.Screen;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

public class NonModelTest extends Application {

	Scene scene;
	BorderPane pane;
	Rectangle2D screen;
	Deck deck;
	Hand hand;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new BorderPane();
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		deck = new Deck();
		pane.setBottom(deck.getModel().getSkin());
		BorderPane.setAlignment(deck.getModel().getSkin(), Pos.CENTER);
	
		hand = new Hand();
		pane.setCenter(hand.getModel().getContainer());
		
		deck.getModel().getSkin().setOnMousePressed( e-> {
			hand.add(deck.pop());
		});
		
		
		
		stage.setScene(scene);
		stage.setTitle("Nonmodel Tests");
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
