package com.utc.graphemobile.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.ShowLeftMenuEventListener;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

public class LeftMenu extends Table {
	public static final float WIDTH = 100;
	public static final float PADDING = 5;
	public static final float CLOSE_SIZE = 20;
	private Image img;
	private IGrapheScreen screen = null;

	public LeftMenu(IGrapheScreen screen) {

		this.screen = screen;

		UIEventListener listener = new UIEventListener(screen);
		top();
		left();
		TextureRegion deleteTR = new TextureRegion(new Texture(
				Gdx.files.internal("Delete All.png")));// TODO sale
		img = new Image(deleteTR);
		img.setHeight(Utils.toDp(CLOSE_SIZE));
		img.setWidth(Utils.toDp(CLOSE_SIZE));
		img.setPosition(Utils.toDp(WIDTH),
				Gdx.graphics.getHeight() - img.getHeight());
		img.addListener(new ShowLeftMenuEventListener(this));
		this.addActor(img);

		TextButton bt = new TextButton("open", "Open", screen.getSkin()
				.getRegion("open"), screen.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		row();
		bt = new TextButton("center", "Center", screen.getSkin().getRegion(
				"center"), screen.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		row();
		bt = new TextButton("label", "Label", screen.getSkin().getRegion(
				"label"), screen.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		row();
		bt = new TextButton("about", "About", screen.getSkin().getRegion(
				"about"), screen.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));

		setWidth(Utils.toDp(WIDTH));
		setHeight(Gdx.graphics.getHeight());

		setBackground(screen.getSkin().getDrawable("gray-pixel"));

		this.setX(Utils.toDp(-WIDTH));
	}

	public void onResize() {
		setHeight(Gdx.graphics.getHeight());
		invalidate();
		img.setY(getHeight() - img.getHeight());
	}
}
