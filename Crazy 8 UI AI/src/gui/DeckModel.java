package gui;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.List;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;


/* TODO:
 * Shuffle Animation
 * Deal/draw/pop animation
 * Deck prompt(stackpane blink)
 * 
 * */


public class DeckModel {

	private final List<CardModel> cardModelList;
	private Image cardback;		// in the event user changes cardback in Library
	
	private final StackPane skin;
	private final ImageView print;
	private final Background onPrompt;
	
	public DeckModel() {
		cardModelList = new ArrayList<>();
		
		print = new ImageView();
		
		skin = new StackPane(print);
		skin.setMaxWidth(CardModel.PRINT_WIDTH);
		skin.setMaxHeight(CardModel.PRINT_HEIGHT);
		
		try {
			
			cardback = new Image((getClass().getResource(
					"IMAGE/card_back.png").toURI().toString()));
			
			print.setImage(cardback);
			print.setFitHeight(CardModel.PRINT_HEIGHT);
			print.setFitWidth(CardModel.PRINT_WIDTH);
			print.setSmooth(true);
			
			StackPane.setMargin(print, new Insets(2.0d));
			
		} catch (URISyntaxException e) {
			Logger.getLogger(DeckModel.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(DeckModel.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		onPrompt = new Background(new BackgroundFill(Color.rgb(0, 255, 255), CornerRadii.EMPTY, Insets.EMPTY));
	}
	
	public void setOnEnter() {
		cardModelList.get(cardModelList.size() - 1).setOnEnter();
	}
	
	public void setOnExit() {
		cardModelList.get(cardModelList.size() - 1).setOnExit();
	}
	
	
}
