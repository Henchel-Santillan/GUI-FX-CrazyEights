package gui.studio.plotter;

import javafx.scene.Cursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ListChangeListener;

import javafx.geometry.Point2D;

//TODO: How to indicate finish operation?
//TODO: Get an unfinished line to trail points?
//TODO: Plot at end operation or plot after each point is laid?
public class MultiSegmentPlotter {

	private final Canvas owner;
	private final ObservableList<Point2D> order;
	private final BooleanProperty active;
	private final ReadOnlyIntegerWrapper counter;
	
	public MultiSegmentPlotter(Canvas owner) {
		this.owner = owner;
		owner.setCursor(Cursor.HAND);
		
		order = FXCollections.observableArrayList();
		active = new SimpleBooleanProperty(false);
		
		counter = new ReadOnlyIntegerWrapper(0);
		
		GraphicsContext gc = owner.getGraphicsContext2D();
		
		active.addListener((observable, oldValue, newValue) -> {
			if (newValue) {
				owner.setOnMousePressed(e -> {
					if (e.getButton() == MouseButton.PRIMARY) {
						order.add(new Point2D(e.getX(), e.getY()));
					} else {
						gc.closePath();
						owner.setCursor(Cursor.DEFAULT);
						active.set(false);
					}
					e.consume();
				});
			} 
		});
		
		//This is how to implement plot while in process
		//listening for size > 1 ensures counter pointer = size - 2
		order.addListener((ListChangeListener<Point2D>) c -> {
			if (c.getList().size() > 1) {
				gc.beginPath();
				gc.moveTo(order.get(counter.get()).getX(), order.get(counter.get()).getY());
				gc.lineTo(order.get(counter.get() + 1).getX(), order.get(counter.get() + 1).getY());
			}
		});
	}
	
	public IntegerProperty counterProperty() {
		return counter;
	}
	
	public int getCounter() {
		return counter.get();
	}
	
	public BooleanProperty activeProperty() {
		return active;
	}
	
	public boolean isActive() {
		return active.get();
	}
	
	public void setActive(boolean active) {
		this.active.set(active);
	}
	
	//TODO: Add boolean toggleswitch for guide plot = ON or OFF?
	public void lineplot() {
		GraphicsContext gc = owner.getGraphicsContext2D();
		gc.beginPath();
		gc.moveTo(order.get(0).getX(), order.get(0).getY());
	
		int counter = 0;
		while (order.get(counter) != null) {
			gc.lineTo(order.get(counter).getX(), order.get(counter).getY());
			counter++;
		}
		
		gc.closePath();
		order.clear();
	}
	
	/*public void quadraticplot() {
		
	}
	
	public void bezierplot() {
		
	}*/
}
