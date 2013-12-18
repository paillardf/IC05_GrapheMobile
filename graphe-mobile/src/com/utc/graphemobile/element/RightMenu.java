package com.utc.graphemobile.element;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.utc.graphemobile.input.ColorTextFieldListener;
import com.utc.graphemobile.input.NameTextFieldListener;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

/**
 * RightMenu of the application
 * 
 * Content depends of the current nodes selection
 * 
 * @TODO : Add a previsualisation of the color
 */
public class RightMenu extends Table {

	IGrapheScreen screen = null;
	TextField nameTF = null;
	TextField colorTF = null;
	Image unselect = null;
	public static final float PADDING = 5;
	public static final float WIDTH_SCALE = 0.1f;
	boolean visible = false;

	/**
	 * Constructor of RightMenu
	 * 
	 * @param screen
	 *            The GrapeScreen
	 */
	public RightMenu(IGrapheScreen screen) {
		this.screen = screen;

		setBackground(getSkin().getDrawable("gray-pixel"));
		onResize();
	}

	/**
	 * Manage the resize of the menu
	 */
	public void onResize() {
		setWidth(Utils.toDp(Gdx.graphics.getWidth() * WIDTH_SCALE));
		setHeight(Gdx.graphics.getHeight());

		setX(Gdx.graphics.getWidth() + (visible ? -getWidth() : 0));

		update();
	}

	/***
	 * Allow the menu to be refresh
	 */
	public void refresh() {
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
		if (selectedNodes.size() == 0) {
			hide();
		} else {
			show();
		}
	}

	/**
	 * Hide the menu by moving it to the left
	 */
	private void show() {
		if (visible == false) {
			visible = true;
			setX(getX() - getWidth());
		}
		update();
	}

	/**
	 * Hide the menu by moving it to the right
	 */
	private void hide() {
		if (visible == true) {
			visible = false;
			setX(getX() + getWidth());
		}
		update();
	}

	/**
	 * Update display of the menu
	 */
	private void update() {
		reset();
		left().top();
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();

		// Manage the name
		if (visible && selectedNodes.size() == 1) {
			if (nameTF == null) {
				nameTF = new TextField(" ", getSkin());
				nameTF.setTextFieldListener(new NameTextFieldListener(screen));
			}
			nameTF.setText(selectedNodes.get(0).getNodeModel().getNodeData()
					.getLabel());
			add(nameTF).top().left().pad(Utils.toDp(PADDING))
					.width(getWidth() - 2 * Utils.toDp(PADDING))
					.height(getWidth() * 0.3f);
			row();
		} else if (nameTF != null) {
			removeActor(nameTF);
		}

		if (visible && selectedNodes.size() >= 1) {
			// manage the color
			if (colorTF == null) {
				colorTF = new TextField(" ", getSkin());
				colorTF.setTextFieldListener(new ColorTextFieldListener(screen));
			}
			colorTF.setText(getColorToEdit(selectedNodes));
			add(colorTF).top().left().pad(Utils.toDp(PADDING))
					.width(getWidth() - 2 * Utils.toDp(PADDING))
					.height(getWidth() * 0.3f);
			row();
			row();

			// Manage the unselect button
			if (unselect == null) {
				unselect = new Image(getSkin().getRegion("close"));
				unselect.setName("unselect");
				UIEventListener listener = new UIEventListener(screen);
				unselect.addListener(listener);
			}
			add(unselect).right();
			row();
		} else {
			if (colorTF != null) {
				removeActor(colorTF);
			}
			if (unselect != null) {
				removeActor(unselect);
			}
		}
	}

	private Skin getSkin() {
		return screen.getSkin();
	}

	/**
	 * Get the medium color of all selected elements
	 * 
	 * @param selectedNodes
	 *            All selected elements
	 * @return The corresponding color
	 */
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
		return color.toString().substring(0, 6);
	}

}
