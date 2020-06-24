package utility;

import java.net.URISyntaxException;

import java.util.Random;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class Deck extends Pile1D {

	private final Random random = new Random();
	
	public static final int DEF_SIZE = 52;
	public static final int REC_MAX = 8;
	
	private final ImageView model;
	
	public Deck() {
		super(DEF_SIZE);
		
		//initialize Deck
		for (int i = 0; i < Suit.values().length; i++) {
			for (int j = 0; j < Rank.values().length; j++) {
				Card card = new Card(Rank.values()[j], Suit.values()[i], State.NONE);
				card.setFaceUp(false);
				
				this.card_set.add(card);
			}
		}
		
		model = new ImageView();
		
		try {
			model.setImage(new Image(
					getClass().getResource("/IMAGE/card_back.jpeg").toURI().toString()));
			model.setPreserveRatio(true);
			model.setFitHeight(this.card_set.get(0).getModel().getImageView().getFitHeight());
			model.setFitWidth(this.card_set.get(0).getModel().getImageView().getFitWidth());
			
		} catch (URISyntaxException e) {
			
			System.err.println("Incorrect local path specification. \n");
			Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Deck.class.getName()).log(Level.SEVERE, null, e);
			
		}
	}

	public ImageView getModel() {
		return model;
	}
	
	public void deal(Hand other) { 
		this.put(other, this.card_set.get(this.card_set.size() - 1));
	}
	
	public void deal(Hand other, int toAdd) {   
		for (int i = 0; i < toAdd; i++) {
			this.deal(other);
		}
	}
	
	public void shuffle() {
		for (int i = this.card_set.size() - 1; i > 0; i--) {
			int j = random.nextInt(i + 1);
			
			Card temp = this.card_set.get(i);
			this.card_set.set(i, this.card_set.get(j));
			this.card_set.set(j, temp);
		}
	}
	
	public void applyHints() {
		//colour hints to image viewport, etc.
	}
}
