package main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;


/*TODO: opening animation, main menu, dice for players, game loop*/
public class Main extends Application {

	Rectangle2D screen;
	Scene scene;
	Pane pane;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		pane = new Pane();
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		//scene + stage title are subject to change depending on Menu
		stage.setScene(scene);
		stage.setFullScreen(true);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
