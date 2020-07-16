package test_main;

import javafx.stage.Stage;

public class CustomDialog {
	
	private final Stage parent, modal;
	
	public CustomDialog(Stage parent) {
		this.parent = parent;
		
		modal = new Stage();
	}
	
	public void setWindowTitle(String title) {
		modal.setTitle(title);
	}
	
	public void setHeaderText(String headerText) {
		
	}
	
	public void setContentText(String contentText) {
		
	}
}
