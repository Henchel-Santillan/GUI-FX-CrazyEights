package gui.dialog;

import java.util.Optional;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;

public class RadioDialogPair extends DialogPair {
	
	private final RadioButton control;
	
	public RadioDialogPair(boolean required) {
		super(required);
		control = new RadioButton();
		model.getChildren().add(control);
	}
	
	public RadioDialogPair(boolean required, String description) {
		super(required, description);
		control = new RadioButton();
		model.getChildren().add(control);
	}
	
	public void setToggleGroup(ToggleGroup group) {
		control.setToggleGroup(group);
	}
	
	public boolean isSelected() {
		return control.isSelected();
	}
	
	public Optional<Boolean> get() {
		return Optional.of(this.isSelected());
	}
}
