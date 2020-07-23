package gui.dialog;

import javafx.scene.paint.Color;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

import javafx.scene.layout.HBox;
import javafx.scene.control.Label;

public abstract class DialogPair {
	
	protected final HBox model;
	protected final Label description;
	
	private ReadOnlyBooleanWrapper required;
	
	public DialogPair(boolean required) {
		model = new HBox();
		description = new Label();
		
		this.required = new ReadOnlyBooleanWrapper(required);
		
		if (required) {
			description.setTextFill(Color.RED);
		}
		
		model.getChildren().add(description);
	}
	
	public DialogPair(boolean required, String description) {
		this(required);
		this.description.setText(description);
	}
	
	public HBox getModel() {
		return model;
	}
	
	public ReadOnlyBooleanProperty requiredProperty() {
		return required;
	}
	
	public boolean isRequired() {
		return required.get();
	}
	
	public void setDescription(String description) {
		this.description.setText(description);
	}
}
