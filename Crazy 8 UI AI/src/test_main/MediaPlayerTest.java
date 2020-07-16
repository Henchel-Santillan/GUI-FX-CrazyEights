package test_main;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.Media;

public class MediaPlayerTest extends Application {
	
	Scene scene;
	Pane pane;
	MediaView viewer;
	MediaPlayer player;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		player = new MediaPlayer(new Media(getClass().getResource(
					"/VIDEO/V_20190907_125920.mp4").toExternalForm()));
		player.setAutoPlay(true);
		viewer = new MediaView(player);

		pane.getChildren().add(viewer);
		
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
