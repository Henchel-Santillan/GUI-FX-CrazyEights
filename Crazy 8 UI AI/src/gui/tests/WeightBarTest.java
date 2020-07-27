package gui.tests;

import gui.util.WeightBar;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;

public class WeightBarTest extends Application {

	Pane pane;
	Scene scene;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		pane.getChildren().add(new WeightBar(100).getModel());
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
