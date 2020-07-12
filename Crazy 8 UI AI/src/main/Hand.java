package main;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import javafx.geometry.Insets;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;

import main.Card.Rank;

public class Hand extends Pile1D {

	public static final int VIEW_CAP = 8;
	
	private final ObservableList<Card> playList;
	private final List<Card> playableList;
	private final List<Card> rankList;
	
	private final ScrollPane model;
	private final HBox container;
	private Card selected;
	
	public Hand() {
		playList = FXCollections.observableArrayList();
		rankList = new ArrayList<>();
		playableList = new ArrayList<>();	
	
		container = new HBox();
		container.setSpacing(2.0d);
		
		model = new ScrollPane();
		model.setContent(container);
		model.setVbarPolicy(ScrollBarPolicy.NEVER);
		model.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		model.setFitToHeight(true);
		model.setMaxWidth(Card.SKIN_WIDTH * VIEW_CAP);
		model.setMaxHeight(Card.SKIN_HEIGHT);
		
		model.setPannable(true);
		model.setOnScroll(e -> {
			if(e.getDeltaX() == 0 && e.getDeltaY() != 0) {
				model.setHvalue(model.getHvalue() - e.getDeltaY() / model.getWidth());
				e.consume();
			}
		});
	}
	
	public ScrollPane getModel() {
		return model;
	}
	
	public void enable(Button button) {
		playList.addListener((ListChangeListener<Card>) c ->  {
			boolean positive = c.getList().size() > 1;
			button.setDisable(positive);
			button.setVisible(positive);
		});
	}
	
	public void disableAllElse(List<Card> cardList) {
		for (Card card : cardList) {
			if (card != selected) {
				card.exited();
				card.setIsClicked(false);
				card.setIsPlayable(false);
			}
		}
	}
	
	//lastIn = dropzone.pop()
	public void markAllEligible(Card lastIn) {
		for (Card card : cardList) {
			if (card.getRank() == lastIn.getRank() || card.getSuit() == lastIn.getSuit() 
					|| card.getRank() == Rank.EIGHT) {
				card.setIsPlayable(true);
				playableList.add(card);	
			}
		}
	}
	
	//card = deck.pop()
	@Override
	public void push(Card card) {
		card.setFaceUp();
		
		if (card.isPlayable()) {
			card.getModel().setOnMouseEntered(e -> {
				if (!card.isClicked()) {
					card.entered();
				}
			});
			
			card.getModel().setOnMousePressed(e -> {
				if (!card.isClicked()) {
					card.exited();
				}
			});
			
			card.getModel().setOnMousePressed(e -> {

				selected = card;
				card.clicked();
				
				if (!card.isClicked()) {
					
					playList.add(selected);
					this.disableAllElse(this.cardList);
					
					for (Card rankCard : this.cardList) {
						if (rankCard.getRank() == selected.getRank()) {
							rankCard.getModel().setBackground(new Background(
									new BackgroundFill(Color.rgb(50, 205, 50), CornerRadii.EMPTY, Insets.EMPTY)));
							rankList.add(rankCard);
							
							rankCard.getModel().setOnMousePressed(m -> {
								rankCard.clicked();
								
								if (!rankCard.isClicked()) {
									playList.add(rankCard);
								} else {
									playList.remove(rankCard);
								}
							});
						}
					}
					
				} else {
					playList.clear();
					this.disableAllElse(rankList);
					
					for (Card playableCard : playableList) {
						playableCard.setIsPlayable(true);
					}
				}
			});
		}
		
		cardList.add(card);
		container.getChildren().add(card.getModel());
	}
	
	//cardList = deck.popAll()
	public void pushAll(List<Card> cardList) {
		for (Card card : cardList) {
			this.push(card);
		}
	}
	
	//used only if rankList turns up empty
	//REALITY: ACTUALLY NEVER USED
	public Card pop() {
		container.getChildren().remove(selected.getModel());
		return cardList.remove(cardList.indexOf(selected));
	}
	
	//used if rankList is not empty, and the player proceeds to add to playList after selected != null
	public List<Card> popAll() {
		
		for (Card card : playList) {
			container.getChildren().remove(card.getModel());
			cardList.remove(card);
		}
		
		return playList;
	}
	
	public void sortBySuit() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				cardList.set(j + 1, cardList.get(j));
				container.getChildren().set(j + 1, cardList.get(j).getModel());
				j--;
			}
			
			cardList.set(j + 1, key);
			container.getChildren().set(j + 1, key.getModel());
		}
	}
	
	public void sortByRank() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getRank().getValue() > key.getRank().getValue()) {
				cardList.set(j + 1, cardList.get(j));
				container.getChildren().set(j + 1, cardList.get(j).getModel());
				j--;
			}
			
			cardList.set(j + 1, key);
			container.getChildren().set(j + 1, key.getModel());
		}
	}
	
	public void sortByAll() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getRank().getValue() > key.getRank().getValue() &&
					cardList.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				
				cardList.set(j + 1, cardList.get(j));
				container.getChildren().set(j + 1, cardList.get(j).getModel());
				j--;
			}
				
			cardList.set(j + 1,  key);
			container.getChildren().set(j + 1, key.getModel());
		}
	}
}
