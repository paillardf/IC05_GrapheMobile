package com.utc.graphemobile.element;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
public class RightMenuEdit extends Table {

	IGrapheScreen screen = null;
	TextField nameTF = null;
	TextField colorTF = null;
	ColorVisualisation colorSample = null;
	TextButton unselect = null;
	TextButton delete = null;
	private Table table;
	boolean visible = false;
	public static final float PADDING = 5;
	public static final float WIDTH = 200;
	public static final float FIELD_HEIGHT = 40;

	/**
	 * Constructor of RightMenu
	 * 
	 * @param screen
	 *            The GrapeScreen
	 */
	public RightMenuEdit(IGrapheScreen mScreen) {
		this.screen = mScreen;

		UIEventListener listener = new UIEventListener(screen);

		nameTF = new TextField(" ", getSkin());
		nameTF.setTextFieldListener(new NameTextFieldListener(screen));
		
		colorSample = new ColorVisualisation(getSkin().getRegion("white-pixel"));
		colorSample.addListener(new ClickListener(){

			@Override
			public void touchUp(InputEvent event, float x, float y,
					int pointer, int button) {
				super.touchUp(event, x, y, pointer, button);
				if(screen.getSelectedNodes().size() == 1) {
					screen.selecteColor(screen.getSelectedNodes().get(0).getColor());
				}
			}

		});
		colorTF = new TextField(" ", getSkin());
		colorTF.setTextFieldListener(new ColorTextFieldListener(screen, colorSample));

		unselect = new TextButton("unselect", "Unselect", screen.getSkin()
				.getRegion("close"), screen.getSkin());
		unselect.addListener(listener);
		
		delete = new TextButton("delete", "Delete", screen.getSkin()
				.getRegion("delete"), screen.getSkin());
		delete.addListener(listener);
		
		table = new Table();
		
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
			Gdx.input.setOnscreenKeyboardVisible(false);
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

		reset(); table.reset();
		left().top(); table.left().top();
		// Layout
		table.add(nameTF).top().left().pad(Utils.toDp(PADDING))
				.width(getWidth() - 2 * Utils.toDp(PADDING))
				.height(Utils.toDp(FIELD_HEIGHT));
		table.row();
		table.add(colorSample).top().left().pad(Utils.toDp(PADDING))
				.width(getWidth() - 2 * Utils.toDp(PADDING))
				.height(Utils.toDp(FIELD_HEIGHT));
		table.row();
		table.add(colorTF).top().left().pad(Utils.toDp(PADDING))
				.width(getWidth() - 2 * Utils.toDp(PADDING))
				.height(Utils.toDp(FIELD_HEIGHT));
		table.row();
	
		// Manage the unselect button
		table.add(unselect).pad(Utils.toDp(PADDING));
		table.row();
		
		// Manage the delete button
		table.add(delete).pad(Utils.toDp(PADDING));
		table.row();
		
		add(table);
	}
	
	public boolean getIsVisible() {return visible;}

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

	public boolean pan(float x, float y, float deltaX, float deltaY) {
		if(this.getHeight() > table.getHeight()) return true;
		table.setY(table.getY() - deltaY);
		return false;
	}

	public boolean containsX(float x) {
		return this.getX() < x && this.getX() + this.getWidth() > x;
	}

	public boolean containsY(float y) {
		return this.getY() < y && this.getY() + this.getHeight() > y;
	}

	public boolean contains(float x, float y) {
		return containsX(x) && containsY(y);
	}

	public boolean fling(float velocityX, float velocityY, int button) {
		if(this.getHeight() > table.getHeight()) return true;
		if(velocityY > 0) {
			table.addAction(Actions.moveTo(table.getX(), this.getHeight() - table.getHeight(), (float)(0.4f + Math.log10(velocityY) / 10),
					Interpolation.fade));
		} else if(velocityY != 0) {
			table.addAction(Actions.moveTo(table.getX(), 0, (float)(0.4f + Math.log10(-velocityY) / 10), Interpolation.fade));
		}
		return false;
	}

}
