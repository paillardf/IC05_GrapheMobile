package com.utc.graphemobile.element;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.utc.graphemobile.input.ColorTextFieldListener;
import com.utc.graphemobile.input.NameTextFieldListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

public class RightMenu extends Table {

	IGrapheScreen screen = null;
	TextField nameTF = null;
	TextField colorTF = null;
	public static final float PADDING = 5;
	public static final float WIDTH_SCALE = 0.1f;
	boolean visible = false;

	public RightMenu(IGrapheScreen screen) {
		this.screen = screen;

		left().top();
		setBackground(getSkin().getDrawable("gray-pixel"));
		onResize();
	}

	public void onResize() {
		setWidth(Utils.toDp(Gdx.graphics.getWidth() * WIDTH_SCALE));
		setHeight(Gdx.graphics.getHeight());

		setX(Gdx.graphics.getWidth());

		update();
	}

	public void refresh() {
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
		if (selectedNodes.size() == 0) {
			hide();
		} else {
			show();
		}
	}

	private void show() {
		if (visible == false) {
			visible = true;
			setX(getX() - getWidth());
		}
		update();
	}

	private void hide() {
		if (visible == true) {
			visible = false;
			setX(getX() + getWidth());
		}
		update();
	}

	private void update() {
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
		if (visible && selectedNodes.size() == 1) {
			if (nameTF == null) {
				nameTF = new TextField(" ", getSkin());
				nameTF.setTextFieldListener(new NameTextFieldListener(screen));
			}
			nameTF.setText(selectedNodes.get(0).getNodeModel().getNodeData()
					.getLabel());
			add(nameTF).pad(Utils.toDp(PADDING));
			nameTF.setWidth(Utils.toDp(getWidth() - 2 * PADDING));
			nameTF.setHeight(getWidth() * 0.3f);
			row();
		} else if (nameTF != null) {
			removeActor(nameTF);
		}

//		if (visible && selectedNodes.size() >= 1) {
//			if (colorTF == null) {
//				colorTF = new TextField(" ", getSkin());
//				colorTF.setTextFieldListener(new ColorTextFieldListener(screen));
//			}
//			colorTF.setText(getColorToEdit(selectedNodes));
//			add(colorTF).pad(Utils.toDp(PADDING));
//			row();
//		} else if (colorTF != null) {
//			removeActor(colorTF);
//		}
	}

	private Skin getSkin() {
		return screen.getSkin();
	}

	private String getColorToEdit(List<NodeSprite> selectedNodes) {
		if (selectedNodes.size() == 0)
			return "000000";
		float r, g, b, a;
		r = g = b = a = 0.0f;

		for (NodeSprite nodeSprite : selectedNodes) {
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
