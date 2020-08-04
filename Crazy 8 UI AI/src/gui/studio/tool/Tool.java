package gui.studio.tool;

import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.ImageCursor;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.control.RadioButton;
import javafx.scene.control.Tooltip;

public abstract class Tool {

	protected final ReadOnlyStringWrapper name;
	protected final StringProperty label;
	
	protected final RadioButton model;
	private final ImageView icon;
	
	public Tool(String name) {
		this.name = new ReadOnlyStringWrapper(name);
		this.label = new SimpleStringProperty();
		
		icon = null;
		model = new RadioButton();
		model.setGraphic(icon);
	}
	
	public Tool(String name, String label) {
		this(name);
		this.label.set(label);
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public StringProperty labelProperty() {
		return label;
	}
	
	public String getLabel() {
		return label.get();
	}
	
	public void setLabel(String label) {
		this.label.set(label);
		model.setTooltip(new Tooltip(label));
	}
	
	public void setIcon(String resource, double width, double height, boolean preserveRatio) {
		try {
			icon.setImage(new Image(getClass().getResource(resource).toURI().toString()));
			icon.setFitWidth(width);
			icon.setFitHeight(height);
			icon.setPreserveRatio(preserveRatio);
			icon.setSmooth(true);
			
		} catch (URISyntaxException e) {
			Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, e);
			
		} catch (Exception e) {
			Logger.getLogger(Tool.class.getName()).log(Level.SEVERE, null, e);
		}
	}
}
