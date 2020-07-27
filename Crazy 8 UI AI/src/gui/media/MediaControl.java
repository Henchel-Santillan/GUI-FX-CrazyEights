package gui.media;

import gui.dialog.BrowseDialog;
import gui.dialog.BrowseDialog.BrowseType;

import javafx.stage.Stage;
import javafx.stage.FileChooser;

import java.net.URISyntaxException;

import java.util.Optional;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.stream.IntStream;

import java.io.File;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import javafx.geometry.Pos;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Tooltip;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

public class MediaControl {

	private final BorderPane model;
	private final MediaBar mediaBar;
	private final MediaViewer mediaViewer;

	//QUEUE CONTROLS
	private final ObservableList<Button> queueList;
	private final ListView<Button> queueBar;
	private final HBox queueControls;
	private final VBox queueBox;
	private final Label messenger;
	private final Button queueClear, queuePlay;
	private final HBox topBar;
	private final ContextMenu queueMenuCtx;
	
	private Media playing;	//TODO: playing = null. Should have a PlaceHolder Video that cannot be deleted.
	private Button selected;
	
	private StringProperty destination;
	
	//TODO: Placeholder for playing if no Media found in library
	//TODO: Append error message to a location (probably model bottom) if MediaException
	public MediaControl(Stage parent) {
		
		destination = new SimpleStringProperty();
		messenger = new Label();
		queueList = FXCollections.observableArrayList();

		queueBar = new ListView<>(queueList);	//TODO: Styling of the ListView
		
		queueClear = new Button("Clear");
		queueClear.setOnAction(e -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Clear Queue");
			alert.setContentText("Are you sure you want to clear the queue?");
			
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				queueList.clear();
				queueBar.refresh();
			} else {
				alert.close();
			}
			
			e.consume();
		});
		
		queuePlay = new Button("||");
		queuePlay.setTooltip(new Tooltip("Play Queue"));
		queuePlay.setOnAction(e -> {
			//TODO: Set current to the current MediaTab control
			//Play current in the MediaView
			//By default, repeat is set to false when Queue is active
			//
			queuePlay.setText(">");
			messenger.setText("Now Playing: " + selected.getText() + "\n" 
							  + "Up Next: " + queueList.get(queueList.indexOf(selected) + 1).getText());
			
			e.consume();
		});
		
		queueControls = new HBox();
		queueControls.setAlignment(Pos.CENTER);
		queueControls.getChildren().addAll(queuePlay, queueClear);
		
		queueBox = new VBox();
		queueBox.getChildren().addAll(messenger, queueBar, queueControls);
		
		queueMenuCtx = new ContextMenu();
		queueMenuCtx.setAutoHide(true);
		
		//TODO: for all menu item action handlers, change messenger statement to reflect real count and not index
		MenuItem moveUp = new MenuItem("Move Up");
		moveUp.setOnAction(e -> {
			//TODO: Switches top and current: will consider using a Queue in future implementations
			Button target = queueBar.getSelectionModel().getSelectedItem();
			
			if (queueList.size() > 0 && target != null && queueList.indexOf(target) > 0) {
				int index = queueList.indexOf(target);
				
				Button temp = queueList.get(index - 1);
				queueList.set(index - 1, target);
				queueList.set(index, temp);
				queueBar.refresh();
		
				messenger.setText("Queue item at " + index + " now at " + (index - 1));
			}
		});
		
		MenuItem moveDown = new MenuItem("Move Down");
		moveDown.setOnAction(e -> {
			Button target = queueBar.getSelectionModel().getSelectedItem();
			
			if (queueList.size() > 0 && target != null && queueList.indexOf(target) < queueList.size()) {
				int index = queueList.indexOf(target);
				
				Button temp = queueList.get(index + 1);
				queueList.set(index + 1, target);
				queueList.set(index, temp);
				
				queueBar.refresh();
				
				messenger.setText("Queue item at " + index + " now at " + (index + 1));
			}
		});
		
		MenuItem moveTo = new MenuItem("Move To");
		moveTo.setOnAction(e -> {
			
			Button target = queueBar.getSelectionModel().getSelectedItem();
			
			if (queueList.size() > 1 && target != null) {
				ChoiceDialog<Integer> dialog = new ChoiceDialog<>();
				dialog.setTitle("Queue: Move To");
				dialog.setContentText("Move to position: ");
				
				queueList.addListener((ListChangeListener<Button>) b -> {
					IntStream.range(1, b.getList().size()).forEach(n -> {
						dialog.getItems().add(n);
					});
				});
				dialog.setSelectedItem(dialog.getItems().get(0));
				
				Optional<Integer> result = dialog.showAndWait();
				if (result.isPresent()) {
					int index = queueList.indexOf(target);
					
					queueList.remove(index);
					queueList.add(result.get() - 1, target);
					queueBar.refresh();
					
					messenger.setText("Queue item at " + index + " now at " + (result.get() - 1));
					
				} else {
					dialog.close();
					messenger.setText("Queue Move To cancelled.");
				}
			}
		});
		
		MenuItem remove = new MenuItem("Remove");
		remove.setOnAction(e -> {
			Button removed = queueBar.getSelectionModel().getSelectedItem();
			
			if (queueList.size() > 0 && removed != null) {
				queueList.remove(removed);
				queueBar.refresh();
				
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setContentText("Queue item successfully removed.");
				alert.showAndWait();
			}
		});
		
		queueMenuCtx.getItems().addAll(moveUp, moveDown, moveTo, remove);
		queueList.addListener((ListChangeListener<Button>) b -> {
			boolean none = b.getList().size() == 0;
			boolean deficit = b.getList().size() <= 1;
			
			queueClear.setDisable(none);
			queuePlay.setDisable(none);
			
			moveUp.setDisable(deficit);
			moveDown.setDisable(deficit);
			moveTo.setDisable(deficit);
			
			Button added = b.getList().get(b.getList().size() - 1);
			
			added.setOnContextMenuRequested(e -> {
				queueMenuCtx.show(added, e.getScreenX(), e.getScreenY());
			});
		});
		
		
		Button back = new Button();
		
		try {
			ImageView graphic = new ImageView(new Image(getClass().getResource(
					"/IMAGE/back_arrow.png").toURI().toString()));
			graphic.setSmooth(true);
			graphic.setPreserveRatio(true);
			graphic.setFitWidth(20.0d);
			graphic.setFitHeight(15.0d);
			
			back.setGraphic(graphic);
			
		} catch (URISyntaxException e) {
			Logger.getLogger(MediaControl.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(MediaControl.class.getName()).log(Level.SEVERE, null, e);
			
		}
		
		back.setTooltip(new Tooltip("Back"));
		
		MenuBar navbar = new MenuBar();
		Menu file = new Menu("File");
		
		MenuItem changeDestination = new MenuItem("Edit Destination...");
		changeDestination.setOnAction(e -> {
			/*BrowseDialog browser = new BrowseDialog(parent, BrowseType.FOLDER );
			browser.show();
			destination.set(browser.getResult().get());
					
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Destination Change");
			alert.setContentText("Local folder destination changed successfully.");*/
			
			e.consume();
		});
		
		//TODO: Selection mechanism for determining which media to export
		MenuItem exportMediaAs = new MenuItem("Export Media As...");
		
		exportMediaAs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				FileChooser saver = new FileChooser();
				saver.setTitle("Export Replay Media");
				
				File file = saver.showSaveDialog(parent);
				
				if (file != null) {
					//TODO: Determine next steps
				}
				
			
				e.consume();
			}
		});
		
		file.getItems().addAll(changeDestination, exportMediaAs);
		navbar.getMenus().add(file);
		
		topBar = new HBox(back, navbar);
		topBar.setAlignment(Pos.CENTER_LEFT);
		
		mediaBar = new MediaBar(queueList);
		
		//TODO: Playback  not visible
		try {
			playing = new Media(getClass().getResource(
					"/VIDEO/media_preview.mp4").toURI().toString());
			
		} catch (URISyntaxException e) {
			Logger.getLogger(MediaControl.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(MediaControl.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		MediaPlayer player = new MediaPlayer(playing);
		player.setAutoPlay(true);
		
		mediaViewer = new MediaViewer(player);
		mediaViewer.setPrefSize(480, 270);
		
		model = new BorderPane();
		
		model.setTop(topBar);
		model.setLeft(queueBar);
		model.setRight(mediaBar.getModel());
		model.setCenter(mediaViewer.getModel());
	}
	
	public BorderPane getModel() {
		return model;
	}
	
	public void addMedia(MediaTab mediaTab) {
		Button mediaTabModel = mediaTab.getModel();
		
		mediaTabModel.setOnAction( e-> {
			playing = mediaTab.getMedia();
		});
		
		mediaBar.addMediaTab(mediaTab);
	}
	
	//TODO: Set playing Media
}
