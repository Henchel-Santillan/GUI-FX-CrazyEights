package test_gui;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.Cursor;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import javafx.scene.input.Dragboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.TransferMode;

/* Drag-and-Drop Gesture Test Cases taken from Oracle. Steps towards implementation are enumerated below.
 * Start gesture on the source
 * DRAG_OVER Event on Target
 * Visual Feedback for drag and drop attempt
 * DRAG_DROPPED
 * DRAG_DONE
 * */

public class DragBoardTest extends Application {

	Pane pane;
	Scene scene;
	
	@Override
	public void start(Stage stage) {
		final Text source = new Text(50, 100, "DRAG ME!");
		final Text target = new Text(300, 100, "DROP HERE!");
		
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		source.setOnDragDetected(e -> {
			scene.setCursor(Cursor.CLOSED_HAND);
			Dragboard db = source.startDragAndDrop(TransferMode.ANY);
			
			ClipboardContent content = new ClipboardContent();
			content.putString(source.getText());
			db.setContent(content);
			
			e.consume();
		});
		
		target.setOnDragOver(e -> {
			if (e.getGestureSource() != target && e.getDragboard().hasString()) {
				e.acceptTransferModes(TransferMode.COPY_OR_MOVE);
			}
			
			e.consume();
		});
		
		target.setOnDragEntered(e -> {
			if (e.getGestureSource() != target && e.getDragboard().hasString()) {
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
			
			if (db.hasString()) {
				target.setText(db.getString());
				success = true;
			}
			
			e.setDropCompleted(success);
			e.consume();
		});
		
		source.setOnDragDone(e -> {
			if (e.getTransferMode() == TransferMode.MOVE) {
				source.setText("");
			}
			
			e.consume();
		});
		
		pane.getChildren().addAll(source, target);
		
		stage.setScene(scene);
		stage.show();
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}

}
