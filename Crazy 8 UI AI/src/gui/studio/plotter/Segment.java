package gui.studio.plotter;

import javafx.scene.shape.Shape;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

//Resizable, scalable Line Segment
public class Segment extends Shape {
	
	private final Circle startPoint, endPoint;
	private final double startX, startY, endX, endY;
	
	public Segment(double startX, double startY, double endX, double endY) {
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		
		startPoint = new Circle(1.0, startX, startX);
		endPoint = new Circle(1.0, endY, endY);
		
		
	}
	
	public double getStartX() {
		return startX;
	}
	
	public double getStartY() {
		return startY;
	}
	
	public double getEndX() {
		return endX;
	}
	
	public double getEndY() {
		return endY;
	}
	
	public Line toLine() {
		return new Line(startX, startY, endX, endY);
	}
}
