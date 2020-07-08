package main;

import javafx.scene.control.Button;

public class HumanPlayer extends Player {

	private final Button confirm;
	
	public HumanPlayer(State state, Hand hand) {
		super(state, hand);
		
		confirm = new Button();
	}
	
	
}
