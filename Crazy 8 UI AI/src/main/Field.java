package main;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.BorderPane;

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
		
		model = new BorderPane();
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
	
	
}
