package test_main;

import java.util.Optional;
import javafx.util.Duration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;

 //TODO: Custom cursor? (imported image) --> YJ
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Slider;
import javafx.scene.control.ListView;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

//TODO: Style the Class
public class MediaControl {

	private final BorderPane container;
	private final MediaView mediaView;
	private final HBox mediaBar, menuBox;
	private final Menu fileMenu;
	private final Label message;
	/*private final VBox store;
	private final ScrollPane mediaBox;*/
	private final ListView<Button> mediaBox;
	private final ObservableList<Button> store;
	
	private final ContextMenu mediaTabMenu;
	
	public MediaControl() {
		container = new BorderPane();
		mediaView = new MediaView();
		
		store = FXCollections.observableArrayList();
		mediaBox = new ListView<>(store);
		
		message = new Label();
		
		menuBox = new HBox();
		menuBox.setSpacing(2.0d);
		menuBox.setAlignment(Pos.CENTER);
		
		fileMenu = new Menu();
		MenuItem directoryChange = new MenuItem("Edit Copy Path");
		directoryChange.setOnAction(e -> {
			//TODO: Custom File System Browse Dialog
			
		});
		
		
		mediaTabMenu = new ContextMenu();
		MenuItem addToQueue = new MenuItem("Add to Queue");
		addToQueue.setOnAction(e -> {
			//TODO: Add to Queue
		});
		
		MenuItem deleteTab = new MenuItem("Delete Media Tab");
		deleteTab.setOnAction(e -> {
			Alert deleteConfirmation = new Alert(AlertType.CONFIRMATION);
			deleteConfirmation.setTitle("Delete Media Tab");
			deleteConfirmation.setHeaderText("WARNING: Delete in progress");
			deleteConfirmation.setContentText("Deleting this media tab will remove it "
					+ "from the game library. You will still have your local copy, however."
					+ "Click 'OK' to confirm and 'Cancel' to go back.");
			
			Optional<ButtonType> result = deleteConfirmation.showAndWait();
			
			if (result.get() == ButtonType.OK) {
				Button removed = mediaBox.getSelectionModel().getSelectedItem();
				if (store.size() > 0 && removed != null) {
					message.setText("Media Tab " + store.indexOf(removed) + " deleted.");
					store.remove(removed);
					mediaBox.refresh();
				} else {
					message.setText("No item selected.");
				}
			} else {
				deleteConfirmation.close();
			}
		});
		
		//TODO: Style addToQueue Green and deleteTab red
		mediaTabMenu.getItems().addAll(addToQueue, deleteTab);
		
		mediaBar = new HBox();
	}
	
	public BorderPane getContainer() {
		return container;
	}
	
	public void addMediaTab(MediaTab mediaTab) {
		Button tab = mediaTab.getModel();
		
		store.add(tab);
		mediaBox.refresh();
		
		tab.setOnAction(e -> {
			 MediaPlayer player = new MediaPlayer(mediaTab.getMedia());
			 player.setAutoPlay(true);
			 mediaView.setMediaPlayer(player);
		});
		
		tab.setOnContextMenuRequested(e -> {
			mediaTabMenu.show(tab, e.getScreenX(), e.getScreenY());
		});
	}
	
}
