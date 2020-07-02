package test_gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import utility_deprecated.Card;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

import javafx.geometry.Orientation;

public class HandModelTestListView extends Application {
	
	Rectangle2D screen;
	Pane pane;
	Scene scene;
	ImageView image1, image2, image3;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		pane = new Pane();
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		ListView<ImageView> control = new ListView<>();
		control.setOrientation(Orientation.HORIZONTAL);
		ObservableList<ImageView> items = FXCollections.observableArrayList();
		
		
		try {
			image1 = new ImageView(new Image(getClass().getResource("/IMAGE/JD.png").toURI().toString()));
			image2 = new ImageView(new Image(getClass().getResource("/IMAGE/QC.png").toURI().toString()));
			image3 = new ImageView(new Image(getClass().getResource("/IMAGE/KH.png").toURI().toString()));
			
			items.addAll(image1, image2, image3);
			
			for (int i = 0; i < items.size(); i++) {
				items.get(i).setFitWidth(80.0d);
				items.get(i).setFitHeight(120.0d);
			}
			
		} catch (URISyntaxException e) {
			Logger.getLogger(HandModelTestListView.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(HandModelTestListView.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		control.setItems(items);
		
		pane.getChildren().add(control);
		
		stage.setScene(scene);
		stage.setTitle("Hand Model Test");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
