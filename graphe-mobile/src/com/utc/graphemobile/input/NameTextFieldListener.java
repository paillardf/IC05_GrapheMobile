package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;

public class NameTextFieldListener implements TextFieldListener{
	
	TextField mTextField;
	
	public NameTextFieldListener(TextField textField){
		this.mTextField = textField;
	}

	@Override
	public void keyTyped(TextField textField, char key) {
		// TODO Auto-generated method stub
		System.out.println(mTextField.getText());
		
	}
}
