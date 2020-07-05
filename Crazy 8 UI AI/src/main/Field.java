package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;
import javafx.scene.control.ChoiceDialog;

import main.Card.Suit;

public class Field {

	private final Deck deck;
	private final Dropzone dropzone;
	
	private final List<Player> playerList;
	private Player current;
	private Suit changedSuit;
	
	private final BorderPane model;
	
	public Field() {
		deck = new Deck();
		dropzone = new Dropzone();
		
		playerList = new ArrayList<>();
		
		Region divider = new Region();
		divider.setPrefSize(Card.SKIN_WIDTH, Card.SKIN_HEIGHT);
		Group group = new Group(dropzone.getModel(), divider, deck.getModel());
		
		model = new BorderPane();
		model.setCenter(group);
		
		model.setBottom(playerList.get(0).getHand().getModel());
		model.setTop(playerList.get(1).getHand().getModel());
		
		switch (playerList.size()) {
			case 3:
				model.setLeft(playerList.get(2).getHand().getModel());
				break;
			case 4:
				model.setLeft(playerList.get(2).getHand().getModel());
				model.setRight(playerList.get(3).getHand().getModel());
		}
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public BorderPane getModel() {
		return model;
	}
	
	public Player nextPlayer() {
		if (Dropzone.getDrawCount() > 0) {
			playerList.get((playerList.indexOf(current) + 1) % 4).getHand().pushAll(deck.popAll()); 
		}
		
		if (Dropzone.requestSuitChange()) {
			List<String> choices = new ArrayList<>();
			
			for (Suit suit : Suit.values()) {
				choices.add(suit.getID());
			}
			
			ChoiceDialog<String> modal = new ChoiceDialog<>(dropzone.pop().getSuit().getID(), choices);
			modal.setTitle("Suit Change Request");
			modal.setHeaderText("Select a suit from the list below.");
			modal.setContentText("NOTE:\n'S' = Spades\n'C' = Clubs\n'D' = Diamonds\n'H = Hearts'");
			modal.initModality(Modality.APPLICATION_MODAL);
			
			
			Optional<String> result = modal.showAndWait();
			//cancelling or closing the dialog (on purpose or uneopectedly) will preserve the original suit
			//must set suitChange Request to False here.
			if (result.isPresent()) {
				switch (result.get()) {
					case "S":
						changedSuit = Suit.SPADES;
						break;
					case "C":
						changedSuit = Suit.CLUBS;
						break;
					case "D":
						changedSuit = Suit.DIAMONDS;
						break;
					case "H":
						changedSuit = Suit.HEARTS;
						break;
				}
			}
			//must set requestSuitChange to false 
		}
		
		return (Dropzone.getSkipCount() > 0) ? 
			playerList.get(((playerList.indexOf(current) + 1) + Dropzone.getSkipCount()) % playerList.size()) : 
			playerList.get((playerList.indexOf(current) + 1) % playerList.size());
	}
	
	/*public void put(Hand hand, Dropzone dropzone) {
		
	}*/
}
