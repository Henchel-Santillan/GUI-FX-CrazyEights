package test_main;

import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.scene.media.Media;
import javafx.scene.control.Button;

public class MediaTab {
	
	public static final double MAX_WIDTH = 250.0d;
	
	private final Button model;
	private final Media media;
	
	private StringProperty name;
	private StringProperty dateTime;
	
	public MediaTab(Media media /*, Image thumbnail*/) {
		model = new Button();
		//tabModel.setGraphic();	//TODO: capture video thumbnail + background recorder
		
		this.media = media;
		name = new SimpleStringProperty();
		dateTime = new SimpleStringProperty();
	}
	
	public Button getModel() {
		return model;
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
}
