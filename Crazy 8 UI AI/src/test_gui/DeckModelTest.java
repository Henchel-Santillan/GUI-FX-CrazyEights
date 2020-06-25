package test_gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.geometry.Insets;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.control.Button;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;


public class DeckModelTest extends Application {

	Scene scene;
	Pane pane;
	ImageView test1, test2;
	StackPane test_cont;
	HBox box;
	VBox container;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		box = new HBox();
		container = new VBox();
		scene = new Scene(pane, 500, 500);
		
		try {
			
			test1 = new ImageView(new Image(getClass().getResource("/IMAGE/card_back.png").toURI().toString()));
			test2 = new ImageView(new Image(getClass().getResource("/IMAGE/card_back.png").toURI().toString()));
			
			test_cont = new StackPane(test1, test2);
			StackPane.setMargin(test1, new Insets(2.0d));
			StackPane.setMargin(test2, new Insets(2.0d));
			
			for (Node node : test_cont.getChildren()) {
				((ImageView) node).setFitHeight(120.0d);
				((ImageView) node).setFitWidth(80.0d);
			}
			
		} catch (URISyntaxException e) {
			Logger.getLogger(DeckModelTest.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(DeckModelTest.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		Button button = new Button("Click to simulate card draw.");
		
		button.setOnAction(e -> {
			if (test_cont.getChildren().size() > 0) {
				
				Node removed = test_cont.getChildren().get(test_cont.getChildren().size() - 1);
				
				box.getChildren().add(removed);
				test_cont.getChildren().remove(removed);
			}
		});
		
		container.getChildren().addAll(button, test_cont, box);
		
		pane.getChildren().add(container);
		
		stage.setScene(scene);
		stage.setTitle("Deck Model Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
