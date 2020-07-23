package gui.studio;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.control.Button;

public abstract class Tool {

	protected final ReadOnlyStringWrapper name;
	protected final StringProperty label;
	
	protected final Button model;
	
	public Tool(String name) {
		this.name = new ReadOnlyStringWrapper(name);
		this.label = new SimpleStringProperty();
		
		//TODO: setGraphic()
		model = new Button();
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
	}
}
