package gui;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class NodeBounder {

	private final Node node;
	private final double margin; 
	
	public NodeBounder(Node node, double margin) {
		this.node = node;
		this.margin = margin;
	}
	
	public final boolean intersect(double side, double point) {
		return side + margin > point && side - margin < point;
	}
	
	public final double getNodeX() {
		return node.getBoundsInParent().getMinX();
	}
	
	public final double getNodeY() {
		return node.getBoundsInParent().getMinY();
	}
	
	public final double getNodeWidth() {
		return node.getBoundsInParent().getWidth();
	}
	
	public final double getNodeHeight() {
		return node.getBoundsInParent().getHeight();
	}
	
	public final double getParentX(double localX) {
		return this.getNodeX() + localX;
	}
	
	public final double getParentY(double localY) {
		return this.getNodeY() + localY;
	}
	
	public final boolean isInDrag(MouseEvent event) {
		double xPos = this.getNodeX() + event.getX();
		double yPos = this.getNodeY() + event.getY();
		
		return (xPos > this.getNodeX() + margin && xPos < this.getNodeX() + this.getNodeWidth() - margin) 
			&& (yPos > this.getNodeY() + margin && yPos < this.getNodeY() + this.getNodeHeight() - margin);
	}
	
	public final boolean leftResize(MouseEvent event) {
		return this.intersect(0, event.getX());
	}
	
	public final boolean rightResize(MouseEvent event) {
		return this.intersect(this.getNodeWidth(), event.getX());
	}
	
	public final boolean topResize(MouseEvent event) {
		return this.intersect(0,  event.getY());
	}
	
	public final boolean bottomResize(MouseEvent event) {
		return this.intersect(this.getNodeHeight(), event.getY());
	}
	
	public final boolean isInResize(MouseEvent event) {
		return this.leftResize(event)
			|| this.rightResize(event)
			|| this.topResize(event)
			|| this.bottomResize(event);
	}
}
