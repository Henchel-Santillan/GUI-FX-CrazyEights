package gui.studio.manager;

import gui.studio.tool.Tool;

import javafx.scene.image.WritableImage;

public class Action {
	
	private final Tool tool;
	private final WritableImage snapshot;
	
	public Action(Tool tool, WritableImage snapshot) {
		this.tool = tool;
		this.snapshot = snapshot;
	}
	
	public Tool getTool() {
		return tool;
	}
	
	public WritableImage getSnapshot() {
		return snapshot;
	}
	
	public String toLoggableForm() {
		return tool.toString();
	}
}
