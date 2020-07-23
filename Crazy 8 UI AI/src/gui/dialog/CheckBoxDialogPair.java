package gui.dialog;

import java.util.Optional;
import javafx.scene.control.CheckBox;

public class CheckBoxDialogPair extends DialogPair {

	private final CheckBox control;
	
	public CheckBoxDialogPair(boolean required) {
		super(required);
		control = new CheckBox();
		model.getChildren().add(control);
	}
	
	public CheckBoxDialogPair(boolean required, String description) {
		super(required, description);
		control = new CheckBox();
		model.getChildren().add(control);
	}
	
	public boolean isSelected() {
		return control.isSelected();
	}
	
	public boolean isIndeterminate() {
		return control.isIndeterminate();
	}
	
	public void setIndeterminate(boolean permission) {
		control.setAllowIndeterminate(permission);
	}
	
	public Optional<Boolean> get() {
		return Optional.of(this.isSelected());
	}
}
