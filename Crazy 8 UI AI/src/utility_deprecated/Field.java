package utility_deprecated;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.Group;
import javafx.scene.layout.Region;
import javafx.scene.layout.BorderPane;

public class Field {
	
	private final Deck deck;
	private final Dropzone dropzone;
	private final BorderPane model;
	
	private List<Player> playerList;
	
	public Field(Deck deck, Dropzone dropzone, Player...players) {
		
		this.deck = deck;
		this.dropzone = dropzone;
	
		playerList = new ArrayList<>();
		
		for (Player player : players) {
			playerList.add(player);
		}
		
		model = new BorderPane();
		
		Region divider = new Region();
		divider.setPrefSize(100.0d, 100.0d);
		model.setCenter(new Group(dropzone.getModel(), divider, deck.getModel().getSkin()));
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Dropzone getDropzone() {
		return dropzone;
	}
	
	public List<Player> getPlayerList() {
		return playerList;
	}
	
	public BorderPane getModel() {
		return model;
	}
	
}
