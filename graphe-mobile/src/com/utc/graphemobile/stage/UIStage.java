package com.utc.graphemobile.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.utc.graphemobile.input.UIGestureListener;

public class UIStage extends Stage{
	
	private UIGestureListener gestureListener;
	private Table table;
	
	public UIStage() {
		super();
		
		Skin skin = new Skin();
		
		table = new Table();
		table.setFillParent(true);
		table.left().top();
		
		// Left Menu
		table.add(leftMenu(skin)).height(this.getHeight()).width((float) (this.getWidth()*0.075));
		table.add(border(skin)).height(this.getHeight()).width((float) (this.getWidth()*0.005));
		
		this.addActor(table);
		
		gestureListener = new UIGestureListener();
	}
	
	private Table leftMenu(Skin skin) {
		Table table = new Table();
		
		Pixmap p = new Pixmap(1, 1, Format.RGBA8888);
		p.setColor(Color.WHITE);
		p.fill();
		skin.add("white", new Texture(p));
		
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("ok-icon-md.png")));
		Image image1 = new Image(tr);
		image1.setScaling(Scaling.fillX);
		table.add(image1);
		
		table.row();
		
		tr = new TextureRegion(new Texture(Gdx.files.internal("Delete All.png")));
		Image image2 = new Image(tr);
		image2.setScaling(Scaling.fillX);
		table.add(image2);
				
		table.setBackground(skin.getDrawable("white"));
		
		return table;
	}
	
	private Table border(Skin skin) {
		Table table = new Table();
		
		Pixmap p = new Pixmap(1, 1, Format.RGBA8888);
		p.setColor(Color.GRAY);
		p.fill();
		skin.add("gray", new Texture(p));
		
		table.setBackground(skin.getDrawable("gray"));
		
		return table;
	}

	public UIGestureListener getGestureListener() {
		return gestureListener;
	}

	public void setGestureListener(UIGestureListener gestureListener) {
		this.gestureListener = gestureListener;
	}

	
}
