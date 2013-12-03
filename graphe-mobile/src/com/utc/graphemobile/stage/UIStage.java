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
	private Table leftTable;
	private Table rightTable;
	private Skin skin;
	
	private float widthUI = 0.0f;	
	private float widthLeftMenu = 0.0f;
	private float widthRightMenu = 0.0f;

	private float widthBorder = 0.0f;
	
	private float heightUI = 0.0f;
	
	public UIStage() {
		super();
		eventListener = new UIEventListener();
		skin = new Skin();

		leftTable = new Table();
		leftTable.left().top();
		
		rightTable = new Table();
		rightTable.left().top();
		
		Pixmap p = new Pixmap(1, 1, Format.RGBA8888);
		p.setColor(Color.WHITE);
		p.fill();
		skin.add("white", new Texture(p));
		
		p.setColor(Color.GRAY);
		p.fill();
		skin.add("gray", new Texture(p));
		
		resize();
	}
	
	public void resize() {		
		widthUI = this.getWidth();
		heightUI = this.getHeight();
		
		widthLeftMenu = (float) (widthUI*0.075);
		widthRightMenu = (float) (widthUI*0.25);
		widthBorder = (float) (widthUI*0.005);

		leftTable.reset();
		leftTable.setWidth(widthUI);
		leftTable.setHeight(heightUI);
		drawLeftMenu();

		rightTable.reset();
		rightTable.setWidth(widthUI);
		rightTable.setHeight(heightUI);
		drawRightMenu();
	}
	
	public void showLeftMenu() {
		this.addActor(leftTable);
	}
	
	public void showRightMenu() {
		this.addActor(rightTable);
	}
	
	public void hideLeftMenu() {
		leftTable.remove();
	}
	
	public void hideRightMenu() {
		rightTable.remove();
	}
	
	private void drawLeftMenu() {
		// Left Menu
		leftTable.add(leftMenu()).height(heightUI).width(widthLeftMenu);
		leftTable.add(border()).height(heightUI).width(widthBorder);
	}
	
	private void drawRightMenu() {
		// Right Menu
		rightTable.add().height(heightUI)
			.width(widthUI - widthRightMenu);
		rightTable.add(rightMenu()).height(heightUI).width(widthRightMenu);
	}
	
	private Table leftMenu() {
		Table table = new Table();
		
		float scale = 0.0f;
		
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("ok-icon-md.png")));
		Image image1 = new Image(tr);		
		scale = (float) (widthLeftMenu / image1.getWidth());				
		image1.setWidth(widthLeftMenu);
		image1.setHeight((int) (image1.getHeight() * scale));
		image1.setX(0);
		image1.setY(heightUI - image1.getHeight());
		table.addActor(image1);
		image1.addListener(eventListener);
		
		
		//table.row();
		
		tr = new TextureRegion(new Texture(Gdx.files.internal("Delete All.png")));		
		Image image2 = new Image(tr);
		scale = (float) (widthLeftMenu / image2.getWidth());	
		image2.setWidth(widthLeftMenu);
		image2.setHeight((int) (image2.getHeight() * scale));
		image2.setX(0);
		image2.setY(image1.getY() - image2.getHeight() - 20);		
		table.addActor(image2);
		image2.addListener(eventListener);
						
		table.setBackground(skin.getDrawable("white"));		
		
		return table;
	}
	
	private Table rightMenu() {
		Table table = new Table();
		
		// TODO G�rer le menu
		
		table.setBackground(skin.getDrawable("gray"));
		return table;
	}
	
	private Table border() {
		Table table = new Table();
		table.setBackground(skin.getDrawable("gray"));
		return table;
	}
}
