package test_main;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.scene.control.TextField;

public class LimitedTextField extends TextField {
	
	private IntegerProperty maxLength;
	
	public LimitedTextField() {
		super();
		maxLength = new SimpleIntegerProperty(255);
	}
	
	public LimitedTextField(String text) {
		super(text);
		maxLength = new SimpleIntegerProperty(255);
	}
	
	public IntegerProperty maxLengthProperty() {
		return maxLength;
	}
	
	public int getMaxLength() {
		return maxLength.get();
	}
	
	public boolean setMaxLength(int maxLength) {
		if (maxLength > 0) {
			this.maxLength.set(maxLength);
			return true;
		}
		throw new IllegalArgumentException("Max Length must be greater than 0.");
	}
	
	@Override
	public void replaceText(int start, int end, String insertedText) {
		String currentText = this.getText() == null ? "" : this.getText();
		String finalText = currentText.substring(0, start).concat(insertedText).concat(currentText.substring(end));
		
		int exceeding = finalText.length() - this.getMaxLength();
		
		if (exceeding <= 0) {
			super.replaceText(start, end, insertedText);
		} else {
			String cutInsertedText = insertedText.substring(0, insertedText.length() - exceeding);
			super.replaceText(start,  end, cutInsertedText);
		}
	}
	
	public void setNumericOnly() {
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("\\d*")) {
				setText(newValue.replaceAll("[^\\d]", ""));
			}
		});
	}
	
	public void setAlphaOnly() {
		this.textProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue.matches("/^[A-Za-z]+$/")) {
				setText(newValue.replaceAll("[^a-zA-Z]", ""));
			}
		});
	}
	
}
