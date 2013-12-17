package com.utc.graphemobile.element;

import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.utc.graphemobile.input.ColorTextFieldListener;
import com.utc.graphemobile.input.NameTextFieldListener;
import com.utc.graphemobile.screen.GrapheScreen;
import com.utc.graphemobile.screen.IGrapheScreen;

public class RightMenu extends Table {

	IGrapheScreen screen = null;
	Skin skin = null;
	TextField nameTF = null;
	TextField colorTF = null;
	boolean visible = false;

	RightMenu(IGrapheScreen screen, Skin skin) {
		this.screen = screen;
		this.skin = skin;
	}

	void resize(float width, float height) {
		reset();
		left().top();
		setFillParent(true);
		setWidth(width);
		setHeight(height);
		setBackground(skin.getDrawable("gray-pixel"));
		update();
	}

	void refresh() {
		update();
	}

	private void show() {
		visible = true;
		update();
	}

	private void hide() {
		visible = false;
		update();
	}

	private void update() {
		float currentY = getHeight();
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
		if (visible && selectedNodes.size() == 1) {
			if (colorTF == null) {
				nameTF = new TextField("", skin);
				nameTF.setWidth(getWidth() - 10);
				nameTF.setTextFieldListener(new NameTextFieldListener(screen));
			}
			nameTF.setText(selectedNodes.get(0).getNodeModel().getNodeData().getLabel());
			nameTF.setHeight(nameTF.getWidth() * 0.3f);
			nameTF.setX(5);
			currentY -= nameTF.getHeight() + 5;
			nameTF.setY(currentY);
			addActor(nameTF);
		}
		else if(nameTF != null){
			removeActor(nameTF);
		}
		
		if (visible && selectedNodes.size() >= 1) {
			if (colorTF == null) {
				colorTF = new TextField("", skin);
				colorTF.setTextFieldListener(new ColorTextFieldListener(screen));
			}
			colorTF.setText(getColorToEdit(selectedNodes));
			colorTF.setHeight(colorTF.getWidth() * 0.3f);
			colorTF.setX(5);
			currentY -= colorTF.getHeight() + 5;
			colorTF.setY(currentY);
			colorTF.setWidth(getWidth() - 10);
			addActor(colorTF);
		}
		else if(colorTF != null){
			removeActor(colorTF);
		}
	}

	private String getColorToEdit(List<NodeSprite> selectedNodes) {
		if(selectedNodes.size() == 0) return "000000";
		float r, g, b, a;
		r = g = b = a = 0.0f;
		
		for(NodeSprite nodeSprite : selectedNodes){
			r += nodeSprite.getNodeModel().getNodeData().r();
			g += nodeSprite.getNodeModel().getNodeData().g();
			b += nodeSprite.getNodeModel().getNodeData().b();
		}
		r /= selectedNodes.size();
		g /= selectedNodes.size();
		b /= selectedNodes.size();
		
		Color color = new Color();
		color.set(r, g, b, a);
		return color.toString();
	}

}
