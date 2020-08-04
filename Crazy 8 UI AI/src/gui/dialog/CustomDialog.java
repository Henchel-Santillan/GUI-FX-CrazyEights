package gui.dialog;

import javafx.stage.Stage;

//TODO: How to make result for a CustomDialog?
public class CustomDialog extends DialogUtil {

	public CustomDialog(Stage parent) {
		super(parent);
		
		//TODO: Determine action on Ok click
		ok.setOnAction(e -> {
			modal.close();
			e.consume();
		});
	}
	
	public void addPair(DialogPair pair) {
		frame.getChildren().add(frame.getChildren().size() - 3, pair.getModel());
	}
}
