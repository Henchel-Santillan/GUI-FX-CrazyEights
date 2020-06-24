package gui;

import java.util.Objects;

import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import utility.Card;

public class HandModel {
	
	final ListView<Card> model;
	final ObservableList<Card> model_list;
	
	public HandModel() {
		model = new ListView<>();
		model_list = FXCollections.observableArrayList();
		
		model.setItems(model_list);
	}
	
	public ListView<Card> get() {
		return model;
	}
	
	public void add(Card card) {
		model_list.add(card);
		model.refresh();
	}
	
	public void add(Card... cards) {
		model_list.addAll(cards);
		model.refresh();
	}
	
	public void remove(Card card) {
		model_list.remove(card);
		model_list.removeIf(Objects::isNull);
		
		model.refresh();
	}
	
	public void remove(Card... cards) {
		model_list.removeAll(cards);
	}
}
