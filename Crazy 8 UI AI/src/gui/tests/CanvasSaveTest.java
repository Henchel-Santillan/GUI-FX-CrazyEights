package gui.tests;

import gui.util.ToggleSwitch;

import java.util.logging.Logger;

import javax.imageio.ImageIO;

import java.util.logging.Level;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;

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
import javafx.scene.control.ChoiceBox;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;

import javafx.scene.paint.Color;

//TODO: ImportImage --> resize to fit aspect ratio of canvas, if dimensions are larger
//TODO: ResizableImage class in gui.util
//TODO: HistoryManager
public class CanvasSaveTest extends Application {
	
	public String projectname = "Untitled";
	public static final double OFFSET = 2.0;
	
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
	
	ChoiceBox<String> extensionFilter;
	
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
		
		label = new Label("Context save: ");
		toggle = new ToggleSwitch(80.0d);
		
		extensionFilter = new ChoiceBox<>();
		extensionFilter.getItems().addAll("png", "jpg", "gif", "jpeg");
		extensionFilter.setValue(extensionFilter.getItems().get(0));
		
		save = new MenuItem("Save");
		save.setOnAction(e -> {
			
			//TODO: get project name, probably a path
			File file = new File(projectname);
			
			if (file.exists() && !file.isDirectory()) {
				
				transfer(file, canvas, extensionFilter.getValue());
				
			} else {
				
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Save");
				
				File chosen = chooser.showSaveDialog(stage);
				
				transfer(chosen, canvas, extensionFilter.getValue());
				
				if (toggle.isSelected()) {
					context.save();
				}
			}
			
			e.consume();
		});
		
		saveas = new MenuItem("Save As");
		saveas.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.setTitle("Save As");
		
			chooser.setInitialDirectory(new File(System.getProperty("user.home") + "/Downloads/"));
			chooser.setInitialFileName(projectname + "." + extensionFilter.getValue());
			
			//TODO: check if chosen is a directory
			File chosen = chooser.showSaveDialog(stage);
			
			if (chosen != null) {
				transfer(chosen, canvas, extensionFilter.getValue());
				
				if (toggle.isSelected()) {
					context.save();
				}
			}
			
			e.consume();
		});
		
		importImage = new MenuItem("Import Image");
		importImage.setOnAction(e -> {
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(
					new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
			
			File chosen = chooser.showOpenDialog(stage);
			
			if (chosen != null) {
				
				context.drawImage(new Image(chosen.toURI().toString()), 0, 0);
			}
				
			e.consume();
		});
		
		file = new Menu("File");
		file.getItems().addAll(save, saveas, importImage);
		
		bar = new MenuBar(file);
		
		ribbon = new HBox(bar, label, toggle.getModel(), extensionFilter);
		root.setTop(ribbon);
		
		stage.setScene(scene);
		stage.setTitle("Canvas Save Test");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	private void transfer(File target, Canvas canvas, String extension) {
		try {
			SnapshotParameters snaps = new SnapshotParameters();
			snaps.setFill(Color.TRANSPARENT);
			
			BufferedImage image = SwingFXUtils.fromFXImage(canvas.snapshot(snaps, null), null);
			BufferedImage imagergb = new BufferedImage(image.getWidth(), image.getHeight(), 
					BufferedImage.OPAQUE);
			Graphics2D graphics = imagergb.createGraphics();
			graphics.drawImage(image, 0, 0, null);
			
			//TODO: can have a string choicebox to change extension
			ImageIO.write(imagergb, extension, target);
			/*WritableImage writable = canvas.snapshot(snaps, new WritableImage(
					(int) canvas.getWidth(), (int) canvas.getHeight()));
			RenderedImage image = SwingFXUtils.fromFXImage(writable, null);
			ImageIO.write(image, "png", chosen);*/
			
		} catch (IOException ex) {
			Logger.getLogger(CanvasSaveTest.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}
