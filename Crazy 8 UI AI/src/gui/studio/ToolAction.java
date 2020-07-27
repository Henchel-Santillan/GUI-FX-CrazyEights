package gui.studio;

//Defines a writable action event class when Tool 'apply()' is called.
//Combined with ToolHistory
//Can ToolAction be a Canvas that is created after every ToolOperation?

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

//Path and bitmap are not part of save(_ and restore()
public class ToolAction {
	
	public ToolAction() {
		
	}
	
	public String toLoggableForm() {
		return "";
	}
}
