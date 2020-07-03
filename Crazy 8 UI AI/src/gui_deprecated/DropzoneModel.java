package gui_deprecated;

import javafx.scene.layout.StackPane;


//opposite of the DeckModel, in that it receives instead of giving cards
public class DropzoneModel {
	
	private final StackPane skin;
	
	public DropzoneModel() {
		skin = new StackPane();
	}
	
	public StackPane getSkin() {
		return skin;
	}
	
	public void recycle() {
		//recycle animation
	}
}
