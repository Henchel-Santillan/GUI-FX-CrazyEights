package gui.dialog;

import gui.LimitedTextField;

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
	
	public BoxFieldDialog(Stage parent) {
		super(parent);
		
		modal.setResizable(false);
		
		labelField = new Label();
		field = new LimitedTextField();
		
		HBox fieldBox = new HBox(labelField, field);
		
		labelBox = new Label();
		box = new ChoiceBox<>();
		
		HBox boxBox = new HBox(labelBox, box);
		
		cancel.setOnAction( e-> {
			modal.close();
			e.consume();
		});
		
		field.focusedProperty().addListener(c -> {
			if (field.getText().trim().isEmpty()) {
				warning.setText(warningText.get());
				ok.setDisable(true);
			} else {
				warning.setText("");
				ok.setDisable(false);
			}
		});
		
		frame.getChildren().addAll(fieldBox, boxBox);
	}
	
	public void setFieldMaxLength(int maxLength) {
		field.setMaxLength(maxLength);
	}
	
	public void setFieldNumericOnly() {
		field.setNumericOnly();
	}
	
	public void setFieldAlphaOnly() {
		field.setAlphaOnly();
	}
	
	public void setBoxValue(T value) {
		box.setValue(value);
	}
	
	public void setBoxItems(ObservableList<? extends T> items) {
		box.getItems().addAll(items);
		box.setValue(box.getItems().get(0));
	}
	
	public void setLabelField(String labelField) {
		this.labelField.setText(labelField);
	}
	
	public void setLabelBox(String labelBox) {
		this.labelBox.setText(labelBox);
	}
	
	public void show() {	
		modal.showAndWait();
		ok.setOnAction(e -> {
			result = new Pair<>(Optional.ofNullable(field.getText()), Optional.ofNullable(box.getValue()));
			modal.close();
			e.consume();
		});
	}
	
	public Pair<Optional<String>, Optional<T>> getResult() {
		return result;
	}
}
