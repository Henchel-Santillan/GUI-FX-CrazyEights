package gui.util;

import javafx.scene.Cursor;
import javafx.scene.Node;

import javafx.scene.input.MouseEvent;

public class DragResizer {
	
	public interface DragResizeListener {
		/*void onDrag(Node node, double x, double y, double width, double height);
		void onResize(Node node, double x, double y, double width, double height);*/
		void resize(Node node, double x, double y, double width, double height);
	}
	
	public enum State {
		DEFAULT (Cursor.DEFAULT), 
		DRAG (Cursor.CLOSED_HAND),
		
		N_RESIZE (Cursor.N_RESIZE), 
		S_RESIZE (Cursor.S_RESIZE), 
		W_RESIZE (Cursor.W_RESIZE), 
		E_RESIZE (Cursor.E_RESIZE), 
		
		NW_RESIZE (Cursor.NW_RESIZE), 
		NE_RESIZE (Cursor.NE_RESIZE), 
		SW_RESIZE (Cursor.SW_RESIZE), 
		SE_RESIZE (Cursor.SE_RESIZE);
		
		private final Cursor cursor;
		
		private State(Cursor cursor) {
			this.cursor = cursor;
		}
		
		public final Cursor getStateCursor() {
			return cursor;
		}
	}
	
	//TODO: Margin generic values
	public static final double MARGIN = 0.0;
	//TODO: Set minimum accetable widths and heights for nodes to be resized
	public static final double MIN_HEIGHT = 0.0;
	public static final double MIN_WIDTH = 0.0;
	
	private final NodeBounder bounder;
	
	private Node node;
	private DragResizeListener listener;
	private State state;
	
	private DragResizer(Node node, DragResizeListener listener) {
		this.node = node;
		
		if (listener != null) {
			this.listener = listener;
		} else {
			listener = new DragResizeListener() {
				@Override
				public void resize(Node node, double x, double y, double width, double height) {
					//TODO: input default implementation
				}
			};
		}
		
		state = State.DEFAULT;
		bounder = new NodeBounder(node, MARGIN);
	}
	
	public State getState(MouseEvent event) {
		boolean left = bounder.leftResize(event);
		boolean right = bounder.rightResize(event);
		boolean top = bounder.topResize(event);
		boolean bottom = bounder.bottomResize(event);
		
		if (left) 
			return State.W_RESIZE;
		else if (right) 
			return State.E_RESIZE;
		else if (bottom) 
			return State.S_RESIZE;
		else if (top) 
			return State.N_RESIZE;
		else if (left && top) 
			return State.NW_RESIZE;
		else if (left && bottom) 
			return State.SW_RESIZE;
		else if (right && top) 
			return State.NE_RESIZE;
		else if (right && bottom) 
			return State.SW_RESIZE;
		else if (bounder.isInDrag(event)) 
			return State.DRAG;
		else 
			return State.DEFAULT;
	}
	
	public static void setResizable(Node node, DragResizeListener listener) {
		DragResizer resizer = new DragResizer(node, listener);
		
		node.setOnMouseMoved(e -> {
			resizer.onMouseMoved(e);
			e.consume();
		});
		
		node.setOnMouseReleased(e -> {
			resizer.onMouseReleased(e);
			e.consume();
		});
		
		node.setOnMouseDragged(e -> {
			resizer.onMouseDragged(e);
			e.consume();
		});
		
		node.setOnMousePressed( e-> {
			resizer.onMousePressed(e);
			e.consume();
		});
	}
	
	//TODO: Make fields for nodeX, nodeY, nodeWidth, nodeHeight, etc. etc.
	public void resetCoordinates(MouseEvent even) {
		
	}
	
	private void onMouseMoved(MouseEvent event) {
		state = this.getState(event);
		node.setCursor(state.getStateCursor());
	}
	
	private void onMouseReleased(MouseEvent event) {
		state = State.DEFAULT;
		node.setCursor(Cursor.DEFAULT);
	}
	
	private void onMouseDragged(MouseEvent event) {
		
		//If provide default implementation, then null check is not necessary
		if (listener != null) {
			
		}
	}
	
	private void onMousePressed(MouseEvent event) {
		
	}
}
