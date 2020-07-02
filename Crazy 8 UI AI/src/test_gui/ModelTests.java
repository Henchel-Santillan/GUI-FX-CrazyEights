package test_gui;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import utility_deprecated.Deck;
import utility_deprecated.Hand;
import utility_deprecated.Rank;
import utility_deprecated.Suit;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.PerspectiveCamera;

import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;

import java.net.URISyntaxException;

import gui.CardModel;
import gui.HandModel;
import gui.DeckModel;

public class ModelTests extends Application {

	Rectangle2D screen;
	Scene scene;
	BorderPane pane;
	Pane nPane;
	List<CardModel> cardList = new ArrayList<>();
	Deck deck;
	Hand hand;
	
	@Override
	public void start(Stage stage) throws URISyntaxException {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new BorderPane();
		
		nPane = new Pane();
		
		//scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		CardModel model1 = new CardModel(Rank.ACE, Suit.SPADES);
		model1.setFaceUp();
		
		CardModel model2 = new CardModel(Rank.ACE, Suit.CLUBS);
		model2.setFaceUp();
		
		genDeck();
		
		/*model1.flip();
		model.setOnEnter();
		model.setOnExit();
		model.setOnClick();*/
		
		HandModel handmodel = new HandModel();
		handmodel.add(model1);
		handmodel.add(model2);
		//handmodel.addAll(model1, model2);
		pane.setCenter(handmodel.getContainer());
		
		DeckModel deckmodel = new DeckModel();
		deckmodel.setItems(cardList);
		
		deckmodel.getSkin().setOnMousePressed(e -> {
			handmodel.add(deckmodel.pop());
		});
		
		pane.setBottom(deckmodel.getSkin());
		BorderPane.setAlignment(deckmodel.getSkin(), Pos.CENTER);
		
		nPane.getChildren().add(model1.getSkin());
		
		stage.setScene(scene);
		stage.setTitle("CardModel New Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void genDeck() {
		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = 0; j < Rank.values().length; j++) {
				cardList.add(new CardModel(Rank.values()[j], Suit.values()[i]));
			}
		}
	}

}
