package gui.dialog;

import java.util.Optional;
import javafx.scene.control.TextField;

public class TextDialogPair extends DialogPair {
	
	private final TextField control;
	
	public TextDialogPair(boolean required) {
		super(required);
		control = new TextField();
		model.getChildren().add(control);
	}
	
	public TextDialogPair(boolean required, String description) {
		super(required, description);
		control = new TextField();
		model.getChildren().add(control);
	}
	
	public Optional<String> get() {
		if (control.getLength() == 0) {
			return Optional.empty();
		}
		return Optional.ofNullable(control.getText());
	}
}
