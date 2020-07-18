package test_main;

import java.util.Optional;

import javafx.stage.Stage;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import javafx.collections.ObservableList;

public class BoxFieldDialog<T> extends CustomDialog<T> {
	
	private final LimitedTextField field;
	private final ChoiceBox<T> box;
	private final Label labelField, labelBox, warning;
	private final Button ok, cancel;
	
	//TODO: Style the BoxFieldDialog
	public BoxFieldDialog(Stage parent) {
		super(parent);
		
		labelField = new Label();
		field = new LimitedTextField();
		
		Label warningField = new Label();
		warningField.setTextFill(Color.RED);
		
		field.focusedProperty().addListener(c -> {
			if (field.getText().trim().isEmpty()) {
				warningField.setText("REQUIRED");
			}
		});
		
		HBox fieldBox = new HBox(labelField, field, warningField);
		
		labelBox = new Label();
		box = new ChoiceBox<>();
		
		HBox boxBox = new HBox(labelBox, box);
		
		warning = new Label();
		
		ok = new Button("OK");
		cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			modal.close();
			e.consume();
		});

		HBox buttonBox = new HBox(ok, cancel);
		buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
		
		frame.getChildren().addAll(fieldBox, boxBox, warning, buttonBox);
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
	}
	
	public void setLabelField(String labelField) {
		this.labelField.setText(labelField);
	}
	
	public void setLabelBox(String labelBox) {
		this.labelBox.setText(labelBox);
	}
	
	public void setWarning(String warning) {
		this.warning.setText(warning);
	}
	
	//TODO: Define getting process for field information
	public Optional<T> displayAndWait() {
		modal.showAndWait();
		ok.setOnAction(e -> {
			/* TODO:
			 * Validate responses: check if LimitedTextField is not empty + response in choicebox
			 * 	if none:
			 * 		must reject the dialog and recall responses + set warning label
			 * 	else:
			 * 		collects information and returns in an Optional
			 * 
			 */
			e.consume();
		});
	}
}
