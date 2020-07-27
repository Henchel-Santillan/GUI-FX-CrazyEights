package gui.tests;

import gui.dialog.BoxFieldDialog;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BoxFieldDialogTest extends Application {

	Scene scene;
	Rectangle2D screen;
	VBox pane;
	Button button;
	Label fieldLabel, boxLabel;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		pane = new VBox();
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		button = new Button("Open BoxFieldDialog.");
		fieldLabel = new Label("This will change soon.");
		boxLabel = new Label("This will change soon too.");
		
		ObservableList<String> list = FXCollections.observableArrayList();
		list.add("Hello");
		list.add("World");
		
		BoxFieldDialog<String> dialog = new BoxFieldDialog<>(stage);
		dialog.setFieldNumericOnly();
		dialog.setWindowTitle("Test");
		dialog.setContentText("Hello World! Dialog Test.");
		dialog.setBoxItems(list);
		
		dialog.setLabelBox("Choose one option: ");
		dialog.setLabelField("Enter name: ");
		
		button.setOnAction(e -> {
			dialog.show();
			if (dialog.isPresent()) {
				fieldLabel.setText(dialog.getResult().getKey().get());
				boxLabel.setText(dialog.getResult().getKey().get());
			}
			
			e.consume();
		});
		
		pane.getChildren().addAll(button, fieldLabel, boxLabel);
		
		stage.setScene(scene);
		stage.setTitle("BoxFieldDialogTest");
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
