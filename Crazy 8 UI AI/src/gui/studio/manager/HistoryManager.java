package gui.studio.manager;

import gui.studio.tool.Tool;

import javafx.scene.image.WritableImage;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;

public class HistoryManager {
	
	public static final int CAP = 20;
	
	private final ActionQueue actionQueue;
	private final Canvas owner;
	private Action selected;
	
	public HistoryManager(Canvas owner) {
		this.owner = owner;
		actionQueue = new ActionQueue(CAP);
	}
	
	public void addAction(Tool tool, boolean attributeCommitEnabled, int width, int height) {
		if (actionQueue.getCurrent() + 1 >= CAP) {
			Action removed = actionQueue.poll();
			//TODO: Write removed meta data to status bar.
		}
		
		GraphicsContext gc = owner.getGraphicsContext2D();
		
		if (attributeCommitEnabled) {
			gc.save();
		}
		
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		
		actionQueue.offer(new Action(tool, owner.snapshot(
				params, new WritableImage(width, height))));
		
		selected = actionQueue.peek();
	}
	
	public void undo() {
		
	}
	
	public void redo() {
		
	}
}
