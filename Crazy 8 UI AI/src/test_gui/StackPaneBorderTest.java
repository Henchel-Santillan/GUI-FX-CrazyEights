package test_gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.control.Button;

import javafx.scene.paint.Color;

public class StackPaneBorderTest extends Application {

	Scene scene;
	Pane pane;
	ImageView test1, test2;
	StackPane test_cont1, test_cont2;
	HBox box;
	VBox layout;
	Button button;
	
    public int pos = 0;
    public final int minPos = 0;
    public final int maxPos = 100;
	
	public boolean test1_clicked = false;
	
	@Override
	public void start(Stage stage) {
		pane = new Pane();
		scene = new Scene(pane, 500, 500);
		
		try {
			test1 = new ImageView(new Image(getClass().getResource("/IMAGE/4H.png").toURI().toString()));
			test2 = new ImageView(new Image(getClass().getResource("/IMAGE/2S.png").toURI().toString()));
			
			test_cont1 = new StackPane(test1);
			StackPane.setMargin(test1, new Insets(2.0));
			
			test_cont2 = new StackPane(test2);
			StackPane.setMargin(test2, new Insets(2.0));
			
			box = new HBox();
			box.getChildren().addAll(test_cont1, test_cont2);
			
			for (Node node : box.getChildren()) {
				
				((ImageView)((StackPane) node).getChildren().get(0)).setFitWidth(80.0d);
				((ImageView)((StackPane) node).getChildren().get(0)).setFitHeight(120.0d);
				
				node.setOnMousePressed(e -> {
					
					if (!test1_clicked) {
						((StackPane) node).setBackground(new Background(
								new BackgroundFill(Color.rgb(178, 34, 34), CornerRadii.EMPTY, Insets.EMPTY)));
						//disable all other clicked images
						disableAll((StackPane) node, box.getChildren());
						test1_clicked = true; //in CardModel, will use BooleanProperty for click
					} else {
						((StackPane) node).setBackground(new Background(
								new BackgroundFill(Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
						test1_clicked = false;
					}
				});
			}
			
		} catch (URISyntaxException e) {
			Logger.getLogger(StackPaneBorderTest.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(StackPaneBorderTest.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		/*ScrollBar bar = new ScrollBar();
		bar.setOrientation(Orientation.HORIZONTAL);
		bar.setUnitIncrement(((ImageView) test_cont1.getChildren().get(0)).getFitWidth());
		bar.setBlockIncrement(bar.getUnitIncrement());
		bar.setPrefSize(box.getWidth(), 15);
		bar.setVisibleAmount(bar.getWidth() / ((ImageView) test_cont1.getChildren().get(0)).getFitWidth());
		bar.setLayoutY(scene.getHeight() - bar.getHeight() + 75);
		bar.setValue(0.0);
		
		bar.valueProperty().addListener((observable, oldValue, newValue) -> {
			box.setLayoutX(-newValue.doubleValue());
		});
		
		layout = new VBox();
		layout.getChildren().addAll(box, bar);*/
	
		ScrollPane spane = new ScrollPane();
		spane.setPrefWidth(((ImageView)test_cont1.getChildren().get(0)).getFitWidth());
		spane.setContent(box);
		spane.setFitToHeight(true);
		spane.setPannable(true);
		spane.setVbarPolicy(ScrollBarPolicy.NEVER);
		spane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		/*scrollPane.setOnScroll(event -> {
	    if(event.getDeltaX() == 0 && event.getDeltaY() != 0) {
	        scrollPane.setHvalue(scrollPane.getHvalue() - event.getDeltaY() / this.allComments.getWidth());*/
		
		spane.setOnScroll(e -> {
			if(e.getDeltaX() == 0 && e.getDeltaY() != 0) {
				spane.setHvalue(spane.getHvalue() - e.getDeltaY() / spane.getWidth());
				e.consume();
			}
		});
		
		pane.getChildren().add(spane);
		
		stage.setScene(scene);
		stage.setTitle("StackPane Border Test");
		stage.show();
		
		System.out.println(box.getHeight());
		System.out.println(((ImageView) test_cont1.getChildren().get(0)).getFitHeight());
		System.out.println(spane.getHeight());
	}
	
	private void disableAll(StackPane selected, List<Node> stackpane_list) {
		for (Node pane : stackpane_list) {
			if (!((StackPane)pane).equals(selected)) {
				((StackPane) pane).setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), 
						CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
		
	}

	
	//to change maxPos, add listener to the size of the content area. If larger, reset maxPos
	/*//pos is current X, minPos = 0, maxPos is end of ScrollPane width minus viewport 
	private void hSpanning(ScrollPane sPane, int pos, int minPos, int maxPos) {
		//integrate the card adding tool by instantiating a Deck, and then adding to the Hbox using Button + refresh pane
		sPane.setOnScroll(e -> {
			
			final int nPos = pos;
			
			if (e.getDeltaY() > 0) {
				//if pos at minPos, set pos to minPos else decrement
				sPane.setHvalue(pos == minPos ? minPos : pos--);
			} else {
				sPane.setHvalue(pos == maxPos ? maxPos : pos++);
			}
		});
	}*/
	
	public static void main(String[] args) {
		launch(args);
	}

}
