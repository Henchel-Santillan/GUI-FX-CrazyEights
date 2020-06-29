package test_gui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import javafx.util.Duration;
import javafx.animation.ScaleTransition;

public class ZoomTest extends Application {

	public double current_zoom = 0.0d;
	public static final double SCALE_FACTOR = 1.5d;
	
	public boolean clicked = false;
	
	Scene scene;
	Pane pane;
	Rectangle test_node;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		test_node = new Rectangle(100, 100);
		
		test_node.setOnMousePressed(e -> {
			ScaleTransition st = new ScaleTransition(Duration.seconds(0.1d), test_node);
			
			if (clicked) {
				st.setByX(-SCALE_FACTOR);
				st.setByY(-SCALE_FACTOR);
				clicked = false;
			} else {
				st.setByX(SCALE_FACTOR);
				st.setByY(SCALE_FACTOR);
				clicked = true;
			}
			
			st.play();
		});
		
		/*ScaleTransition st = new ScaleTransition(Duration.seconds(2.0d), test_node);
		st.setByX(1.5f);
		st.setByY(1.5f);
		st.setAutoReverse(true);
		st.play();*/
		
		pane.getChildren().add(test_node);
		
		stage.setScene(scene);
		stage.setTitle("Node Zoom Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
