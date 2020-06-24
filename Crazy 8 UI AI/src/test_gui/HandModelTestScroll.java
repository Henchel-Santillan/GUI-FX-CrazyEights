package test_gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import javafx.geometry.Orientation;

public class HandModelTestScroll extends Application {

	Rectangle2D screen;
	Pane pane;
	Scene scene;
	ImageView image1, image2, image3;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		pane = new Pane();
		scene = new Scene(pane, screen.getWidth(), screen.getHeight());
		
		HBox span_model = new HBox();
		
		try {
			image1 = new ImageView(new Image(getClass().getResource("/IMAGE/JD.png").toURI().toString()));
			image2 = new ImageView(new Image(getClass().getResource("/IMAGE/QC.png").toURI().toString()));
			image3 = new ImageView(new Image(getClass().getResource("/IMAGE/KH.png").toURI().toString()));
			
			span_model.getChildren().addAll(new StackPane(image1), new StackPane(image2), 
					new StackPane(image3));
			
			for (int i = 0; i < span_model.getChildren().size(); i++) {
				((ImageView)span_model.getChildren().get(i)).setFitWidth(80.0d);
				((ImageView)span_model.getChildren().get(i)).setFitHeight(120.0d);
				
				((ImageView)span_model.getChildren().get(i)).setOnMousePressed(e -> {
					
				});
			}
			
		} catch (URISyntaxException e) {
			Logger.getLogger(HandModelTestListView.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(HandModelTestListView.class.getName()).log(Level.SEVERE, null, e);
			
		}
	
		ScrollPane container = new ScrollPane();
		container.setContent(span_model);
		container.setPannable(true);
		
		//ISSUE: Make each ImageView in the span model AND inside the container viewport clickable (or mouse responsive)
		
	
		pane.getChildren().add(container);
		
		stage.setScene(scene);
		stage.setTitle("Hand Model Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
