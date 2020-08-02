package gui.dialog;

import java.io.File;
import java.util.Optional;

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
	
	public BrowseDialog(Stage parent, final BrowseType type) {
		super(parent);
		
		this.type = type;
		
		result = Optional.empty();
		
		field = new TextField();
		browse = new Button("Browse");

		browse.setOnAction(e -> {
			if (type == BrowseType.FOLDER) {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("Browse Folders");
				
				File chosen = chooser.showDialog(parent);
				
				if (chosen == null) {
					warning.setVisible(true);
				} else {
					warning.setVisible(false);
					field.setText(chosen.getAbsolutePath());
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
					warning.setVisible(true);
				} else {
					warning.setVisible(false);
					field.setText(chosen.getAbsolutePath());
				}
			}
			e.consume();
		});
		
		HBox browseBox = new HBox(field, browse);
		
		field.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue != null && !newValue.trim().isEmpty()) {
				ok.setDisable(false);
			} else {
				ok.setDisable(true);
			}
		});
		
		ok.setOnAction(e -> {
			result = Optional.ofNullable(field.getText());
			modal.close();
			e.consume();
		});
		
		frame.getChildren().add(frame.getChildren().size() - 2, browseBox);
	}
	
	public final BrowseType getType() {
		return type;
	}
	
	public final boolean hasResult() {
		return result.isPresent();
	}

	public final Optional<String> getResult() {
		return result;
	}
}
