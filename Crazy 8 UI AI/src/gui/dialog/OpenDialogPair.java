package gui.dialog;

import javafx.scene.Node;

public class OpenDialogPair<T extends Node> extends DialogPair {

	public OpenDialogPair(boolean required) {
		super(required);
	}
	
	public OpenDialogPair(boolean required, String description) {
		super(required, description);
	}
	
	public void add(T item) {
		model.getChildren().add(item);
	}
}
