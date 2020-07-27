package gui.tests;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javafx.scene.layout.Pane;
import javafx.scene.control.TextArea;
import javafx.scene.control.SplitPane;

public class SplitPaneTest extends Application {
	
	Pane pane;
	Scene scene;
	SplitPane divider;
	TextArea area;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		area = new TextArea();
		divider = new SplitPane(area);
		
		pane.getChildren().add(divider);
		
		stage.setScene(scene);
		stage.setTitle(SplitPaneTest.class.getName());
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
