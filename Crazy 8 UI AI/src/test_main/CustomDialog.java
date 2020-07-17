package test_main;

import javafx.stage.Stage;

public class CustomDialog {
	
	private final Stage parent, modal;
	
	//TODO: Decide on CustomDialog frame pane (e.g. GridPane)
	public CustomDialog(Stage parent) {
		this.parent = parent;
		
		modal = new Stage();
	}
	
	public Stage getParent() {
		return parent;
	}
	
	public void setWindowTitle(String title) {
		modal.setTitle(title);
	}
	
	public void setHeaderText(String headerText) {
		
	}
	
	public void setContentText(String contentText) {
		
	}
	
	//TODO: Brainstorm a list
	
	
}
