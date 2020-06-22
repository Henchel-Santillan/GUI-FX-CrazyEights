package test_gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;

public class DragboardSingleShapeTest extends Application {

	Scene scene;
	Pane pane;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		Rectangle source = new Rectangle(50, 100);
		source.setFill(Color.BLUE);
		source.setStroke(Color.BLACK);
		source.setStrokeWidth(1.5d);
		
		source.setOnDragDetected(event -> {
			scene.setCursor(Cursor.CLOSED_HAND);
			
			Dragboard db = source.startDragAndDrop(TransferMode.ANY);
			
			ClipboardContent content = new ClipboardContent();
			
			db.setContent(content);
			
			event.consume();
		});
		
		Region target = new Region();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
