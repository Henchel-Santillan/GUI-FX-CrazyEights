package main;

import javafx.scene.control.Button;

public class HumanPlayer extends Player {
	
	public HumanPlayer(State state, Hand hand) {
		super(state, hand);
	}
	
	public void markReady(Button button) {
		hand.enable(button);
	}
}
