package gui.tests;

import gui.util.ToggleSwitch;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javafx.scene.control.Label;

public class ToggleSwitchTest extends Application {
	
	Rectangle2D screen;
	Scene scene;
	Pane pane;
	VBox box;
	ToggleSwitch toggleswitch;
	Label label;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new Pane();
		box = new VBox();
		
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		toggleswitch = new ToggleSwitch(80);
		
		label = new Label();
		
		toggleswitch.selectedProperty().addListener((observable, oldValue, newValue) -> {
			label.setText(String.valueOf(newValue));
		});
		
		box.getChildren().addAll(toggleswitch.getModel(), label);
		pane.getChildren().add(toggleswitch.getModel());
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}