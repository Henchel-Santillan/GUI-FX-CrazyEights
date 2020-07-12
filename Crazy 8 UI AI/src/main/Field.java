package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Priority;

import javafx.scene.Group;

import javafx.stage.Modality;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import main.Card.Suit;
import main.Card.Rank;

public class Field {

	private final Deck deck;
	private final Dropzone dropzone;
	
	private final List<Player> playerList;
	private Player current;
	
	private final BorderPane model;
	private final Button confirm;
	private final Label deckCounter, dropzoneCounter;
	
	public Field(Player...players) {
		deck = new Deck();
		deckCounter = new Label(String.valueOf(deck.cardList.size()));
		
		dropzone = new Dropzone();
		dropzoneCounter = new Label(String.valueOf(dropzone.cardList.size()));
		
		playerList = new ArrayList<>();
		
		for (Player player : players) {
			playerList.add(player);
		}
		
		current = playerList.get(0);
		
		Region divider = new Region();
		divider.setPrefSize(Card.SKIN_WIDTH, Card.SKIN_HEIGHT);
		Group group = new Group(dropzone.getModel(), divider, deck.getModel());
		
		model = new BorderPane();
		model.setCenter(group);
		
		//TODO: confirm styling with CSS
		confirm = new Button("Confirm");		
		confirm.setDisable(true);				
		
		VBox box = new VBox();
		VBox.setVgrow(confirm, Priority.NEVER);
		
		box.getChildren().addAll(playerList.get(0).getHandModel(), confirm);
		
		model.setTop(playerList.get(1).getHandModel());
		
		switch (playerList.size()) {
			case 3:
				model.setLeft(playerList.get(2).getHandModel());
				break;
			case 4:
				model.setLeft(playerList.get(2).getHandModel());
				model.setRight(playerList.get(3).getHandModel());
			default: break;
		}
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public BorderPane getModel() {
		return model;
	}
	
	//resolves all effects and gets the next non-skipped Player in the plyerList
	public Player nextPlayer() {
		if (dropzone.getDrawCount() > 0) {
			deck.setToDeal(dropzone.getDrawCount());
			playerList.get((playerList.indexOf(current) + 1) % 4).drawAll(deck.popAll()); 
			deck.setToDeal(0);
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
			current.scan(new Card(Rank.EIGHT, dropzone.getChangedSuit(), State.NONE));
		} else {
			current.scan(lastIn);
		}
	}
	
	//listen() is only called in gameflow for HumanPlayer
	public void listen() {
		((HumanPlayer) current).markReady(confirm);
		
		confirm.setOnAction(e -> {
			dropzone.pushAll(current.playAll());
		});
	}
	
	//split list into two from this.depthSearch() onwards and this.depthSearch() backwards
	//method follows through only if deck recommended size drops below Deck.MIN_CAPAICTY
	//TODO: Transfer Animation
	public void recycle() {
		int splitIndex = dropzone.cardList.size() - dropzone.depthSearch();
		
		//lists.get(0) is from dropzone 0 to this.depthSearch() - 1
		//lists.get(1) is this.depthSearch() to dropzone.cardList.size() - 1
		List<List<Card>> lists = new ArrayList<>(
				dropzone.cardList.stream()
				.collect(Collectors.groupingBy(s -> dropzone.cardList.indexOf(s) >= splitIndex)).values()
		);
		
		dropzone.cardList.removeAll(lists.get(0));
		
		for (Card card : lists.get(0)) {
			dropzone.getModel().getChildren().remove(card.getModel());
		}
		
		dropzoneCounter.setText(String.valueOf(dropzone.cardList.size() - lists.get(0).size()));
		
		List<Card> temp = new ArrayList<>(lists.get(0));
		Collections.shuffle(temp);
		
		deck.pushAll(temp);
		deckCounter.setText(String.valueOf(deck.cardList.size() - lists.get(0).size()));
	}
}
