package test_main;

import java.io.File;
import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.stage.Stage;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.layout.HBox;

import javafx.scene.control.TextField;
import javafx.scene.control.Button;

public class BrowseDialog<T> extends CustomDialog<T> {
	
	private final TextField field;
	private final Button browse, ok, cancel;
	
	private BooleanProperty isBrowseFolder;
	
	//browseFolder means DirecroyChooser if true, FileChooser if false
	public BrowseDialog(Stage parent, boolean isBrowseFolder) {
		super(parent);
		
		this.isBrowseFolder = new SimpleBooleanProperty(isBrowseFolder);
		
		field = new TextField();
		browse = new Button("BROWSE");
		browse.setOnAction(e -> {
			if (isBrowseFolder) {
				DirectoryChooser chooser = new DirectoryChooser();
				chooser.setTitle("Browse Folders");
				
				File chosen = chooser.showDialog(parent);
				
				if (chosen == null) {
					field.setText("No directory selected.");
				} else {
					field.setText(chosen.getAbsolutePath());
				}
				
			} else {
				FileChooser chooser = new FileChooser();
				chooser.setTitle("Browse Files");
			}
			e.consume();
		});
		
		HBox browseBox = new HBox(field, browse);
		
		ok = new Button("OK");
		cancel = new Button("Cancel");
		cancel.setOnAction(e -> {
			modal.close();
			e.consume();
		});
		
		HBox buttonBox = new HBox(ok, cancel);
		
		frame.getChildren().addAll(browseBox, buttonBox);
	}
	
	public BooleanProperty isBrowseFolderProperty() {
		return isBrowseFolder;
	}
	
	public boolean isBrowseFolder() {
		return isBrowseFolder.get();
	}
	
	public void setFileExtensionFilters(String...filters) {
		
	}
	
	//IF BROWSE SELECTED FILECHOOSER POPUP; Optional returns the String in the Textfield
	public Optional<T> displayAndWait() {
		ok.setOnAction(e -> {
			
			
			e.consume();
		});
	}
}
