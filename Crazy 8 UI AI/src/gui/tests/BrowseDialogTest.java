package gui.tests;

import gui.dialog.BrowseDialog;
import gui.dialog.BrowseDialog.BrowseType;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BrowseDialogTest extends Application {
	
	Rectangle2D screen;
	Scene scene;
	VBox box;
	Label response;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		box = new VBox();
		scene = new Scene(box, screen.getWidth(), screen.getHeight());
		
		response = new Label("Response will be shown here.");
		
		Button action = new Button("Click to open BrowseDialog.");
		action.setOnAction(e -> {
			BrowseDialog dialog = new BrowseDialog(stage, BrowseType.FOLDER);
			dialog.setWarningText("No folder or file selected.");
			dialog.setWindowTitle("Test Browse");
			dialog.setContentText("Hello World! Dialog Test.");
			
			dialog.show();
			
			if (dialog.hasResult()) {
				response.setText(dialog.getResult().get());
			}
			
			e.consume();
		});
		
		box.getChildren().addAll(action, response);
		stage.setScene(scene);
		stage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
