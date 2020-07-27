package gui.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import javafx.geometry.Pos;

import javafx.scene.Node;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class WeightBox {

	private final HBox model;
	private final ComboBox<Integer> box;
	private final Button minus, plus;
	
	private final ReadOnlyIntegerWrapper maxSize;
	private ReadOnlyIntegerWrapper current;
	
	public WeightBox(int maxSize) {
		this.maxSize = new ReadOnlyIntegerWrapper(maxSize);
		current = new ReadOnlyIntegerWrapper();
		
		box = new ComboBox<>();
		box.getItems().addAll(6, 8, 10, 11, 12, 14, 18, 24, 36, 48);
		box.setEditable(true);
		
		box.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
			current.set(newValue.getSelectedItem());
		});
		
		box.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				box.getEditor().setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
		
		box.getEditor().focusedProperty().addListener((observable, oldValue, newValue) -> {
			String input = box.getEditor().getText();
			
			if (!newValue && input!= null && !input.trim().isEmpty()) {
				int filtered = (Integer.valueOf(input) > maxSize) ? maxSize : Integer.valueOf(input);
				box.setValue(filtered);
				current.set(box.getValue());
			}
		});
		
		box.getEditor().setAlignment(Pos.CENTER);
		
		minus = new Button("-");
		minus.setOnAction(e -> {
			if (current.get() > 0) {
				current.set(current.get() - 1);
				box.setValue(current.get());
			}
			e.consume();
		});
		
		plus = new Button("+");
		plus.setOnAction(e -> {
			if (current.get() < maxSize) {
				current.set(current.get() + 1);
				box.setValue(current.get());
			}
			e.consume();
		});
		
		model = new HBox(minus, box, plus);
		model.setAlignment(Pos.CENTER);
		
		for (Node node : model.getChildren()) {
			HBox.setHgrow(node, Priority.ALWAYS);
		}
	}
	
	public HBox getModel() {
		return model;
	}
	
	public IntegerProperty maxSizeProperty() {
		return maxSize;
	}
	
	public int getMaxSize() {
		return maxSize.get();
	}
	
	public IntegerProperty currentProperty() {
		return current;
	}
	
	public int getCurrent() {
		return current.get();
	}
}
