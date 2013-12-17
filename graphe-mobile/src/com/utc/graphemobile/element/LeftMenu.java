package com.utc.graphemobile.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.ShowLeftMenuEventListener;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.stage.UIStage;
import com.utc.graphemobile.utils.Utils;

public class LeftMenu extends Table {
	public static final float WIDTH = 100;
	public static final float PADDING = 5;
	public static final float CLOSE_SIZE = 20;
	private Image img;
	public LeftMenu(UIStage stage) {

		UIEventListener listener = new UIEventListener(stage.screen);
		top();
		left();
		TextureRegion deleteTR = new TextureRegion(new Texture(Gdx.files.internal("Delete All.png")));//TODO sale 
		img = new Image(deleteTR);
		img.setHeight(Utils.toDp(CLOSE_SIZE));
		img.setWidth(Utils.toDp(CLOSE_SIZE));
		img.setPosition(Utils.toDp(WIDTH),Gdx.graphics.getHeight()-img.getHeight());
		img.addListener(new ShowLeftMenuEventListener(this));
		this.addActor(img);
		
		
		TextButton bt = new TextButton("open","Open", stage.getSkin().getRegion("open"), stage.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		row();
		bt = new TextButton("center", "Center", stage.getSkin().getRegion("center"), stage.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		row();
		bt = new TextButton("label", "Label", stage.getSkin().getRegion("label"), stage.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		row();
		bt = new TextButton("about", "About", stage.getSkin().getRegion("about"), stage.getSkin());
		bt.addListener(listener);
		this.add(bt).pad(Utils.toDp(PADDING));
		
		
		
		setWidth(Utils.toDp(WIDTH));
		setHeight(Gdx.graphics.getHeight());
		
		setBackground(stage.getSkin().getDrawable("gray-pixel"));
		
		
	}
	
	public void onResize(){
		setHeight(Gdx.graphics.getHeight());
		img.setY(Gdx.graphics.getHeight()-img.getHeight());
	}

	

	
}
