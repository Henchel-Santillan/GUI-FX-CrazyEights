package gui.util;

import javafx.collections.ObservableList;
import javafx.beans.property.StringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

import javafx.geometry.Pos;

import javafx.scene.Node;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.Priority;

import javafx.scene.control.ComboBox;
import javafx.scene.text.Font;

public class FamilyBox {

	private final StackPane model;
	private final ComboBox<String> box;
	private final ReadOnlyStringWrapper selected;
	
	//TODO: Search Function + display in box
	public FamilyBox() {
		selected = new ReadOnlyStringWrapper();
		
		box = new ComboBox<>();
		box.setEditable(true);
		box.getItems().addAll(Font.getFamilies());
		
		box.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
			selected.set(newValue.getSelectedItem());
		});
		
		box.getEditor().accessibleTextProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("/^[A-Za-z]+$/")) {
				box.getEditor().setText(newValue.replaceAll("[^a-zA-Z]", ""));
			}
		});
		
		box.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
			//TODO: Use iterative binary or jump search to determine location of Font family
			String input = box.getEditor().getText().trim();
			
			//Hide all none-matching
			//TODO: Use sorted and filteredlist
			if (!newValue && input != null && !input.isEmpty()) {
				int index = binarySearch(box.getItems(), input);
				
				if (index != -1) {
					box.getEditor().setText(box.getItems().get(index));
				}
			}
		});
		
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
	
	private final int binarySearch(ObservableList<String> array, String input) {
		int left = 0, right = array.length - 1;
		
		while (left <= right) {
			int median = left + (right - left) / 2;
			
			if (array[median].equalsIgnoreCase(input)) {
				return median;
			}
			
			if (array[median].compareToIgnoreCase(input) < 0) {
				left = median + 1;
			} else {
				right = median - 1;
			}
		}
		
		return -1;
	}
}
