package gui;

import javafx.scene.Cursor;
import javafx.scene.Node;

import javafx.scene.shape.Shape;
import javafx.scene.input.MouseEvent;

//TODO: ALternative implementation, where a separate DragResizerHelper class is declared
public class DragResizerOld {
	
	public interface DragResizeListener {
		void onDrag(Node node, double x, double y, double width, double height);
		void onResize(Node node, double x, double y, double width, double height);
	}
	
	//This looks like cancer.
	public static enum State {
		DEFAULT (Cursor.DEFAULT),
		DRAG (Cursor.CLOSED_HAND),
		N (Cursor.N_RESIZE),
		S (Cursor.S_RESIZE),
		E (Cursor.E_RESIZE),
		W (Cursor.W_RESIZE),
		NE (Cursor.NE_RESIZE),
		NW (Cursor.NW_RESIZE),
		SE (Cursor.SE_RESIZE),
		SW (Cursor.SW_RESIZE);
		
		private final Cursor cursor;
		
		private State(Cursor cursor) {
			this.cursor = cursor;
		}
		
		public final Cursor getStateCursor() {
			return cursor;
		}
	}
	
	//TODO: Determine better numbers
	private static final double MARGIN = 0.0;
	private static final double MIN_H = 0.0;
	private static final double MIN_W = 0.0;
	
	private Node node;
	private DragResizeListener listener;
	private State state;
	
	private DragResizerOld(Node node, DragResizeListener listener) {
		this.node = node;
		
		if (listener != null) {
			this.listener = listener;
		} else {
			//This is a default listener for the class if none is specified, may not be operable
			//May remove the default listener if there is nog generic way to accomplish node bounds get/set
			this.listener = new DragResizeListener() {
				@Override
				public void onDrag(Node node, double x, double y, double w, double h) {
					//TODO: Generic node bounds --> must first place in a container (e.g. a Pane)
					//Will put in Pane that wait for garbage collection
					//Out of bounds check to disallow reszizing beyonfd parent container, etc.
					setNodeSize(node, x, y, w, h);
				}
				
				@Override
				public void onResize(Node node, double x, double y, double w, double h) {
					//TODO: Same here, instead of getting, will be used for setting
					setNodeSize(node, x, y, w, h);
				}
			};
		}
		
		state = State.DEFAULT;
	}
	
	public static void resize(Node node) {
		DragResizerOld.resize(node, null);
	}
	
	public static void resize(Node node, DragResizeListener listener) {
		final DragResizerOld resizer = new DragResizerOld(node, listener);
		
		//Pressed, dragged, moved, released
		node.setOnMousePressed(e -> {
			e.consume();
		});
		
		node.setOnMouseDragged(e -> {
			e.consume();
		});
		
		node.setOnMouseMoved(e -> {
			resizer.onMouseMoved(e);
			e.consume();
		});
		
		node.setOnMouseReleased(e -> {
			resizer.onMouseReleased();
			e.consume();
		});
	}
	
	private void onMousePressed() {
		
	}
	
	private void onMouseDragged() {
		
	}
	
	private void onMouseMoved(MouseEvent event) {
		state = this.getState(event);
		node.setCursor(state.getStateCursor());
	}
	
	private void onMouseReleased() {
		node.setCursor(Cursor.DEFAULT);
		state = State.DEFAULT;
	}
	
	
	//Ugliest method
	public final State getState(MouseEvent event) {
		boolean left = intersect(0, event.getX());
		boolean right = intersect(node.getBoundsInParent().getWidth(), event.getX());
		boolean top = intersect(0, event.getY());
		boolean bottom = intersect(node.getBoundsInParent().getHeight(), event.getY());
		
		if (left) 
			return State.W;
		else if (right) 
			return State.E;
		else if (bottom) 
			return State.S;
		else if (top) 
			return State.N;
		else if (left && top) 
			return State.NW;
		else if (left && bottom) 
			return State.SW;
		else if (right && top) 
			return State.NE;
		else if (right && bottom) 
			return State.SW;
		else if (isInDrag(event)) 
			return State.DRAG;
		else 
			return State.DEFAULT;
	}
	
	//Should not handle for user. Client classes/programs should create own implementation
	private void setNodeSize(Node node, double x, double y, double width, double height) {
		node.setLayoutX(x);
		node.setLayoutY(y);
		
		if (node instanceof Shape) {
			
		}
	}
	
	private boolean intersect(double side, double point) {
		return side + MARGIN > point && side - MARGIN < point;
	}
	
	private boolean isInDrag(MouseEvent event) {
		double boundsX = node.getBoundsInParent().getMinX();
		double boundsY = node.getBoundsInParent().getMinY();
		
		double xPos = boundsX + event.getX();
		double yPos = boundsY + event.getY();
		
		double nodeXi = boundsX + MARGIN;
		double nodeYi = boundsY + MARGIN;
		
		double nodeXf = boundsX + node.getBoundsInParent().getWidth() - MARGIN;
		double nodeYf = boundsY + node.getBoundsInParent().getHeight() - MARGIN;
		
		return (xPos > nodeXi && xPos < nodeXf) && (yPos > nodeYi && yPos < nodeYf);
	}
	
	private boolean isInResize(MouseEvent event) {
		return intersect(0, event.getX()) ||
			   intersect(node.getBoundsInParent().getWidth(), event.getX()) ||
			   intersect(0, event.getY()) ||
			   intersect(node.getBoundsInParent().getHeight(), event.getY());
	}
	
	
}
