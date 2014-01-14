package com.utc.graphemobile.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.StartButtonListener;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

/**
 * RightMenu of the application
 * 
 * Content depends of the current nodes selection
 */
public class RightMenuSpatialization extends Table {

	private Thread spatialization;
	private IGrapheScreen screen = null;
	private TextButton startButton = null;
	private TextButton stopButton = null;
	private TextButton closeButton = null;
	private ScrollPane scrollPane = null;
	private List list = null;

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
	public RightMenuSpatialization(IGrapheScreen screen) {
		this.screen = screen;
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
	 * Show the menu by moving it to the left
	 */
	public void show() {
		if (visible == false) {
			visible = true;
			// setX(getX() - getWidth());
			addAction(Actions.moveTo(Gdx.graphics.getWidth() - getWidth(), 0, 0.7f,
					Interpolation.fade));
		}
		update();
	}

	/**
	 * Hide the menu by moving it to the right
	 */
	public void hide() {
		if (visible == true) {
			visible = false;
			// setX(getX() + getWidth());
			addAction(Actions.moveTo(Gdx.graphics.getWidth(), 0, 0.7f,
					Interpolation.fade));
		}
		update();
	}
	
	public boolean getIsVisible() {return visible;}

	/**
	 * Update display of the menu
	 */
	private void update() {
		reset();
		left().top();

		String[] listEntries = { "Abstract Force", "FruchtermanReingold",
				"MultiLevel", "Random", "Rotate", "Scale", "Yifan Hu" };
		
		list = new List(listEntries, getSkin());
		list.getStyle().font.setScale(Utils.toDp(0.5f));
		
		//scrollPane = new ScrollPane(list);
		
		add(list).pad(Utils.toDp(PADDING)).left()
				.width(Utils.toDp(WIDTH - PADDING * 2));
		
		this.row();
		
		
		startButton = new TextButton("start", "Start", getSkin().getRegion(
				"play"), getSkin());
		startButton.addListener(new StartButtonListener(screen));
		add(startButton).pad(Utils.toDp(PADDING)).padTop(Utils.toDp(3*PADDING)).left()
				.width(Utils.toDp(WIDTH - PADDING * 2));
		
		this.row();
		
		stopButton = new TextButton("stop", "Stop", getSkin().getRegion(
				"stop"), getSkin());
		stopButton.addListener(new StartButtonListener(screen));
		add(stopButton).pad(Utils.toDp(PADDING)).left()
				.width(Utils.toDp(WIDTH - PADDING * 2));
		
		this.row();
		
		UIEventListener listener = new UIEventListener(screen);
		closeButton = new TextButton("closeRS", "Close", getSkin().getRegion(
				"gray-pixel"), getSkin());
		closeButton.addListener(listener);
		add(closeButton).pad(Utils.toDp(PADDING)).padTop(Utils.toDp(3*PADDING)).left()
		.width(Utils.toDp(WIDTH - PADDING * 2));
	}

	private Skin getSkin() {
		return screen.getSkin();
	}

	public Thread getSpatialization() {
		return spatialization;
	}

	public void setSpatialization(Thread spa) {
		spatialization = spa;
	}

	public List getList() {
		return list;
	}
}
