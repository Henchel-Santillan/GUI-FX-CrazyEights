package test_gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;


public class CardModelDragTest extends Application {

	Scene scene;
	Pane pane;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		scene.setFill(Color.LIGHTGREEN);
		
		Rectangle card_model = new Rectangle(80, 120);
		
		card_model.setX(scene.getWidth() * 0.5 - card_model.getWidth());
		card_model.setY(scene.getHeight() * 0.5 - card_model.getHeight());
		
		card_model.setFill(Color.WHITE);
		card_model.setStroke(Color.BLACK);
		card_model.setStrokeWidth(2.0);
		
		card_model.setArcWidth(15.0);
		card_model.setArcHeight(15.0);
		
		card_model.setOnMousePressed(e -> {
			card_model.setX(e.getX() - card_model.getWidth() * 0.5);
			card_model.setY(e.getY() - card_model.getHeight() * 0.5);
		}); 
		
		card_model.setOnMouseDragged(e -> {
			
		});
			
		pane.getChildren().add(card_model);
		
		stage.setScene(scene);
		stage.setTitle("Card Model Design and Drag Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
