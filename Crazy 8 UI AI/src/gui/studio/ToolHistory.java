package gui.studio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

//Expires after a single session (e.g. when back is clicked)
//Represents a static change and update manager
public class ToolHistory {

	private final VBox model;
	private final ObservableList<ToolAction> history;
	private final ObservableList<String> strLog;
	private final ListView<String> log;
	
	private ToolAction selected;
	
	public ToolHistory() {
		history = FXCollections.observableArrayList();
		strLog = FXCollections.observableArrayList();
		log = new ListView<>(strLog);
		
		history.addListener((ListChangeListener<ToolAction>) c -> {
			strLog.add(c.getList().get(c.getList().size() - 1).toLoggableForm());
			log.refresh();
		});
		
		Label label = new Label();
        log.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        	label.setText(newValue);
        	selected = history.get(strLog.indexOf(newValue));
        });
		
		Button apply = new Button("Apply");
		apply.setOnAction( e-> {
			//TODO: Revert changes of selected item by editing canvas state (restore all prev ToolActions)
			
			e.consume();
		});
		
		HBox bottom = new HBox(label, apply);
		
		model = new VBox(log, bottom);
		model.setSpacing(5.0d);
		model.setAlignment(Pos.CENTER);
		VBox.setMargin(bottom, new Insets(5.0d));
		VBox.setVgrow(log, Priority.ALWAYS);
	}
	
	public VBox getModel() {
		return model;
	}
	
	public void addToolAction(ToolAction action) {
		history.add(action);
	}
}
