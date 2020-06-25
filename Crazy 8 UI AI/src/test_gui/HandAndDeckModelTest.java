package test_gui;

import java.io.File;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.List;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.Screen;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class HandAndDeckModelTest extends Application {

	public static final int DECK_SIZE = 52;
	public static final int HAND_VIEWCAP = 8;
	
	public boolean clicked = false;
	
	List<Node> similar;

	Rectangle2D screen;
	Scene scene;
	BorderPane container;
	HBox hand_model;
	StackPane deck_model;
	ScrollPane hand;
	
	StackPane selected;
	
	@Override
	public void start(Stage stage) {
		screen = Screen.getPrimary().getVisualBounds();
		
		container = new BorderPane();
		scene = new Scene(container, screen.getWidth(), screen.getHeight());
		
		hand_model = new HBox();
		hand_model.setSpacing(2.0);
		
		try {
			String[] file_names = new String[DECK_SIZE];
			File dir = new File("/home/jhr/Programming/eclipse_workspace/Crazy 8 UI AI/RESOURCES/IMAGE");
			File[] dir_list = dir.listFiles();
			
			int count = 0;
			
			if (dir_list != null) {
				for (File file : dir_list) {
					if (!file.getName().equals("card_back.png")) {
						file_names[count] = file.getName();
						count ++;
					}
				}
			}
			
			deck_model = new StackPane();
			
			for (int i = 0; i < DECK_SIZE; i++) {
				deck_model.getChildren().add(new StackPane(new ImageView(new Image(getClass().getResource(
						"/IMAGE/".concat(file_names[i])).toURI().toString()))));
				
				((ImageView)((StackPane) 
						deck_model.getChildren().get(i)).getChildren().get(0)).setFitWidth(80.0d);
				((ImageView)((StackPane) 
						deck_model.getChildren().get(i)).getChildren().get(0)).setFitHeight(120.0d);
				
				StackPane.setMargin(deck_model.getChildren().get(i), new Insets(2.0d));
			}
			
			deck_model.setMaxWidth(((ImageView)((StackPane) 
						deck_model.getChildren().get(0)).getChildren().get(0)).getFitWidth());
			
			deck_model.setOnMousePressed(e-> {
				if (deck_model.getChildren().size() > 0) {
					hand_model.getChildren().add(deck_model.getChildren().remove(deck_model.getChildren().size() - 1));
				}
			});
			
			deck_model.setOnMouseEntered(e -> {
				deck_model.setBackground(new Background(new BackgroundFill(Color.rgb(0, 255, 255), 
						CornerRadii.EMPTY, Insets.EMPTY)));
			});
			
			deck_model.setOnMouseExited(e -> {
				deck_model.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), 
						CornerRadii.EMPTY, Insets.EMPTY)));
			});
			
		} catch (URISyntaxException e) {
			Logger.getLogger(HandAndDeckModelTest.class.getName()).log(Level.SEVERE, null, e);
			
	    } catch (Exception e) {
			Logger.getLogger(HandAndDeckModelTest.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		//Cannot access nodes in HBox; mouse events are consumed at the ScrollPane
		//Since there is nothing in the ScrollPane to begin with, cannot internally handle mouse evenys
		//at the true or desired root. Therefore, must add listener so that everytime a Card is added to the 
		//HandModel, must get that node and apply margin settings and mouse event reads
		
		similar = new ArrayList<>();
		
		hand_model.widthProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println(newValue);
			
			 Node pane = hand_model.getChildren().get(hand_model.getChildren().size() - 1);
			
			 /*String path = ((ImageView)((StackPane) pane).getChildren().get(0)).getImage().getUrl();
			 System.out.println(path);
			 System.out.println(path.length());
			 System.out.println(path.substring(75, path.length() - 5));*/
			 
			 StackPane.setMargin(pane, new Insets(2.0d));
			 
			 pane.setOnMouseEntered(e -> {
				 if (!clicked)
				 ((StackPane) pane).setBackground(new Background(new BackgroundFill(
						 Color.rgb(178, 34, 34), CornerRadii.EMPTY, Insets.EMPTY)));
			 });
			 
			 pane.setOnMouseExited(e -> {
				 if (!clicked)
				 ((StackPane) pane).setBackground(new Background(new BackgroundFill(
						 Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
			 });
			 
			 pane.setOnMousePressed(e -> {
				 selected = (StackPane) pane;
				 
				 if (!clicked) {
					 ((StackPane) pane).setBackground(new Background(new BackgroundFill(
							 Color.rgb(50, 205, 50), CornerRadii.EMPTY, Insets.EMPTY)));
					 disableAllElse(hand_model.getChildren());
					 searchAndHighlight(hand_model.getChildren());
					 clicked = true;
				 } else {
					 ((StackPane) pane).setBackground(new Background(new BackgroundFill(
							 Color.rgb(255, 255, 255), CornerRadii.EMPTY, Insets.EMPTY)));
					 disableAllElse(similar);
					 clicked = false;
				 }

			 });
		});
		
		hand = new ScrollPane();
		hand.setContent(hand_model);
		hand.setVbarPolicy(ScrollBarPolicy.NEVER);
		hand.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		hand.setPannable(true);
		hand.setFitToHeight(true);
		hand.setMaxWidth(((ImageView)((StackPane) 
				deck_model.getChildren().get(0)).getChildren().get(0)).getFitWidth() * HAND_VIEWCAP);
		hand.setMaxHeight(((ImageView)((StackPane) 
				deck_model.getChildren().get(0)).getChildren().get(0)).getFitHeight());
		
		hand.setOnScroll(e -> {
			if(e.getDeltaX() == 0 && e.getDeltaY() != 0) {
				hand.setHvalue(hand.getHvalue() - e.getDeltaY() / hand.getWidth());
				e.consume();
			}
		});
		
		container.setCenter(hand);
		container.setBottom(deck_model);
		BorderPane.setAlignment(deck_model, Pos.CENTER);
		BorderPane.setMargin(deck_model, new Insets(0));
		BorderPane.setMargin(hand, new Insets(0));
		
		stage.setScene(scene);
		stage.setTitle("Deck and Hand Model Test");
		stage.show();
	}
	
	private void disableAllElse(List<Node> node_list) {
		
		for (Node node : node_list) {
			if (!((StackPane)node).equals(selected)) {
				((StackPane) node).setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255), 
						CornerRadii.EMPTY, Insets.EMPTY)));
			}
		}
		
	}
	
	private void searchAndHighlight(List<Node> node_list) {
		
		String path_selected = ((ImageView)((StackPane) selected).getChildren().get(0)).getImage().getUrl();
		
		for (Node node : node_list) {
			String path_node = ((ImageView)((StackPane) node).getChildren().get(0)).getImage().getUrl();
			
			if (path_selected.substring(75, path_selected.length() - 5).equals(
					path_node.substring(75, path_node.length() - 5))) {
				((StackPane) node).setBackground(new Background(new BackgroundFill(
						 Color.rgb(50, 205, 50), CornerRadii.EMPTY, Insets.EMPTY)));
				similar.add(node);
			}
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
