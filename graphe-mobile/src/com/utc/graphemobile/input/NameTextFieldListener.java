package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.utc.graphemobile.screen.IGrapheScreen;

public class NameTextFieldListener implements TextFieldListener{

	public NameTextFieldListener(IGrapheScreen screen) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void keyTyped(TextField textField, char key) {
		// TODO Auto-generated method stub
		System.out.println(textField.getText());
		
	}
}
