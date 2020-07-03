package gui_deprecated;

import java.util.List;
import java.util.ArrayList;

import javafx.geometry.Insets;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import utility_deprecated.Card;

public class HandModel {
	
	public static final int VIEW_CAP = 8;
	
	private final List<CardModel> cardModelList;
	private final List<CardModel> rankSimilar;

	private final ScrollPane container;
	private final HBox skin;
	private CardModel selected;
	
	public HandModel() {
		cardModelList = new ArrayList<>();
		rankSimilar = new ArrayList<>();
		
		skin = new HBox();
		skin.setSpacing(2.0d);
		
		container = new ScrollPane();
		container.setContent(skin);
		container.setVbarPolicy(ScrollBarPolicy.NEVER);
		container.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		container.setPannable(true);
		container.setFitToHeight(true);
		container.setMaxWidth(CardModel.PRINT_WIDTH * VIEW_CAP);
		container.setMaxHeight(CardModel.PRINT_HEIGHT);
		
		container.setOnScroll(e -> {
			if(e.getDeltaX() == 0 && e.getDeltaY() != 0) {
				container.setHvalue(container.getHvalue() - e.getDeltaY() / container.getWidth());
				e.consume();
			}
		});
	}
	
	public List<CardModel> getModels() {
		return cardModelList;
	}
	
	public List<CardModel> getKin() {
		return rankSimilar;
	}
	
	public ScrollPane getContainer() {
		return container;
	}
	
	public CardModel getSelected() {
		return selected;
	}
	
	public void add(CardModel cardModel) {	//deck should have pop	
		cardModel.setFaceUp();
		StackPane.setMargin(cardModel.getSkin(), new Insets(2.0d));
		
		cardModel.getSkin().setOnMouseEntered(e -> {
			if (!cardModel.isClicked()) {
				cardModel.setOnEnter();
				e.consume();
			}
		});
		
		cardModel.getSkin().setOnMouseExited(e -> {
			if (!cardModel.isClicked()) {
				cardModel.setOnExit();
				e.consume();
			}
		});
		
		cardModel.getSkin().setOnMousePressed(e -> {
			selected = cardModel;
			
			if (!cardModel.isClicked()) {
				cardModel.setClicked(true);
				cardModel.setOnClick();
				this.disableAllElse(cardModelList);
				
				for (CardModel similar : cardModelList) {
					if (similar.getRank() == selected.getRank()) {
						similar.setClicked(true);
						similar.setOnClick();
						rankSimilar.add(similar);
					}
				}
				
			} else {
				cardModel.setClicked(false);
				cardModel.setOnExit();
				this.disableAllElse(rankSimilar);
			}
		});
		
		cardModelList.add(cardModel);
		skin.getChildren().add(cardModel.getSkin());
	}
	
	public void addAll(CardModel...cardModels) {
		for (CardModel cardModel : cardModels) {
			this.add(cardModel);
		}
	}
	
	public void disableAllElse(List<CardModel> cardModels) {
		for (CardModel cardModel : cardModels) {
			if (!cardModel.equals(selected)) {
				cardModel.setOnExit();
				cardModel.setClicked(false);
			}
		}
	}
	
	//Called only on sort operations
	//change list is the updated card_set of Hand
	public void refresh(List<Card> change_list) {
		//clear cardModel_list, rank_similar, and the skin
		//repopulate the above
		cardModelList.clear();
		skin.getChildren().clear();
		
		for (Card card : change_list) {
			this.add(card.getModel());
		}
	}
}
