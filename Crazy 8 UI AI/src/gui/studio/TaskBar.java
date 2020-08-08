package gui.studio;

import gui.util.FamilyBox;
import gui.util.WeightBox;

import javafx.scene.canvas.Canvas;

import java.net.URISyntaxException;
import java.util.logging.Logger;
import java.util.logging.Level;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import javafx.scene.control.ToggleButton;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ColorPicker;

public class TaskBar {

	private final Canvas owner;
	private final HBox model;
	
	private final ToggleGroup filter, general, text, shape;
	private final ToggleButton select, sketch, textbox;
	private final ToggleButton fill, borderFill, borderStyle;
	private final WeightBox borderWeight, textWeight;
	
	private final CheckBox bold, italicize, underline;
	private final ColorPicker colorPicker;
	private final FamilyBox fontFamilies;
	
	public TaskBar(Canvas owner) {
		this.owner = owner;
		
		filter = new ToggleGroup();
		select = new ToggleButton();
		sketch = new ToggleButton();
		textbox = new ToggleButton();
		
		filter.getToggles().addAll(select, sketch, textbox);
		
		general = new ToggleGroup();
		text = new ToggleGroup();
		shape = new ToggleGroup();
		
		model = new HBox();
	}
	
	public HBox getModel() {
		return model;
	}
}
