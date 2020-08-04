package gui.tests;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class ToggleGroupTest extends Application {

	Rectangle2D screen;
	Scene scene;
	VBox box;
	HBox buttons;
	ToggleButton one, two, three;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		box = new VBox();
		scene = new Scene(box, screen.getWidth(), screen.getHeight());
		
		ToggleGroup group = new ToggleGroup();
		/*RadioButton one = new RadioButton("One");
		RadioButton two = new RadioButton("Two");
		RadioButton three = new RadioButton("Three");*/
		one = new ToggleButton();
		two = new ToggleButton();
		three = new ToggleButton();
		
		buttons = new HBox(one, two, three);
		
		group.getToggles().addAll(one, two, three);
		
		try {			
			ImageView oneIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/10C.png").toURI().toString()));
			
			ImageView twoIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/10D.png").toURI().toString()));
			
			ImageView threeIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/10H.png").toURI().toString()));
			
			ImageView[] icons = new ImageView[] {oneIcon, twoIcon, threeIcon};
			
			for (int i = 0; i < icons.length; i++) {
				icons[i].setFitWidth(20);
				icons[i].setFitHeight(30);
				icons[i].setSmooth(true);
				
				((ToggleButton) group.getToggles().get(i)).setGraphic(icons[i]);
			}
			
		} catch (Exception e) {
			Logger.getLogger(ToggleGroupTest.class.getName()).log(Level.SEVERE, null, e);
		}
		
		Label label = new Label("This will update with the radio button selected");
		group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
			label.setText(String.valueOf(group.getToggles().indexOf(newValue) + 1));
		});
		
		box.getChildren().addAll(buttons, label);
		
		stage.setScene(scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
