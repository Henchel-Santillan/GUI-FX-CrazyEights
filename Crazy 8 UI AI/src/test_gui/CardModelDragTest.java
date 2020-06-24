package test_gui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.Cursor;
import javafx.scene.Group;

import javafx.scene.paint.Color;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.net.URISyntaxException;


public class CardModelDragTest extends Application {

	Scene scene;
	Pane pane;
	ImageView image;
	Rectangle card_model, target;
	Group card;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		scene.setFill(Color.LIGHTGREEN);
		
		card_model = new Rectangle(80, 120);
		
		card_model.setX(scene.getWidth() * 0.5 - card_model.getWidth());
		card_model.setY(scene.getHeight() * 0.5 - card_model.getHeight());
		
		card_model.setFill(Color.WHITE);
		card_model.setStroke(Color.BLACK);
		card_model.setStrokeWidth(2.0);
		
		card_model.setArcWidth(15.0);
		card_model.setArcHeight(15.0);
		
		target = new Rectangle(card_model.getWidth(), card_model.getHeight());
		target.setX(scene.getWidth() - (target.getWidth() + 10.0));
		target.setY(scene.getHeight() * 0.5 - target.getHeight());
		
		target.setFill(card_model.getFill());
		target.setStroke(card_model.getStroke());
		target.setStrokeWidth(card_model.getStrokeWidth());
		
		target.setArcWidth(card_model.getArcWidth());
		target.setArcHeight(card_model.getArcHeight());
		
		try {
			image = new ImageView(new Image(
					getClass().getResource("/IMAGE/".concat("10C" + ".png")).toURI().toString()));
			image.setPreserveRatio(true);
			image.setFitHeight(card_model.getHeight());
			image.setFitWidth(card_model.getWidth());
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CardModelDragTest.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CardModelDragTest.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		image.setX(card_model.getX());
		image.setY(card_model.getY());
		
		card = new Group();
		card.getChildren().addAll(card_model, image);
		
		
		card.setOnMousePressed(e -> {
			/*card_model.setX(e.getX() - card_model.getWidth() * 0.5);
			image.setX(card_model.getX());
			card_model.setY(e.getY() - card_model.getHeight() * 0.5);
			image.setY(card_model.getY());*/
			
			card_model.setStroke(Color.RED);
		}); 
		
		card.setOnDragDetected(e -> {
			scene.setCursor(Cursor.CLOSED_HAND);
			Dragboard db = card.startDragAndDrop(TransferMode.ANY);
			
			ClipboardContent content = new ClipboardContent();
			content.putImage(((ImageView) card.getChildren().get(1)).getImage());
			db.setContent(content);
			
			e.consume();
		});
		
		
		
		target.setOnDragOver(e -> {
			if (e.getGestureSource() != target && e.getDragboard().hasImage()) {
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			
			e.consume();
		});
		
		target.setOnDragEntered(e -> {
			if (e.getGestureSource() != target && e.getDragboard().hasImage()) {
				target.setFill(Color.GREEN);
			}
			
			e.consume();
		});
		
		target.setOnDragExited(e -> {
			target.setFill(Color.BLACK);
			
			e.consume();
		});
		
		target.setOnDragDropped(e -> {
			
			Dragboard db = e.getDragboard();
			boolean success = false;
			
			if (db.hasImage()) {
				//target.setText(db.getString());
				success = true;
			}
			
			e.setDropCompleted(success);
			e.consume();
		});
		
		card.setOnDragDone(e -> {
			if (e.getTransferMode() == TransferMode.MOVE) {
				//erase old location
				
				card_model.setX(target.getX());
				card_model.setY(target.getY());
				
				image.setX(target.getX());
				image.setY(target.getY());
			}
			
			e.consume();
		});
			
		pane.getChildren().addAll(card, target);
		
		stage.setScene(scene);
		stage.setTitle("Card Model Design and Drag Test");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
