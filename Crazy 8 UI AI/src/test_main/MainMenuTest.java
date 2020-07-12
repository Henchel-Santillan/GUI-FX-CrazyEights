package test_main;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.control.Button;

public class MainMenuTest extends Application {

	Rectangle2D screen;
	Scene scene;
	BorderPane pane;
	FlowPane buttons;
	
	Button classic, countdown, settings;
	
	List<Button> buttonList = new ArrayList<>();
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		pane = new BorderPane();
		
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		classic = new Button("Classic");
		countdown = new Button("Countdown");
		settings = new Button("Settings");
		
		buttonList.add(classic);
		buttonList.add(countdown);
		buttonList.add(settings);
		
		buttons = new FlowPane(Orientation.VERTICAL);
		
		for (Button button : buttonList) {
			button.setPrefHeight(100);
			button.setPrefWidth(250);
			buttons.getChildren().add(button);
		}
		
		buttons.setVgap(3.0);
		buttons.setHgap(3.0);
		buttons.setColumnHalignment(HPos.CENTER);
		
		pane.setCenter(buttons);
		
		scene.getStylesheets().add("mainmenu.css");
		
		stage.setScene(scene);
		stage.setTitle("Main Menu Test");
		//stage.setFullScreen(true);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
