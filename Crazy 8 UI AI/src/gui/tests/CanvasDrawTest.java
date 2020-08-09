package gui.tests;

import gui.util.WeightBox;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;

import javafx.scene.Scene;
import javafx.scene.ImageCursor;

import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
/*import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;*/
import javafx.scene.layout.Region;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Tooltip;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CanvasDrawTest extends Application {
	
	public static final int CANVAS_WIDTH = 800;
	public static final int CANVAS_HEIGHT = 500;
	public static final double ICON_SIZE = 20;
	public static final double CURSOR_SIZE = 10;
	public static final double OFFSET = 2.0;
	
	Scene scene;
	Rectangle2D screen;
	BorderPane root;
	StackPane stack;
	Region region;
	Canvas canvas;
	GraphicsContext context;
	
	FlowPane toolmenu;
	WeightBox weight;
	ColorPicker picker;
	ToggleButton sketch, erase;
	Button clear;
	ImageCursor sketchCursor, eraseCursor;
	
	public boolean isErase = false;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		root = new BorderPane();
		
		scene = new Scene(root, screen.getWidth(), screen.getHeight());
	
		canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
		context = canvas.getGraphicsContext2D();
		
		weight = new WeightBox(30);
		picker = new ColorPicker(Color.BLACK);
		
		sketch = new ToggleButton();
		sketch.setTooltip(new Tooltip("Sketch"));
		
		erase = new ToggleButton();
		erase.setTooltip(new Tooltip("Erase"));
		
		try {
			ImageView sketchIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/sketchIcon.png").toURI().toString()));
			
			sketchIcon.setFitWidth(ICON_SIZE);
			sketchIcon.setFitHeight(ICON_SIZE);
			sketchIcon.setSmooth(true);
			
			sketch.setGraphic(sketchIcon);
			
			ImageView eraseIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/eraseIcon.png").toURI().toString()));
			
			eraseIcon.setFitWidth(ICON_SIZE);
			eraseIcon.setFitHeight(ICON_SIZE);
			eraseIcon.setSmooth(true);
			
			erase.setGraphic(eraseIcon);
			
			Image sketchImage = new Image(getClass().getResource(
					"/IMAGE/sketchCursor.png").toURI().toString());
			
			Image eraseImage = new Image(getClass().getResource(
					"/IMAGE/eraseCursor.png").toURI().toString());
			
			sketchCursor = new ImageCursor(sketchImage, 0, sketchImage.getHeight());
			
			eraseCursor = new ImageCursor(eraseImage, 0, eraseImage.getHeight());
			
			
		} catch (URISyntaxException e) {
			Logger.getLogger(CanvasDrawTest.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(CanvasDrawTest.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		//must bind context line width to current property
		sketch.setOnAction(e -> {
			picker.setVisible(true);
			canvas.setCursor(sketchCursor);
			
			isErase = false;
			e.consume();
		});
		
		erase.setOnAction(e -> {
			context.setStroke(Color.TRANSPARENT);
			picker.setVisible(false);
			canvas.setCursor(eraseCursor);
			
			isErase = true;
			e.consume();
		});
		
		weight.currentProperty().addListener((observable, oldValue, newValue) -> {
			context.setLineWidth(newValue.doubleValue());
			System.out.println(context.getLineWidth());
		});
		
		picker.setOnAction(e -> {
			context.setStroke(picker.getValue());
		});
		
		context.setStroke(picker.getValue());
		context.setLineWidth(weight.getCurrent());
		
		clear = new Button("Clear");
		clear.setOnAction(e -> {
			context.clearRect(0,  0, canvas.getWidth(), canvas.getHeight());
			e.consume();
		});
		
		toolmenu = new FlowPane(sketch, erase, weight.getModel(), picker, clear);
		root.setBottom(toolmenu);
		
		canvas.setOnMousePressed(e -> {
			context.beginPath();
			context.moveTo(e.getX(), e.getY());
			context.stroke();
			context.closePath();
			
			e.consume();
		});
		
		canvas.setOnMouseDragged(e -> {
			context.lineTo(e.getX(), e.getY());
			
			if (isErase) {
				context.clearRect(e.getX(), e.getY(), context.getLineWidth(), context.getLineWidth());
			} else {
				context.stroke();
			}
			
			e.consume();
		});
		
		
		/*canvasStack = new StackPane(canvas);
		canvasStack.setPrefSize(CANVAS_WIDTH, CANVAS_HEIGHT);
		canvasStack.setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0),
				CornerRadii.EMPTY, Insets.EMPTY)));
		StackPane.setMargin(canvas, new Insets(2.0));*/
		
		
		stack = new StackPane(canvas);
		stack.setMaxSize(CANVAS_WIDTH + OFFSET, CANVAS_HEIGHT + OFFSET);
		stack.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, 
				CornerRadii.EMPTY, BorderWidths.DEFAULT)));
		
		root.setCenter(stack);
		
	
		stage.setScene(scene);
		stage.setTitle("Canvas Draw Test");
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
