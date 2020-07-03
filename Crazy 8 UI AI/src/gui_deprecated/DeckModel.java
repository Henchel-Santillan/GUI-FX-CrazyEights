package gui_deprecated;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

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

import javafx.scene.control.Label;

import javafx.scene.image.ImageView;
import javafx.scene.media.AudioClip;

/* TODO:
 * Shuffle Animation
 * Deal/draw/pop animation
 * Deck prompt(stackpane blink)
 * 
 * */


public class DeckModel {

	private final List<CardModel> cardModelList;	
	
	private final StackPane skin;
	private final ImageView print;
	private final Label number;
	private final Background onPrompt;
	
	private final BooleanProperty isOnPrompt;
	
	public DeckModel() {
		cardModelList = new ArrayList<>();
		
		print = new ImageView();
		
		skin = new StackPane(print);
		skin.setMaxWidth(CardModel.PRINT_WIDTH);
		skin.setMaxHeight(CardModel.PRINT_HEIGHT);
		
		onPrompt = new Background(new BackgroundFill(
				Color.rgb(0, 255, 255), CornerRadii.EMPTY, Insets.EMPTY));
		
		isOnPrompt = new SimpleBooleanProperty(false);
		
		number = new Label();
	}
	
	public StackPane getSkin() {
		return skin;
	}
	
	public void setItems(List<CardModel> cardModels) {
		for (CardModel cardModel : cardModels) {
			cardModel.setFaceDown();
			cardModelList.add(cardModel);
			skin.getChildren().add(cardModel.getSkin());
			
			cardModel.getSkin().setOnMouseEntered(e -> {
				cardModel.setOnEnter();
			});
			
			cardModel.getSkin().setOnMouseExited(e -> {
				cardModel.setOnExit();
			});	
		}
		
		skin.getChildren().add(number);
		number.setText(String.valueOf(Double.valueOf(cardModelList.size())));
	}
	
	public void add(CardModel cardModel) {
		
		cardModel.getSkin().setOnMouseEntered(e -> {
			cardModel.setOnEnter();
		});
		
		cardModel.getSkin().setOnMouseExited(e -> {
			cardModel.setOnExit();
		});	
		
		cardModelList.add(cardModel);
		skin.getChildren().add(cardModel.getSkin());
	}
	
	public void setOnPrompt() {
		cardModelList.get(cardModelList.size() - 1).getSkin().setBackground(onPrompt);
		isOnPrompt.set(true);
	}
	
	public CardModel pop() {
		
		try {
			AudioClip clip = new AudioClip(getClass().getResource(
					"/AUDIO/cardShove4.wav").toURI().toString());
			clip.setCycleCount(1);
			clip.play();
			
		} catch (Exception e) {
			Logger.getLogger(DeckModel.class.getName()).log(Level.SEVERE, null, e);
		}
		
		number.setText(String.valueOf(Double.valueOf(number.getText()) - 1));
		return cardModelList.remove(cardModelList.size() - 1);
	}
	
	public void shuffle() {
		
	}
	
}
