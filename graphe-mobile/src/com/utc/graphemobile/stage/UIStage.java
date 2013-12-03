package com.utc.graphemobile.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.UIEventListener;

public class UIStage extends Stage{
	
	private UIEventListener eventListener;
	private Table table;
	
	private float widthUI = 0.0f;	
	private float widthLeftMenu = 0.0f;
	private float widthRightMenu = 0.0f;

	private float widthBorder = 0.0f;
	
	private float heightUI = 0.0f;
	private float heightLeftMenu = 0.0f;
	private float heightRightMenu = 0.0f;
	private float heightBorder = 0.0f;
	
	public UIStage() {
		super();
		
		heightUI = this.getHeight();
		widthUI = this.getWidth();
		
		widthLeftMenu = (float) (widthUI*0.075);
		widthRightMenu = (float) (widthUI*0.25);
		widthBorder = (float) (widthUI*0.005);
				
		eventListener = new UIEventListener();
		
		table = new Table();
		table.left().top();

		table.setWidth(widthUI);
		table.setHeight(heightUI);

		this.addActor(table);
	}
	
	public void size() {		
		widthUI = this.getWidth();
		heightUI = this.getHeight();
		
		heightLeftMenu = heightRightMenu = heightUI;
		heightBorder = heightUI;
	}
	
	public void drawLeftMenu() {
		Skin skin = new Skin();

		// Left Menu
		table.add(leftMenu(skin)).height(heightUI).width(widthLeftMenu);
		table.add(border(skin)).height(heightUI).width(widthBorder);
	}
	
	public void drawRightMenu() {
		Skin skin = new Skin();
		
		// Right Menu
		table.add().height(heightUI)
			.width(widthUI - (widthBorder + widthRightMenu + widthLeftMenu));
		table.add(rightMenu(skin)).height(heightUI).width(widthRightMenu);
	}

	
	private Table leftMenu(Skin skin) {
		Table table = new Table();
		
		Pixmap p = new Pixmap(1, 1, Format.RGBA8888);
		p.setColor(Color.WHITE);
		p.fill();
		skin.add("white", new Texture(p));
		
		float scale = 0.0f;
		
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("ok-icon-md.png")));
		Image image1 = new Image(tr);		
		scale = (float) (widthLeftMenu / image1.getWidth());				
		image1.setWidth(widthLeftMenu);
		image1.setHeight((int) (image1.getHeight() * scale));
		image1.setX(0);
		image1.setY(heightUI-image1.getHeight());
		table.addActor(image1);
		image1.addListener(eventListener);
		
		
		//table.row();
		
		tr = new TextureRegion(new Texture(Gdx.files.internal("Delete All.png")));		
		Image image2 = new Image(tr);
		scale = (float) (widthLeftMenu / image2.getWidth());	
		image2.setWidth(widthLeftMenu);
		image2.setHeight((int) (image2.getHeight() * scale));
		image2.setX(0);
		image2.setY(image1.getY()-image2.getHeight()-20);		
		table.addActor(image2);
		image2.addListener(eventListener);
						
		table.setBackground(skin.getDrawable("white"));		
		
		return table;
	}
	
	private Table rightMenu(Skin skin) {
		Table table = new Table();
		
		Pixmap p = new Pixmap(1, 1, Format.RGBA8888);
		p.setColor(Color.GRAY);
		p.fill();
		skin.add("grey", new Texture(p));
								
		table.setBackground(skin.getDrawable("grey"));		
		
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

	public float getWidthUI() {
		return widthUI;
	}

	public void setWidthUI(float witdh) {
		this.widthUI = witdh;
	}

	public float getWidthLeftMenu() {
		return widthLeftMenu;
	}

	public void setWidthLeftMenu(float widthLeftMenu) {
		this.widthLeftMenu = widthLeftMenu;
	}

	public float getWidthBorder() {
		return widthBorder;
	}

	public void setWidthBorder(float widthBorder) {
		this.widthBorder = widthBorder;
	}

	public float getHeightUI() {
		return heightUI;
	}

	public void setHeightUI(float height) {
		this.heightUI = height;
	}

	public float getHeightLeftMenu() {
		return heightLeftMenu;
	}

	public void setHeightLeftMenu(float heightLeftMenu) {
		this.heightLeftMenu = heightLeftMenu;
	}
	
	public float getWidthRightMenu() {
		return widthRightMenu;
	}

	public void setWidthRightMenu(float widthRightMenu) {
		this.widthRightMenu = widthRightMenu;
	}

	public float getHeightRightMenu() {
		return heightRightMenu;
	}

	public void setHeightRightMenu(float heightRightMenu) {
		this.heightRightMenu = heightRightMenu;
	}

	public float getHeightBorder() {
		return heightBorder;
	}

	public void setHeightBorder(float heightBorder) {
		this.heightBorder = heightBorder;
	}	
}
