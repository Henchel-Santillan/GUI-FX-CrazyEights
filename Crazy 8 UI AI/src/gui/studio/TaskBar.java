package gui.studio;

import gui.util.WeightBox;

import javafx.scene.layout.HBox;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


public class TaskBar {

	private final HBox model;
	private final WeightBox fontBox;
	
	
	public TaskBar() {
		model = new HBox();
	}
	
	public HBox getModel() {
		return model;
	}
}
