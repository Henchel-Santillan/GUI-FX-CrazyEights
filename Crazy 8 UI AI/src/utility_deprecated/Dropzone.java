package utility_deprecated;

import javafx.scene.layout.Region;

public class Dropzone {
	
	private final Region model;
	
	public Dropzone() {
		model = new Region();
	}
	
	public Region getModel() {
		return model;
	}
	
	public void recycle(Deck deck) {
		//recycle protocol
	}
}
