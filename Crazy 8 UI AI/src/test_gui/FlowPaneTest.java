package test_gui;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Orientation;
import javafx.geometry.HPos;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.control.Button;

public class FlowPaneTest extends Application {
	
	Rectangle2D screen;
	
	Scene scene;
	AnchorPane anchorpane;
	FlowPane flowpane;
	
	List<Button> buttonList = new ArrayList<>();
	Button classic, countdown, settings;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		flowpane = new FlowPane();
		flowpane.setOrientation(Orientation.VERTICAL);
		flowpane.setColumnHalignment(HPos.CENTER);
		
		anchorpane = new AnchorPane();
		
		classic = new Button("Classic");
		countdown = new Button("Countdown");
		settings = new Button("Settings");
		
		buttonList.add(classic);
		buttonList.add(countdown);
		buttonList.add(settings);
		
		for (Button button : buttonList) {
			flowpane.getChildren().add(button);
		}
		
		AnchorPane.setLeftAnchor(flowpane, screen.getWidth() - );
		
		scene = new Scene(anchorpane, screen.getWidth(), screen.getHeight());
		stage.setScene(scene);
		stage.setTitle("Layout Testing for Main Menu");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
