package gui.util;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;

public class ToggleSwitch {

	private final HBox model;
	private final Label label;
	private final Button toggle;
	
	private final ReadOnlyBooleanWrapper selected;
	private String toggled, nontoggled;
	
	public ToggleSwitch(double width) {
		selected = new ReadOnlyBooleanWrapper(false);
		
		toggled = "ON";
		nontoggled = "OFF";
		
		label = new Label();
		label.setStyle("-fx-text-fill: white;"
					 + "-fx-text-alignment: center;");
		
		toggle = new Button();
		
		toggle.setOnAction(e -> {
			this.fire();
			e.consume();
		});
		
		selected.addListener((observable, oldValue, newValue) -> {			
			if (newValue) {
				label.setText(toggled);
				label.setStyle("-fx-background-color: green;");
				label.toFront();
				
			} else {
				label.setText(nontoggled);
				label.setStyle("-fx-background-color: red;");
				toggle.toFront();
			}
		});
		
		model = new HBox(label, toggle);
		model.setPrefWidth(width);
		model.setAlignment(Pos.CENTER_LEFT);
		model.setStyle("-fx-background-radius: 4px;");
		
		label.prefWidthProperty().bind(model.widthProperty().divide(2.0d));
		label.prefHeightProperty().bind(model.heightProperty());
		toggle.prefWidthProperty().bind(model.widthProperty().divide(2.0d));
		toggle.prefHeightProperty().bind(model.heightProperty());
	}
	
	public ToggleSwitch(double width, String toggled, String nontoggled) {
		this(width);
		this.label.setText(nontoggled);
	}
	
	public HBox getModel() {
		return model;
	}
	
	public BooleanProperty selectedProperty() {
		return selected;
	}
	
	public boolean isSelected() {
		return selected.get();
	}
	
	public void setToggleTexts(String toggled, String nontoggled) {
		this.toggled = toggled;
		this.nontoggled = nontoggled;
	}
	
	public void fire() {
		selected.set(!selected.get());
	}
}
