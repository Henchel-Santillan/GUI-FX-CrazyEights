package gui.studio;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import gui.util.WeightBox;

import javafx.scene.ImageCursor;
import javafx.scene.layout.HBox;

import javafx.scene.canvas.Canvas;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.control.ToggleGroup;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

//TODO: Find all image resources for icons and name appropriately
public class TaskBar {

	private final HBox model;
	private final WeightBox fontBox;
	private final ToggleGroup filter, general, text, shape;
	
	private final Canvas owner;
	
	public TaskBar(Canvas owner) {
		this.owner = owner;
		
		fontBox = new WeightBox(100);
		
		filter = new ToggleGroup();
		RadioButton select = new RadioButton();
		RadioButton sketch = new RadioButton();
		RadioButton textbox = new RadioButton();
		filter.getToggles().addAll(select, sketch, textbox);
		
		try {
			//TODO: Put in an icon iterable?
			ImageView selectIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/selectIcon.png").toURI().toString()));
			
			ImageView sketchIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/sketchIcon.png").toURI().toString()));
			
			ImageView textboxIcon = new ImageView(new Image(getClass().getResource(
					"/IMAGE/textboxIcon.png").toURI().toString()));
			
			ImageView[] icons = new ImageView[]{selectIcon, sketchIcon, textboxIcon};
			
			for (int i = 0; i < icons.length; i++) {
				icons[i].setFitWidth(15.0);
				icons[i].setFitHeight(15.0);
				icons[i].setSmooth(true);
				
				((RadioButton) filter.getToggles().get(i)).setGraphic(icons[i]);
			}
			
		} catch (URISyntaxException e) {
			Logger.getLogger(TaskBar.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(TaskBar.class.getName()).log(Level.SEVERE, null, e);
		}
		
		
		select.setOnAction(e -> {
			//TODO: ImageCursor class for custom cursors (i.e. for each Tool)
			//owner.setCursor();
			e.consume();
		});
		
		
		sketch.setOnAction(e -> {
			e.consume();
		});
		
		
		textbox.setOnAction(e -> {
			e.consume();
		});
		
		
		general = new ToggleGroup();
		text = new ToggleGroup();
		
		shape = new ToggleGroup();
		
		
		
		model = new HBox();
	}
	
	public HBox getModel() {
		return model;
	}
}
