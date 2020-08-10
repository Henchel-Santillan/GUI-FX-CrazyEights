package gui.tests;

import gui.util.ToggleSwitch;

import java.io.File;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BorderWidths;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Label;

import javafx.scene.image.Image;

import javafx.scene.paint.Color;

public class CanvasSaveTest extends Application {
	
	Rectangle2D screen;
	Scene scene;
	BorderPane root;
	StackPane stack;
	Canvas canvas;
	GraphicsContext context;
	
	HBox ribbon;
	MenuBar bar;
	Menu file;
	MenuItem save, saveas, importImage;
	Label label;
	ToggleSwitch toggle;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		root = new BorderPane();
		
		scene = new Scene(root, screen.getWidth(),screen.getHeight());
		
		canvas = new Canvas(600, 400);
		context = canvas.getGraphicsContext2D();
		
		stack = new StackPane(canvas);
		stack.setMaxSize(canvas.getWidth(), canvas.getHeight());
		stack.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		root.setCenter(stack);
		
		save = new MenuItem("Save");
		save.setOnAction(e -> {
			
			//TODO: Check if file in file system
			//if none, create a new one
			//Prompt save
			
			e.consume();
		});
		
		saveas = new MenuItem("Save As");
		saveas.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			
			File chosen = chooser.showSaveDialog(stage);
			
			if (chosen != null) {
				
			}
			//TODO: showSaveDialog()
			
			e.consume();
		});
		
		importImage = new MenuItem("Import Image");
		importImage.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(
					new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
			
			File chosen = chooser.showOpenDialog(stage);
			
			if (chosen != null) {
				//TODO: Image resizing?
				context.drawImage(new Image(chosen.toURI().toString()), 0, 0);
			}
			
			e.consume();
		});
		
		file = new Menu("File");
		file.getItems().addAll(save, saveas, importImage);
		
		bar = new MenuBar(file);
		
		label = new Label("Context save: ");
		toggle = new ToggleSwitch(80.0d);
		
		ribbon = new HBox(bar, label, toggle.getModel());
		root.setTop(ribbon);
		
		stage.setScene(scene);
		stage.setTitle("Canvas Save Test");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
