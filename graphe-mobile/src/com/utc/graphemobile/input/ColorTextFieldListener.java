package com.utc.graphemobile.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.utc.graphemobile.element.ColorVisualisation;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.screen.IGrapheScreen;

public class ColorTextFieldListener implements TextFieldListener {
	ColorVisualisation colorSample;
	
	IGrapheScreen screen = null;

	public ColorTextFieldListener(IGrapheScreen screen, ColorVisualisation colorSample) {
		this.screen = screen;
		this.colorSample = colorSample;
	}

	@Override
	public void keyTyped(TextField textField, char key) {
		int cursorPosition = textField.getCursorPosition();
		String text = textField.getText();
		// Enter OR Ctrl + s
		if (key == 10 || key == 13 || key == 19) {
			if (text.length() < 6) {
				text = (text + "000000").substring(0, 6);
				textField.setText(text);
				textField.setCursorPosition(cursorPosition);
			}
			Color color = Color.valueOf(text);
			for (NodeSprite nodeSprite : screen.getSelectedNodes()) {
				nodeSprite.setColor(color);
			}
		}
		// Ctrl + a
		else if (key == 1) {
			textField.selectAll();
		}
		// Return
		else if (key == 8) {}
		// Casual letter
		else if ((key >= 97 && key <= 102) || (key >= 65 && key <= 70)
				|| (key >= 48 && key <= 57)) {
			if (text.length() > 6) {
				text = text.substring(0, 6);
				textField.setText(text);
				textField.setCursorPosition(cursorPosition);
			}
		}
		// Other letter
		else if (key != 0) {
			text = text.replace(String.valueOf(key), "");
			textField.setText(text);
			if (textField.getCursorPosition() != cursorPosition)
				textField.setCursorPosition(cursorPosition - 1);
		}
		colorSample.setColor(Color.valueOf((text + "000000").substring(0, 6)));
	}
}
