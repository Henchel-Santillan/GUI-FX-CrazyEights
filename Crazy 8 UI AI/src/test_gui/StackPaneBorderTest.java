package test_gui;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;

import javafx.geometry.Insets;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;

public class StackPaneBorderTest extends Application {

	Scene scene;
	Pane pane;
	ImageView test1, test2;
	StackPane test_cont1, test_cont2;
	HBox box;
	
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
			
			/*for (int i = 0; i < box.getChildren().size(); i++) {
				
				((StackPane) box.getChildren().get(i)).setOnMousePressed(e -> {
					((StackPane)box.getChildren().get(i)).setBackground(new Background(
							new BackgroundFill(Color.rgb(178, 34, 34), CornerRadii.EMPTY, Insets.EMPTY)));
				});
			}*/
			
			for (Node node : box.getChildren()) {
				
				((ImageView)((StackPane) node).getChildren().get(0)).setFitWidth(80.0d);
				((ImageView)((StackPane) node).getChildren().get(0)).setFitHeight(120.0d);
				
				node.setOnMousePressed(e -> {
					
					if (!test1_clicked) {
						((StackPane) node).setBackground(new Background(
								new BackgroundFill(Color.rgb(178, 34, 34), CornerRadii.EMPTY, Insets.EMPTY)));
						//disable all other clicked images
						disableAll((StackPane) node, box.getChildren());
						test1_clicked = true;
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
		
		pane.getChildren().add(box);
		
		stage.setScene(scene);
		stage.setTitle("StackPane Border Test");
		stage.show();
	}
	
	private void disableAll(StackPane selected, List<Node> stackpane_list) {
		for (Node pane : stackpane_list) {
			if (!((StackPane)pane).equals(selected)) {
				((StackPane) pane).setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), 
						CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
