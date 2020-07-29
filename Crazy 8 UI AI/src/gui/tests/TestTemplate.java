package gui.tests;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class TestTemplate extends Application {
	
	Rectangle2D screen;
	Scene scene;
	Pane pane;
	VBox box;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new Pane();
		box = new VBox();
		
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
