package gui.dialog;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.stage.StageStyle;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public abstract class DialogUtil {
	
	protected final Stage parent, modal;
	protected final Scene scene;
	protected final VBox frame;
	
	protected final Button ok, cancel;
	
	protected final Label content, warning;
	
	public DialogUtil(Stage parent) {
		this.parent = parent;
		
		content = new Label();
		
		warning = new Label();
		warning.setVisible(false);
		warning.setTextFill(Color.RED);
	
		frame = new VBox();
		frame.setPadding(new Insets(20.0d));
		frame.setSpacing(5.0d);
		scene = new Scene(frame);
		
		modal = new Stage(StageStyle.UTILITY);
		modal.setScene(scene);
		modal.initOwner(parent);
		modal.initModality(Modality.WINDOW_MODAL);
		modal.setResizable(true);
		
		ok = new Button("OK");
		ok.setDisable(true);
		
		cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			modal.close();
			e.consume();
		});
		
		HBox buttonBox = new HBox(ok, cancel);
		buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		frame.getChildren().addAll(content, warning, buttonBox);
	}
	
	public final Stage getParent() {
		return parent;
	}
	
	public final void setWindowTitle(String title) {
		modal.setTitle(title);
	}
	
	public final void setContentText(String contentText) {
		content.setText(contentText);
	}
	
	public final void setWarningText(String warningText) {
		warning.setText(warningText);
	}
	
	public final void show() {
		modal.showAndWait();
	}
}