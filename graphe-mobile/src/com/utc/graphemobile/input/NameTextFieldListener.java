package com.utc.graphemobile.input;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldListener;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.screen.IGrapheScreen;

public class NameTextFieldListener implements TextFieldListener {

	private IGrapheScreen screen = null;

	public NameTextFieldListener(IGrapheScreen screen) {
		this.screen = screen;
	}

	@Override
	public void keyTyped(TextField textField, char key) {
		// Enter OR Ctrl + s
		if (key == 10 || key == 13 || key == 19) {
			List<NodeSprite> selectedNodes = screen.getSelectedNodes();
			if (selectedNodes.size() == 1) {
				selectedNodes.get(0).getNodeModel().getNodeData()
						.setLabel(textField.getText());
			} else if(selectedNodes.size() >= 1) {
				for(int i = 0; i < selectedNodes.size(); i++) {
					selectedNodes.get(i).getNodeModel().getNodeData()
						.setLabel(textField.getText() + "(" + (i + 1) + ")");
				}
			}
		}
		// Ctrl + a
		else if (key == 1) {
			textField.selectAll();
		}
	}
}
