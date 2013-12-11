package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;

public class ColorTextFieldListener implements TextFieldListener{

	TextField mTextField;
	
	public ColorTextFieldListener(TextField textField){
		this.mTextField = textField;
	}

	@Override
	public void keyTyped(TextField textField, char key) {
		// TODO Auto-generated method stub
		System.out.println(mTextField.getText());
	}
}
