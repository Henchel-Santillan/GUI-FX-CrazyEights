package gui.util;

import javafx.application.Platform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import javafx.beans.property.StringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.geometry.Pos;

import javafx.scene.layout.StackPane;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class FamilyBox {

	private final StackPane model;
	private final ComboBox<String> box;
	private final ReadOnlyStringWrapper selected;
	
	private final ObservableList<String> fonts;
	private final FilteredList<String> filteredFonts;
	
	//TODO: Search Function + display in box
	public FamilyBox() {
		selected = new ReadOnlyStringWrapper();
		
		fonts = FXCollections.observableArrayList();
		fonts.addAll(Font.getFamilies());
		
		filteredFonts = new FilteredList<>(fonts, p -> true);
		
		box = new ComboBox<>();
		box.setEditable(true);
		
		box.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
			selected.set(newValue.getSelectedItem());
			box.getEditor().setText(newValue.getSelectedItem());
		});
		
		box.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("/^[A-Za-z]+$/")) {
				box.getEditor().setText(newValue.replaceAll("[^a-zA-Z]", ""));
			}
			
			final TextField editor = box.getEditor();
			final String selectedItem = box.getSelectionModel().getSelectedItem();
			
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					if (selectedItem == null || !selectedItem.contentEquals(editor.getText())) {
						filteredFonts.setPredicate(item -> {
							if (item.toLowerCase().startsWith(newValue.toLowerCase())) {
								return true;
							}
							return false;
						});
					}
				}
			});
		});
		
		box.setItems(filteredFonts);
		box.getEditor().setAlignment(Pos.CENTER);
		
		model = new StackPane(box);
	}
	
	public final StackPane getModel() {
		return model;
	}
	
	public final StringProperty selectedProperty() {
		return selected;
	}
	
	public final String getSelected() {
		if (selected.get() != null || !selected.get().trim().isEmpty())
			return selected.get();
		
		throw new IllegalStateException("No font selected.");
	}
	
	public final Font getSelectedAsFont() {
		if (selected.get() != null || !selected.get().trim().isEmpty())
			return Font.font(selected.get());
		
		throw new IllegalStateException("No font selected.");
	}
}
