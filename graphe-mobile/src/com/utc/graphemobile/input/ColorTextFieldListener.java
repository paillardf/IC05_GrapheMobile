package com.utc.graphemobile.input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.screen.IGrapheScreen;

public class ColorTextFieldListener implements TextFieldListener {
	IGrapheScreen screen = null;
	
	public ColorTextFieldListener(IGrapheScreen screen) {
		// TODO Auto-generated constructor stub
		this.screen = screen;
	}

	@Override
	public void keyTyped(TextField textField, char key) {
		String text = textField.getText();
		// Enter
		if (key == 13) {
			System.out.println("Enter");
			Color color = Color.valueOf(text);
			for(NodeSprite nodeSprite : screen.getSelectedNodes()){
				nodeSprite.getNodeModel().getNodeData().setR(color.r);
				nodeSprite.getNodeModel().getNodeData().setG(color.g);
				nodeSprite.getNodeModel().getNodeData().setB(color.b);
				nodeSprite.getNodeModel().getNodeData().setAlpha(color.a);
			}
		}
		// Return
		else if (key == 8) {
			System.out.println("Return");
		} else if ((key >= 97 && key <= 102) || (key >= 65 && key <= 70)
				|| (key >= 48 && key <= 57)) {
			if(text.length() > 6){
				text.substring(0, 6);
				textField.setText(text);
			}
		} else {
			text = text.replaceAll(String.valueOf(key), "");
			textField.setText(text);
		}
	}
}
