package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import javafx.scene.Group;

import javafx.stage.Modality;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Button;

import main.Card.Suit;
import main.Card.Rank;

public class Field {

	private final Deck deck;
	private final Dropzone dropzone;
	
	private final List<Player> playerList;
	private Player current;
	
	private final BorderPane model;
	private final Button confirm;
	
	public Field() {
		deck = new Deck();
		dropzone = new Dropzone();
		
		playerList = new ArrayList<>();
		
		Region divider = new Region();
		divider.setPrefSize(Card.SKIN_WIDTH, Card.SKIN_HEIGHT);
		Group group = new Group(dropzone.getModel(), divider, deck.getModel());
		
		model = new BorderPane();
		model.setCenter(group);
		
		confirm = new Button("Confirm");		//still must add to the scene graph
		confirm.setDisable(true);				//setOnAction: must disappear 
		
		VBox box = new VBox();
		VBox.setVgrow(confirm, Priority.NEVER);
		
		box.getChildren().addAll(playerList.get(0).getHand().getModel(), confirm);
		
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
		if (dropzone.getDrawCount() > 0) {
			deck.setToDeal(dropzone.getDrawCount());
			playerList.get((playerList.indexOf(current) + 1) % 4).getHand().pushAll(deck.popAll()); 
		}
		
		if (dropzone.requestSuitChange()) {
			List<String> choices = new ArrayList<>();
			
			for (Suit suit : Suit.values()) {
				choices.add(suit.getID());
			}
			
			ChoiceDialog<String> modal = new ChoiceDialog<>(dropzone.pop().getSuit().getID(), choices);
			modal.setTitle("Suit Change Request");
			modal.setHeaderText("Select a suit from the list below.");
			modal.setContentText("NOTE:\n'S' = Spades\n'C' = Clubs\n'D' = Diamonds\n'H = Hearts'");
			modal.initModality(Modality.APPLICATION_MODAL);
			modal.setResizable(false);
			
			Optional<String> result = modal.showAndWait();
	
			if (result.isPresent()) {
				switch (result.get()) {
					case "S":
						dropzone.setChangedSuit(Suit.SPADES);
						break;
					case "C":
						dropzone.setChangedSuit(Suit.CLUBS);
						break;
					case "D":
						dropzone.setChangedSuit(Suit.DIAMONDS);
						break;
					case "H":
						dropzone.setChangedSuit(Suit.HEARTS);
						break;
				}
			}
			dropzone.setRequestSuitChange(false); 
		}
		
		return (dropzone.getSkipCount() > 0) ? 
			playerList.get(((playerList.indexOf(current) + 1) + dropzone.getSkipCount()) % playerList.size()) : 
			playerList.get((playerList.indexOf(current) + 1) % playerList.size());
	}
	
	//mark all eligible + dropzone pop
	//changedSuit: how to determine when to use if state of requestSuitChange always changes?
	public void mark() {
		Card lastIn = dropzone.pop();
		
		if (lastIn.getRank() == Rank.EIGHT && lastIn.getSuit() != dropzone.getChangedSuit()) {
			//create a pseudocard that does not exist (in the cardList or physically in the UI models)
			current.getHand().markAllEligible(new Card(Rank.EIGHT, dropzone.getChangedSuit(), State.NONE));
		} else {
			current.getHand().markAllEligible(lastIn);
		}
	}
	
	public void listen() {
		current.markReady(confirm);
		
		confirm.setOnAction(e -> {
			dropzone.pushAll(current.getHand().popAll());
		});
	}
}
