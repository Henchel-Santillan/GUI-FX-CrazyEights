package main;

import java.util.List;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Button;

public class Hand extends Pile1D {

	public static final int VIEW_CAP = 8;
	
	private final ObservableList<Card> playList;
	private final List<Card> rankList;
	
	private final VBox model;
	private final ScrollPane container;
	private final HBox box;
	private final Button confirmMove;
	private Card selected;
	
	public Hand() {
		playList = FXCollections.observableArrayList();
		rankList = new ArrayList<>();
		
		box = new HBox();
		box.setSpacing(2.0d);
		
		container = new ScrollPane();
		container.setContent(box);
		container.setVbarPolicy(ScrollBarPolicy.NEVER);
		container.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		
		container.setFitToHeight(true);
		container.setMaxWidth(Card.SKIN_WIDTH * VIEW_CAP);
		container.setMaxHeight(Card.SKIN_HEIGHT);
		
		container.setPannable(true);
		container.setOnScroll(e -> {
			if(e.getDeltaX() == 0 && e.getDeltaY() != 0) {
				container.setHvalue(container.getHvalue() - e.getDeltaY() / container.getWidth());
				e.consume();
			}
		});
		
		confirmMove = new Button("Confirm");
		confirmMove.setDisable(true);
		
		playList.addListener((ListChangeListener<Card>) c ->  {
			confirmMove.setDisable(c.getList().size() < 1);
		});
		
		model = new VBox();
		model.setAlignment(Pos.CENTER);
		VBox.setVgrow(confirmMove, Priority.NEVER);
		
		//TODO: confirmMove design
	}
	
	public VBox getModel() {
		return model;
	}
	
	public void disableAllElse(List<Card> cardList) {
		for (Card card : cardList) {
			if (card != selected) {
				card.exited();
				card.setIsClicked(false);
			}
		}
	}
	
	//card = dropzone.pop()
	public void markAllEligible(Card lastIn) {
		for (Card card : cardList) {
			if (card.getRank() == lastIn.getRank() || card.getSuit() == lastIn.getSuit()) {
				card.setIsPlayable(true);
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
				//TODO: Add a button control that floats so that user can confirm selection up to most recent point
				
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
									playList.add(rankList.remove(rankList.indexOf(rankCard)));
								} else {
									rankList.add(playList.remove(playList.indexOf(rankCard)));
								}
							});
						}
					}
					
				} else {
					playList.remove(selected);
					this.disableAllElse(rankList);
				}
			});
		}
		
		cardList.add(card);
		box.getChildren().add(card.getModel());
	}
	
	//cardList = deck.popAll()
	public void pushAll(List<Card> cardList) {
		for (Card card : cardList) {
			this.push(card);
		}
	}
	
	//used only if rankList turns up empty
	public Card pop() {
		box.getChildren().remove(selected.getModel());
		return cardList.remove(cardList.indexOf(selected));
	}
	
	//used if rankList is not empty, and the player proceeds to add to playList after selected != null
	public List<Card> popAll() {
		
		for (Card card : playList) {
			box.getChildren().remove(card.getModel());
		}
		
		return playList;
	}
	
	public void sortBySuit() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				cardList.set(j + 1, cardList.get(j));
				box.getChildren().set(j + 1, cardList.get(j).getModel());
				j--;
			}
			
			cardList.set(j + 1, key);
			box.getChildren().set(j + 1, key.getModel());
		}
	}
	
	public void sortByRank() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getRank().getValue() > key.getRank().getValue()) {
				cardList.set(j + 1, cardList.get(j));
				box.getChildren().set(j + 1, cardList.get(j).getModel());
				j--;
			}
			
			cardList.set(j + 1, key);
			box.getChildren().set(j + 1, key.getModel());
		}
	}
	
	public void sortByAll() {
		for (int i = 1; i < cardList.size(); i++) {
			
			Card key = cardList.get(i);
			int j = i - 1;
			
			while (j >= 0 && cardList.get(j).getRank().getValue() > key.getRank().getValue() &&
					cardList.get(j).getSuit().getIterator() > key.getSuit().getIterator()) {
				
				cardList.set(j + 1, cardList.get(j));
				box.getChildren().set(j + 1, cardList.get(j).getModel());
				j--;
			}
				
			cardList.set(j + 1,  key);
			box.getChildren().set(j + 1, key.getModel());
		}
	}
	
}
