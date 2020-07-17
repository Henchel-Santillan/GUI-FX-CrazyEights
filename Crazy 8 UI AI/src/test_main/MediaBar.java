package test_main;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import javafx.geometry.Pos;

import javafx.scene.Node;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.ChoiceBox;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class MediaBar {
	
	//TODO: The control adding to MediaBar should always compare against capacity. addMediaTab() is blind to this.
	public static final int CAPACITY = 10;
	
	private final VBox model;
	private final ListView<Button> bar;
	
	private final ObservableList<Button> store;
	private final FilteredList<Button> storeFiltered;
	private final SortedList<Button> storeSorted;
	
	private final Label messenger;
	private final TextField search;
	private final ChoiceBox<String> sorter;
	private final ContextMenu barMenuCtx;
	
	//NOTE: queueList must be the ObservableList backing the Queue. Will be combined with API Queue
	public MediaBar(ObservableList<Button> queueList) {
		store = FXCollections.observableArrayList();
		storeFiltered = new FilteredList<>(store, p -> true);
		storeSorted = new SortedList<>(storeFiltered);
		
		bar = new ListView<>(storeSorted);
		
		messenger = new Label();
		
		search = new TextField("Search Media Bar by recording name ...");
		search.textProperty().addListener((observable, oldValue, newValue) -> {
			storeFiltered.setPredicate(item -> {
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				
				String lowerCaseFilter = newValue.toLowerCase().trim();
				
				if (item.getText().toLowerCase().contains(lowerCaseFilter)) {
					return true;
				}
			
				return false;
			});
		});
		
		sorter = new ChoiceBox<>();
		sorter.getItems().addAll("Restore All", "Name", "Date Added");
		sorter.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			switch ((Integer) newValue) {
				case 0:
					bar.setItems(storeSorted);
					//TODO: Reset the choicebox
					break;
				case 1:
					//TODO: Sort the selection by Name (Alpha-Order)
					break;
				case 2:
					//TODO: Sort the selection by Date Added (most recent first)
					break;
			}
		});
		
		HBox storeTop = new HBox();
		storeTop.setSpacing(5.0d);
		storeTop.getChildren().addAll(search, sorter);
		
		model = new VBox();
		model.getChildren().addAll(messenger, storeTop, bar);
		model.setSpacing(5.0d);
		model.setAlignment(Pos.CENTER);
		
		for (Node node : model.getChildren()) {
			VBox.setVgrow(node, Priority.NEVER);
		}
		
		barMenuCtx = new ContextMenu();
		MenuItem addToQueue = new MenuItem("Add To Queue");
		addToQueue.setOnAction( e-> {
			//TODO: Add to Queue
			queueList.add(bar.getSelectionModel().getSelectedItem());
			e.consume();
		});
		
		MenuItem deleteTab = new MenuItem("Delete");
		deleteTab.setOnAction(e -> {
			
			Button removed = bar.getSelectionModel().getSelectedItem();
			
			if (store.size() > 0 && removed != null) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Delete Media Tab");
				alert.setContentText("Deleting this Media Tab will remove it from the game ligrary."
						+ "You will still have your local copy. Click 'OK' to confirm and 'Cancel' to go back.");
				
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					messenger.setText("Media Tab at " + store.indexOf(removed) + " deleted" );
					store.remove(removed);
					bar.refresh();
				} else {
					alert.close();
				}
			} else {
				messenger.setText("No item selected");
			}
			e.consume();
		});
		
		barMenuCtx.getItems().addAll(addToQueue, deleteTab);
	}
	
	public VBox getModel() {
		return model;
	}
	
	public void addMediaTab(MediaTab mediaTab) {		
		Button mediaTabModel = mediaTab.getModel();
		
		store.add(mediaTabModel);
		bar.refresh();
		
		//TODO: In MediaControl, implement more global method for adding which sets tab model on action
		mediaTabModel.setOnContextMenuRequested(e -> {
			barMenuCtx.show(mediaTabModel, e.getScreenX(), e.getScreenY());
			e.consume();
		});
	}
}
