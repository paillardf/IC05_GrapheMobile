package com.utc.graphemobile.element;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.utc.graphemobile.input.ColorTextFieldListener;
import com.utc.graphemobile.input.NameTextFieldListener;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

/**
 * RightMenu of the application
 * 
 * Content depends of the current nodes selection
 */
public class RightMenu extends Table {

	IGrapheScreen screen = null;
	TextField nameTF = null;
	TextField colorTF = null;
	ColorVisualisation colorSample = null;
	TextButton unselect = null;
	TextButton delete = null;
	public static final float PADDING = 5;
	public static final float WIDTH = 200;
	public static final float FIELD_HEIGHT = 40;
	boolean visible = false;

	/**
	 * Constructor of RightMenu
	 * 
	 * @param screen
	 *            The GrapeScreen
	 */
	public RightMenu(IGrapheScreen screen) {
		this.screen = screen;

		UIEventListener listener = new UIEventListener(screen);

		nameTF = new TextField(" ", getSkin());
		nameTF.setTextFieldListener(new NameTextFieldListener(screen));
		
		colorSample = new ColorVisualisation(getSkin().getRegion("white-pixel"));
		colorTF = new TextField(" ", getSkin());
		colorTF.setTextFieldListener(new ColorTextFieldListener(screen, colorSample));

		unselect = new TextButton("unselect", "Unselect", screen.getSkin()
				.getRegion("close"), screen.getSkin());
		unselect.addListener(listener);
		
		delete = new TextButton("delete", "Delete", screen.getSkin()
				.getRegion("delete"), screen.getSkin());
		delete.addListener(listener);
		
		setBackground(getSkin().getDrawable("gray-pixel"));
		onResize();
	}

	/**
	 * Manage the resize of the menu
	 */
	public void onResize() {
		setWidth(Utils.toDp(WIDTH));
		setHeight(Gdx.graphics.getHeight());

		setX(Gdx.graphics.getWidth() + (visible ? -getWidth() : 0));

		update();
	}

	/**
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
	 * Show the menu by moving it to the left
	 */
	private void show() {
		if (visible == false) {
			visible = true;
			// setX(getX() - getWidth());
			addAction(Actions.moveTo(getX() - getWidth(), 0, 0.7f,
					Interpolation.fade));
		}
		update();
	}

	/**
	 * Hide the menu by moving it to the right
	 */
	private void hide() {
		if (visible == true) {
			visible = false;
			// setX(getX() + getWidth());
			addAction(Actions.moveTo(getX() + getWidth(), 0, 0.7f,
					Interpolation.fade));
		}
		update();
	}

	/**
	 * Update display of the menu
	 */
	private void update() {
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
		if (selectedNodes.size() < 0) return;

		// Manage the name
		nameTF.getStyle().font.setScale(Utils.toDp(0.5f));
		if (selectedNodes.size() == 1) {
			nameTF.setText(selectedNodes.get(0).getNodeModel().getNodeData()
					.getLabel());
		} else {
			nameTF.setText("name");
		}
		Color color = getColorToEdit(selectedNodes);
		colorSample.setColor(color);
		// manage the color
		colorTF.setText(color.toString().substring(0, 6));

		reset();
		left().top();
		// Layout
		add(nameTF).top().left().pad(Utils.toDp(PADDING))
			.width(getWidth() - 2 * Utils.toDp(PADDING))
			.height(Utils.toDp(FIELD_HEIGHT));
		row();
		add(colorSample).top().left().pad(Utils.toDp(PADDING))
			.width(getWidth() - 2 * Utils.toDp(PADDING))
			.height(Utils.toDp(FIELD_HEIGHT));
		row();
		add(colorTF).top().left().pad(Utils.toDp(PADDING))
			.width(getWidth() - 2 * Utils.toDp(PADDING))
			.height(Utils.toDp(FIELD_HEIGHT));
		row();
	
		// Manage the unselect button
		add(unselect).pad(Utils.toDp(PADDING));
		row();
		
		// Manage the delete button
		add(delete).pad(Utils.toDp(PADDING));
		row();
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
	private Color getColorToEdit(List<NodeSprite> selectedNodes) {
		if (selectedNodes.size() == 0)
			return new Color();
		float r, g, b, alpha;
		r = g = b = alpha = 0.0f;

		for (NodeSprite nodeSprite : selectedNodes) {
			r += nodeSprite.getNodeModel().getNodeData().r();
			g += nodeSprite.getNodeModel().getNodeData().g();
			b += nodeSprite.getNodeModel().getNodeData().b();
			alpha += nodeSprite.getNodeModel().getNodeData().alpha();
		}
		r /= selectedNodes.size();
		g /= selectedNodes.size();
		b /= selectedNodes.size();
		alpha /= selectedNodes.size();

		Color color = new Color();
		color.set(r, g, b, alpha);
		return color;
	}

}
