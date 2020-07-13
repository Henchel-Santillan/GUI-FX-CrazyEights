package main;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.collections.ListChangeListener;

import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;

import javafx.scene.Group;
import javafx.scene.Node;

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
	
	//TODO: Add algorithm to init dropzone
	//TODO: Layout of the Field: where to place labels
	public Field(Player...players) {
		deck = new Deck();
		
		deck.cardList.addListener((ListChangeListener<Card> ) c -> {
			if (c.getList().size() <= Deck.MIN_CAPACITY) {
				this.recycle();
			}
		});
		
		dropzone = new Dropzone(deck.pop());
		playerList = new ArrayList<>();
		
		for (Player player : players) {
			playerList.add(player);
		}
		
		current = playerList.get(0);
		
		model = new BorderPane();
		//TODO: VBox styling
		VBox deckBox = new VBox();
		deckBox.getChildren().addAll(deck.getModel(), new HBox(
				new Label("DECK"), deck.getCounter()));
		deckBox.setAlignment(Pos.CENTER);
		for (Node node : deckBox.getChildren()) {
			VBox.setVgrow(node, Priority.NEVER);
		}
		
		VBox dropzoneBox = new VBox();
		dropzoneBox.getChildren().addAll(dropzone.getModel(), new HBox(
				new Label("DROPZONE"), dropzone.getCounter()));
		for (Node node : dropzoneBox.getChildren()) {
			VBox.setVgrow(node,  Priority.NEVER);
		}
		
		Region divider = new Region();
		divider.setPrefSize(Card.SKIN_WIDTH, Card.SKIN_HEIGHT);
		
		model.setCenter(new Group(deckBox, divider, dropzoneBox));
		
		//TODO: confirm styling with CSS
		confirm = new Button("Confirm");		
		confirm.setDisable(true);				
		
		VBox playerBox = new VBox();
		VBox.setVgrow(confirm, Priority.NEVER);
		
		playerBox.getChildren().addAll(playerList.get(0).getHandModel(), confirm);
		
		//TODO: Change display styles for AI Players
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
	
	
	//listen() is only called in gameflow for HumanPlayer
	/* For this method:
	 * must run scan 
	 * must mark ready
	 * must handle a draw if user chooses to
	 * how to end?
	 * */
	//TODO: Should handle if user cannot go (see Docs).
	public void listen() {
		Card lastIn = dropzone.pop();
		
		if (lastIn.getRank() == Rank.EIGHT && lastIn.getSuit() != dropzone.getChangedSuit()) {
			current.scan(new Card(Rank.EIGHT, dropzone.getChangedSuit(), State.NONE));
		} else {
			current.scan(lastIn);
		}

		deck.top().getModel().setOnMousePressed(e -> {
			if (!current.hasDrawn()) {
				current.draw(deck.pop());
				deck.setIsOnPrompt(false);
				current.setHasDrawn(true);
			}
		});
		
		((HumanPlayer) current).markReady(confirm);
		
		confirm.setOnAction(e -> {
			dropzone.pushAll(current.playAll());
		});
	}
	
	//TODO: Transfer Animation
	public void recycle() {
		List<Card> temp = new ArrayList<>(dropzone.popAll());
		Collections.shuffle(temp);
		deck.pushAll(temp);
	}
}
