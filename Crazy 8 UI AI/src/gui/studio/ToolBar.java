package gui.studio;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.scene.layout.VBox;
import javafx.scene.layout.FlowPane;

import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Button;

public class ToolBar {
	
	private final ObservableList<Tool> tools;
	
	private final VBox model;
	
	public ToolBar() {
		tools = FXCollections.observableArrayList();
		model = new VBox();
	}
	
	public VBox getModel() {
		return model;
	}
}
