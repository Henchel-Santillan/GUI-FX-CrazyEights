package gui;

import java.util.List;
import java.util.ArrayList;

import utility.Card;

public abstract class Pile1DModel {
	
	protected final List<CardModel> cardModelList;
	
	public Pile1DModel() {
		cardModelList = new ArrayList<>();
	}
	
	List<CardModel> getCardModelList() {
		return cardModelList;
	}
	
	abstract void push(CardModel cardModel);
	abstract void pushAll(List<CardModel> cardModelList);
	
	/*abstract CardModel pop(Card card);
	abstract List<CardModel> popAll();	//pop methods need edits*/
}
