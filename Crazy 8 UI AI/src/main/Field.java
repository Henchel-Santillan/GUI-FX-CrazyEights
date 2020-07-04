package main;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;
import javafx.scene.Group;

public class Field {

	private final Deck deck;
	private final Dropzone dropzone;
	
	private final List<Player> playerList;
	private Player current;
	
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
		return (Dropzone.getSkipCount() > 0) ? 
			playerList.get(((playerList.indexOf(current) + 1) + Dropzone.getSkipCount()) % playerList.size()) : 
			playerList.get((playerList.indexOf(current) + 1) % playerList.size());
	}
	
	/*public void put(Hand hand, Dropzone dropzone) {
		
	}*/
}
