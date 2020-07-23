package gui.media;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.control.Button;

public class MediaTab {
	
	public static final double MAX_WIDTH = 250.0d;
	
	private final Button model;
	private final Media media;
	
	private StringProperty name;
	private StringProperty dateTime;
	
	public MediaTab(String name, Media media, Image thumbnail) {
		model = new Button();
		model.setGraphic(new ImageView(thumbnail));	//TODO: capture video thumbnail + background recorder
		
		this.media = media;
		this.name = new SimpleStringProperty(name);
		dateTime = new SimpleStringProperty();
	}
	
	public Button getModel() {
		return model;
	}
	
	public boolean hasModel(Button button) {
		if (button.equals(model)) return true;
		return false;
	}
	
	public Media getMedia() {
		return media;
	}
	
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getName() {
		return name.get();
	}
	
	public void setName(String name) {
		this.name.set(name);
		model.setText(name);
	}
	
	public StringProperty dateTimeProperty() {
		return dateTime;
	}
	
	public String getDateTime() {
		return dateTime.get();
	}
	
	//zoneid = "" if no result is present from Dialog
	public void setDateTime(boolean isZoned, String zoneid) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH/mm");
		
		if (isZoned) {
			ZonedDateTime zone = ZonedDateTime.now(ZoneId.of(zoneid));
			dateTime.set(formatter.format(zone) + ", ZONED");
		} else {
			LocalDateTime local = LocalDateTime.now();
			dateTime.set(formatter.format(local) + ", LOCAL");
		}
	}
	
	public String getMetaData() {
		return this.getName() + "\n" + this.getDateTime();
	}
}