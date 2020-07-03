package gui;

import java.util.List;
import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import utility.Card;
import utility.Hand;

public class HandModel extends Pile1DModel {

	private final List<CardModel> ofSameRank;
	
	private final ScrollPane skin;
	private final HBox container;
	private CardModel selected;
	
	public HandModel() {
		super();
		ofSameRank = new ArrayList<>();
		
		container = new HBox();
		container.setSpacing(2.0d);
		
		skin = new ScrollPane();
		skin.setContent(container);
		skin.setVbarPolicy(ScrollBarPolicy.NEVER);
		skin.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		skin.setFitToHeight(true);
		
		skin.setMaxWidth(CardModel.PRINT_WIDTH * Hand.DEFAULT_SIZE);
		skin.setMaxHeight(CardModel.PRINT_HEIGHT);
		
		skin.setPannable(true);
		skin.setOnScroll(e -> {
			if(e.getDeltaX() == 0 && e.getDeltaY() != 0) {
				skin.setHvalue(skin.getHvalue() - e.getDeltaY() / skin.getWidth());
				e.consume();
			}
		});
	}
	
	void push(CardModel cardModel) {
		cardModelList.add(cardModel);
	}
	
	void pushAll(List<CardModel> cardModelList) {
		for (CardModel cardModel : cardModelList) {
			this.push(cardModel);
		}
	}
}
