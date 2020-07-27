package gui.util;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import javafx.geometry.Pos;

import javafx.scene.Node;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Button;

public class WeightBar {

	private final HBox model;
	private final LimitedTextField field;
	private final Menu menu;
	private final Button minus, plus;
	
	private final ReadOnlyIntegerWrapper maxSize;
	private ReadOnlyIntegerWrapper current;
	
	public WeightBar(int maxSize) {
		field = new LimitedTextField();
		field.setMaxLength(3);
		field.setNumericOnly();
		field.setAlignment(Pos.CENTER);
		
		minus = new Button("-");
		minus.setOnAction(e -> {
			if (current.get() > 0) {
				current.set(current.get() - 1);
				field.setText(String.valueOf(current.get()));
			}
			e.consume();
		});
		
		plus = new Button("+");
		plus.setOnAction(e -> {
			if (current.get() < maxSize) {
				current.set(current.get() + 1);
				field.setText(String.valueOf(current.get()));
			}
			e.consume();
		});
		
		menu = new Menu();
		menu.getItems().addAll(
				new MenuItem("6"),
				new MenuItem("8"),
				new MenuItem("10"),
				new MenuItem("12"),
				new MenuItem("14"),
				new MenuItem("16"),
				new MenuItem("18"),
				new MenuItem("24"));
		menu.setDisable(false);
		
		for (MenuItem item : menu.getItems()) {
			item.setOnAction(e -> {
				field.setText(item.getText());
				e.consume();
			});
		}
		
		field.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				menu.show();
				
			} else {
				menu.hide();
				String fontInput = field.getText();
				
				if (fontInput != null && !fontInput.trim().isEmpty()) {
					int newFont = (Integer.valueOf(fontInput) > maxSize) ? maxSize : Integer.valueOf(fontInput);
					current.set(newFont);
					field.setText(String.valueOf(newFont));
				}
			}
		});
		
		current = new ReadOnlyIntegerWrapper();
		this.maxSize = new ReadOnlyIntegerWrapper(maxSize);
		
		model = new HBox(minus, field, plus);
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
