package gui.dialog;


import gui.util.LimitedTextField;

import java.util.Optional;
import javafx.util.Pair;

import javafx.stage.Stage;
import javafx.collections.ObservableList;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;

public class BoxFieldDialog<T> extends DialogUtil {

	private final LimitedTextField field;
	private final ChoiceBox<T> box;
	private final Label labelField, labelBox;
	
	private Pair<Optional<String>, Optional<T>> result;
	
	//TODO: Issue with result Field returning NullPointer. May need to implement in line with blocking dialog.
	public BoxFieldDialog(Stage parent) {
		super(parent);
	
		result = new Pair<>(null, null);
		
		labelField = new Label();
		field = new LimitedTextField();
		
		HBox fieldBox = new HBox(labelField, field);
		frame.getChildren().add(frame.getChildren().size() - 2, fieldBox);
		
		labelBox = new Label();
		box = new ChoiceBox<>();
		
		HBox boxBox = new HBox(labelBox, box);
		frame.getChildren().add(frame.getChildren().size() - 2, boxBox);
		
		field.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				if (field.getText().trim().isEmpty()) {
					warning.setVisible(true);
					ok.setDisable(true);
				} else {
					warning.setVisible(false);
					ok.setDisable(false);
				}
			}
		});
		
		field.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && !newValue.trim().isEmpty()) {
				warning.setVisible(false);
				ok.setDisable(false);
			} else {
				warning.setVisible(true);
				ok.setDisable(true);
			}
		});
		
		ok.setOnAction(e -> {
			result = new Pair<>(Optional.of(field.getText()), Optional.of(box.getValue()));
			modal.hide();
			e.consume();
		});
	}
	
	public final void setFieldMaxLength(int maxLength) {
		field.setMaxLength(maxLength);
	}
	
	public final void setFieldNumericOnly() {
		field.setNumericOnly();
	}
	
	public final void setFieldAlphaOnly() {
		field.setAlphaOnly();
	}
	
	public final void setBoxValue(T value) {
		box.setValue(value);
	}
	
	public final void setBoxItems(ObservableList<? extends T> items) {
		box.getItems().addAll(items);
		box.setValue(box.getItems().get(0));
	}
	
	public final void setLabelField(String labelField) {
		this.labelField.setText(labelField);
	}
	
	public final void setLabelBox(String labelBox) {
		this.labelBox.setText(labelBox);
	}
	
	public final boolean hasResult() {
		if (result.getKey() == null || result.getValue() == null) {
			return false;
		}
		return true;
	}

	public final Pair<Optional<String>, Optional<T>> getResult() {
		return result;
	}
}
