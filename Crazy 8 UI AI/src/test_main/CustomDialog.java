package test_main;

import java.util.Optional;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public abstract class CustomDialog<T> {
	
	protected final Stage parent, modal;
	protected final Scene scene;
	protected final VBox frame;
	
	protected Label header, content;
	
	public CustomDialog(Stage parent) {
		this.parent = parent;
		
		header = new Label();
		content = new Label();
		
		frame = new VBox();
		frame.getChildren().addAll(header, content);
		
		scene = new Scene(frame);
		
		modal = new Stage(StageStyle.UTILITY);
		modal.setScene(scene);
		modal.initOwner(parent);
		modal.initModality(Modality.APPLICATION_MODAL);
	}
	
	public Stage getParent() {
		return parent;
	}
	
	public void setWindowTitle(String title) {
		modal.setTitle(title);
	}
	
	//TODO: Differentiate between header and content text
	public void setHeaderText(String headerText) {
		header.setText(headerText);
	}
	
	public void setContentText(String contentText) {
		content.setText(contentText);
	}
	
	public abstract Optional<T> displayAndWait();
}
