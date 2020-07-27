package gui.studio;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Ribbon {
	
	private final VBox model; 
	private final HBox menuBox, navBox;

	public Ribbon(Canvas canvas, GraphicsContext gc, ToolHistory history) {
		model = new VBox();
		
		Button back = new Button();
		
		try {
			ImageView backArrow = new ImageView(new Image(getClass().getResource(
					"/IMAGE/back_arrow.png").toURI().toString()));
			backArrow.setSmooth(true);
			backArrow.setFitWidth(20.0d);
			backArrow.setFitHeight(15.0d);
			
			back.setGraphic(backArrow);
			
		} catch (URISyntaxException e) {
			Logger.getLogger(Ribbon.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Ribbon.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		back.setOnAction(e -> {
			//TODO: Go back to previous scene on chain
			e.consume();
		});
		
		//MENUBAR
		MenuBar menuBar = new MenuBar();
		
		//FILE MENU
		Menu file = new Menu("File");
		MenuItem save = new MenuItem("Save");
		MenuItem saveAs = new MenuItem("Save As");
		
		Menu imp = new Menu("Import");
		MenuItem impImage = new MenuItem("Image");
		MenuItem impProject = new MenuItem("Existing Project");
		imp.getItems().addAll(impImage, impProject);
		
		MenuItem exp = new MenuItem("Export");
		
		file.getItems().addAll(save, saveAs, imp, exp);
		
		//EDIT MENU
		Menu edit = new Menu("Edit");
		
		//undo, redo, and history rely on ToolHistory
		MenuItem undo = new MenuItem("Undo");
		undo.setOnAction(e -> {
			
			e.consume();
		});

		MenuItem redo = new MenuItem("Redo");
		MenuItem viewHistory = new MenuItem("History");
		
		MenuItem selectAll = new MenuItem("Select All");
		
		MenuItem clearAll = new MenuItem("Clear All");
		clearAll.setOnAction(e -> {
			gc.clearRect(0,  0, canvas.getWidth(), canvas.getHeight());
			e.consume();
		});
		
		edit.getItems().addAll(undo, redo, viewHistory, selectAll, clearAll);
		
		//IMAGE MENU
		Menu image = new Menu("Image");
		MenuItem imageFlip = new MenuItem("Flip/Rotate");
		MenuItem imageStretch = new MenuItem("Stretch/Skew");
		image.getItems().addAll(imageFlip, imageStretch);
		
		
		Menu help = new Menu("Help");
		MenuItem helpWiki = new MenuItem("Git Wiki");
		
		help.getItems().add(helpWiki);
		
		menuBar.getMenus().addAll(file, edit, image, help);
		
		menuBox = new HBox(back, menuBar);
		
		navBox = new HBox();
		
		model.getChildren().addAll(menuBox, navBox);
	}
	
	public VBox getModel() {
		return model;
	}
}
