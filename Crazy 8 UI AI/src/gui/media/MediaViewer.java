package gui.media;

import javafx.util.Duration;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import javafx.geometry.Pos;
import javafx.geometry.Insets;

import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;

import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;

public class MediaViewer {

	private BooleanProperty repeatRequested, stopRequested, endRequested;  
	//TODO: ToggleSwitch control for repeatRequested
	
	private final VBox model;
	private final MediaView viewer;
	private final MediaPlayer player;	//TODO: MediaPlayer must be specified in MediaControl
	private final HBox controlBar;
	private final Button play; 
	private final Slider timeSlider, volumeSlider; 
	private final Label timeLabel, volumeLabel, durationLabel;
	
	private Duration duration;
	
	public MediaViewer(final MediaPlayer player) {
		this.player = player;
		
		repeatRequested = new SimpleBooleanProperty(false);
		stopRequested = new SimpleBooleanProperty(false);
		endRequested = new SimpleBooleanProperty(false);
		
		viewer = new MediaView();
		
		play = new Button(">");
		play.setOnAction(e -> {
			Status status = player.getStatus();
			
			if (status == Status.UNKNOWN || status == Status.HALTED) {
				return;
			}
			
			if (status == Status.READY || status == Status.PAUSED || status == Status.STOPPED) {
				if (endRequested.get()) {
					player.seek(player.getStartTime());
					endRequested.set(false);
				}
				player.play();
			} else {
				player.pause();
			}
		});
		
		player.currentTimeProperty().addListener(listener -> {
			updateViewer();
		});
		
		player.setOnPlaying(new Runnable() {
			@Override
			public void run() {
				if (stopRequested.get()) {
					player.pause();
					stopRequested.set(false);
				} else {
					play.setText("||");
				}
			}
		});
		
		player.setOnPaused(new Runnable() {
			@Override
			public void run() {
				play.setText(">");
			}
		});
		
		player.setOnReady(new Runnable() {
			@Override
			public void run() {
				duration = player.getMedia().getDuration();
				updateViewer();
			}
		});
		
		player.setCycleCount(repeatRequested.get() ? MediaPlayer.INDEFINITE: 1);
		player.setOnEndOfMedia(new Runnable() {
			@Override
			public void run() {
				if (!repeatRequested.get()) {
					play.setText(">");
					stopRequested.set(true);
					endRequested.set(true);
				}
			}
		});
		
		timeLabel = new Label("Time: ");
		
		timeSlider = new Slider();
		HBox.setHgrow(timeSlider, Priority.ALWAYS);
		timeSlider.setMinWidth(50.0);
		timeSlider.setMaxWidth(Double.MAX_VALUE);
		timeSlider.valueProperty().addListener(listener -> {
			if (timeSlider.isValueChanging()) {
				player.seek(duration.multiply(timeSlider.getValue() / 100.0));
			}
		});
		
		durationLabel = new Label();
		
		volumeLabel = new Label("Volume: ");
		
		volumeSlider = new Slider();
		volumeSlider.setMinWidth(30.0);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.valueProperty().addListener(listener -> {
			if (volumeSlider.isValueChanging()) {
				player.setVolume(volumeSlider.getValue() / 100.0);
			}
		});
		
		controlBar = new HBox();
		controlBar.setAlignment(Pos.CENTER);
		controlBar.setPadding(new Insets(5, 10, 5, 10));
		
		controlBar.getChildren().addAll(play, timeLabel, timeSlider, durationLabel, volumeLabel, volumeSlider);
		
		model = new VBox();
		model.getChildren().addAll(viewer, controlBar);
	}
	
	public VBox getModel() {
		return model;
	}
	
	public BooleanProperty repeatRequestedProperty() {
		return repeatRequested;
	}
	
	public boolean repeatRequested() {
		return repeatRequested.get();
	}
	
	public void requestRepeat(boolean request) {
		repeatRequested.set(request);
	}
	
	//TODO: builtin time formatter
	private void updateViewer() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				Duration current = player.getCurrentTime();
				durationLabel.setText("");
				timeSlider.setDisable(duration.isUnknown());
				
				if (!timeSlider.isDisabled() && duration.greaterThan(Duration.ZERO) && !timeSlider.isValueChanging()) {
					timeSlider.setValue(current.divide(duration.toSeconds()).toMillis() * 100.0);
				}
				
				if (!volumeSlider.isValueChanging()) {
					volumeSlider.setValue(((int) Math.round(player.getVolume() * 100)));
				}
			}
		});
	}
	
	public MediaPlayer getMediaPlayer() {
		return player;
	}
	
	//Called when a media resource from the MediaBar is clicked; called in MediaControl in setOnAction for added Tabs
	//Media media may be changed to MediaPlayer if local player cannot be used
	/*public void autoplay(Media media) {
		MediaPlayer player = new MediaPlayer(media);
		player.setAutoPlay(true);
		viewer.setMediaPlayer(player);
	}*/	
}
