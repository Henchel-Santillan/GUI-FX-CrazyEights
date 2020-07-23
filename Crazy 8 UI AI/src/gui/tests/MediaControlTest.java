package gui.tests;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.scene.Scene;
import javafx.geometry.Rectangle2D;

import gui.media.MediaControl;

public class MediaControlTest extends Application {

	Rectangle2D screen;
	MediaControl mControl;
	Scene scene;
	
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		mControl = new MediaControl();
		scene = new Scene(mControl.getModel(), screen.getWidth(), screen.getHeight());
		
		stage.setScene(scene);
		stage.setTitle("MediaControl Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
