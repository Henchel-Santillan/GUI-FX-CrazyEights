package gui.dialog;

import java.util.Collection;
import java.util.Optional;
import javafx.scene.control.ChoiceBox;

public class BoxDialogPair<T> extends DialogPair {
	
	private final ChoiceBox<T> control;
	
	public BoxDialogPair(boolean required) {
		super(required);
		control = new ChoiceBox<>();
		model.getChildren().add(control);
	}
	
	public BoxDialogPair(boolean required, Collection<? extends T> items) {
		this(required);
		control.getItems().addAll(items);
	}
	
	public BoxDialogPair(boolean required, String description) {
		super(required, description);
		control = new ChoiceBox<>();
		model.getChildren().add(control);
	}
	
	public BoxDialogPair(boolean required, String description, Collection<? extends T> items) {
		this(required, description);
		control.getItems().addAll(items);
	}
	
	public void add(T item) {
		control.getItems().add(item);
	}
	
	public void addAll(Collection<? extends T> items) {
		control.getItems().addAll(items);
	}

	public Optional<T> get() {
		return Optional.ofNullable(control.getValue());
	}
}
