package utility_deprecated;

import java.util.List;
import java.util.ArrayList;

import javafx.scene.layout.BorderPane;

public class FieldOld {
	
	private final Deck deck;
	private final Dropzone dropzone;
	private final List<Player> player_list;
	private Player current_player;
	
	private final BorderPane model;
	
	public FieldOld(Deck deck, Dropzone dropzone, Player... players) {
		this.deck = deck;
		this.dropzone = dropzone;
		
		player_list = new ArrayList<>();
		
		for (int i = 0; i < players.length; i++) {
			player_list.add(players[i]);
		}
		
		model = new BorderPane();
		
		Region region = new Region();
		region.setPrefSize(100.0, 100.0); //Region pref size subject to change
		Group group = new Group();
		group.getChildren().addAll(this.dropzone.getModel(), region, this.deck.getModel());
		
		model.setCenter(group);
		model.setBottom(players[0].getHand().getModel());
		model.setTop(players[1].getHand().getModel());	//at least two players.
		
		switch (players.length) {
			case 3:
				model.setLeft(players[2].getHand().getModel());
				break;
				
			case 4:
				model.setLeft(players[2].getHand().getModel());
				model.setRight(players[3].getHand().getModel());
				break;
		}
	}
	
	public Deck getDeck() {
		return deck;
	}
	
	public Dropzone getDropzone() {
		return dropzone;
	}
	
	public List<Player> getPlayers() {
		return player_list;
	}
	
	public Player getCurrentPlayer() {
		return current_player;
	}
	
	public void setCurrentPlayer(Player current_player) {
		this.current_player = current_player;
	}
	
	/* Field is responsible for:
	 * calling or invoking Dropzone recycling
	 * initializing the Field (initializing Hands, Deck, Players)
	 * responding to Player-induced or Player-triggered action cases
	 * 
	 * */
	
	
}
