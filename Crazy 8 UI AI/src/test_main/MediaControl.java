package test_main;

import java.util.Optional;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

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
	private final ContextMenu queueMenuCtx;
	
	private Media playing;
	private Button selected;
	
	//TODO: Placeholder for playing if no Media found in library
	public MediaControl() {
		
		messenger = new Label();
		queueList = FXCollections.observableArrayList();

		queueBar = new ListView<>(queueList);	//TODO: Styling of the ListView
		
		
		queueClear = new Button("Clear");
		queueClear.setOnAction(e -> {
			//TODO: Confirmation Alert to remove all items in the queue
			e.consume();
		});
		
		queuePlay = new Button("||");
		queuePlay.setOnAction(e -> {
			//TODO: Set current to the current MediaTab control
			//Play current in the MediaView
			//By default, repeat is set to false when Queue is active
			//
			messenger.setText("Now Playing: " + selected.getText() + "\n" 
							  + "Up Next: " + queueList.get(queueList.indexOf(selected) + 1).getText());
			e.consume();
		});
		
		queueControls = new HBox();
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
			boolean deficit = b.getList().size()<= 1;
			
			moveUp.setDisable(deficit);
			moveDown.setDisable(deficit);
			moveTo.setDisable(deficit);
			
			Button added = b.getList().get(b.getList().size() - 1);
			
			added.setOnContextMenuRequested(e -> {
				queueMenuCtx.show(added, e.getScreenX(), e.getScreenY());
			});
		});
		
		mediaBar = new MediaBar(queueList);
		MediaPlayer player = new MediaPlayer(playing);
		player.setAutoPlay(true);
		
		mediaViewer = new MediaViewer(player);
		model = new BorderPane();
		
		model.setLeft(queueBar);
		model.setRight(mediaBar.getModel());
		model.setCenter(mediaViewer.getModel());
	}
	
	public BorderPane getModel() {
		return model;
	}
	
	//TODO: Calls addMediaTab in MediaBar
	public void addMedia(MediaTab mediaTab) {
		Button mediaTabModel = mediaTab.getModel();
		
		mediaTabModel.setOnAction( e-> {
			playing = mediaTab.getMedia();
		});
		
		mediaBar.addMediaTab(mediaTab);
	}
	
	//TODO: Set playing Media
}
