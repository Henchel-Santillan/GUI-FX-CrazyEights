package gui.dialog;

import java.io.File;
import java.util.Optional;

import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.BooleanProperty;

import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.scene.layout.HBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class BrowseDialog extends DialogUtil {

	public enum BrowseType {
		FOLDER, FILE;
	}
	
	private final TextField field;
	private final Button browse;
	private final BrowseType type;
	
	private Optional<String> result;
	private ReadOnlyBooleanWrapper selected;
	
	public BrowseDialog(Stage parent, final BrowseType type) {
		super(parent);
		
		this.type = type;
		selected = new ReadOnlyBooleanWrapper(false);
		
		field = new TextField();
		browse = new Button("Browse");

		browse.setOnAction(e -> {
			if (type == BrowseType.FOLDER) {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("Browse Folders");
				
				File chosen = chooser.showDialog(parent);
				
				if (chosen == null) {
					warning.setText("No folder selected.");
					selected.set(false);
				} else {
					field.setText(chosen.getAbsolutePath());
					selected.set(true);
				}
			} else {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Browse Files");
				chooser.getExtensionFilters().addAll(
						new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.mp4"),
						new ExtensionFilter("Image Files", "*.png", "*.jpeg", "*.jpg"),
						new ExtensionFilter("All files", "*.*")
						);
				
				File chosen = chooser.showOpenDialog(parent);
				
				if (chosen == null) {
					warning.setText("No file selected.");
					selected.set(false);
				} else {
					field.setText(chosen.getAbsolutePath());
					selected.set(true);
				}
			}
			e.consume();
		});
		
		HBox browseBox = new HBox(field, browse);
		
		selected.addListener((observable, oldValue, newValue) -> {
			ok.setDisable(newValue);
		});
		
		frame.getChildren().add(browseBox);
	}
	
	public final BrowseType getType() {
		return type;
	}
	
	public final BooleanProperty selectedProperty() {
		return selected;
	}
	
	public final boolean hasSelected() {
		return selected.get();
	}
	
	public final void show() {
		modal.showAndWait();
		ok.setOnAction(e -> {
			result = Optional.ofNullable(field.getText());
			e.consume();
		});
	}
	
	public final Optional<String> getResult() {
		return result;
	}
}
