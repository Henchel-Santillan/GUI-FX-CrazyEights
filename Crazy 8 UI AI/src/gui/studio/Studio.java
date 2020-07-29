package gui.studio;

import javafx.scene.Cursor;
import javafx.scene.Node;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.MenuBar;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;

public class Studio {
	
	private final BorderPane model;
	private final MenuBar navBar;
	private final Canvas canvas;
	private final GraphicsContext gc;
	
	private final HBox footer;
	private final Label position, toolDetail;
	
	public Studio() {
		model = new BorderPane();
		
		//TOP
		//TODO: Enable keyboard shortcuts
		navBar = new MenuBar();
		Menu file = new Menu();
		MenuItem fileSave = new MenuItem("Save");
		fileSave.setOnAction(e -> {
			e.consume();
		});
		
		MenuItem fileSaveAs = new MenuItem("Save As");
		fileSaveAs.setOnAction(e -> {
			e.consume();
		});
		
		MenuItem fileImport = new MenuItem("Import");
		fileImport.setOnAction(e -> {
			//TODO: Use BrowseDialog to 
			e.consume();
		});
		
		MenuItem fileExport = new MenuItem("Export");
		fileExport.setOnAction(e -> {
			e.consume();
		});
		
		file.getItems().addAll(fileSave, fileSaveAs, fileImport, fileExport);
		
		
		//BOTTOM
		position = new Label();
		toolDetail = new Label();
		footer = new HBox(position, toolDetail);
		
		for (Node node : footer.getChildren()) {
			HBox.setHgrow(node, Priority.ALWAYS);
		}
		
		//CENTER + RIGHT
		//TODO: Find right aspect ratio to translate seamlessly into card back.
		canvas = new Canvas(500, 500);
		canvas.setOnMouseMoved(e -> {
			position.setText(String.valueOf(e.getScreenX()) + "," + String.valueOf(e.getScreenY()));
			e.consume();
		});
		
		gc = canvas.getGraphicsContext2D();
		
		//LEFT
	}
	
	public BorderPane getModel() {
		return model;
	}
}
