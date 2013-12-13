package com.utc.graphemobile.stage;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.input.*;
import com.utc.graphemobile.screen.*;

public class UIStage extends Stage {
	private GrapheScreen screen;
	private UIEventListener eventListener;
	private Table leftTable;
	private Table rightTable;
	private Image hideLeftMenu;
	private Image showLeftMenu;
	private Skin skin;

	private float widthUI = 0.0f;
	private float widthLeftMenu = 0.0f;
	private float widthRightMenu = 0.0f;

	private float widthBorder = 0.0f;

	private float heightUI = 0.0f;

	public UIStage(GrapheScreen screen) {
		super();
		this.screen = screen;
		eventListener = new UIEventListener();
		skin = new Skin(Gdx.files.internal("data/style.json"),
				new TextureAtlas("data/style.atlas"));
		
		leftTable = new Table();

		rightTable = new Table();

		resize();
		drawLeftMenu();
		drawRightMenu();
	}

	public void resize() {
		widthUI = this.getWidth();
		heightUI = this.getHeight();

		widthLeftMenu = (float) (widthUI * 0.075);
		widthRightMenu = (float) (widthUI * 0.25);
		widthBorder = (float) (widthUI * 0.005);

		leftTable.reset();
		leftTable.left().top();
		leftTable.setFillParent(true);
		leftTable.setWidth(widthUI);
		leftTable.setHeight(heightUI);
		leftTable.setX(-widthLeftMenu-widthBorder);
		drawLeftMenu();

		rightTable.reset();
		rightTable.left().top();
		rightTable.setFillParent(true);
		rightTable.setWidth(widthUI);
		rightTable.setHeight(heightUI);
		drawRightMenu();
	}
	
	public void refresh(){
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
		if(selectedNodes.size() == 0){
			hideRightMenu();
		}
		else{
			resize();
		}
	}
	
	public void showHideAndShowButton() {
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("ok-icon-md.png")));
		showLeftMenu = new Image(tr);
		float scale = (float) (widthLeftMenu / showLeftMenu.getWidth());
		showLeftMenu.setWidth(widthLeftMenu/2);
		showLeftMenu.setHeight((showLeftMenu.getHeight()/2) * scale);
		showLeftMenu.setX(7);
		showLeftMenu.setY(heightUI - showLeftMenu.getHeight() - 7);
		showLeftMenu.addListener(new ShowLeftMenuEventListener(showLeftMenu, leftTable));
		
		this.addActor(showLeftMenu);
		
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
		rightTable.add().height(heightUI).width(widthUI - widthRightMenu);
		rightTable.add(rightMenu()).height(heightUI).width(widthRightMenu);
	}

	private Table leftMenu() {
		Table table = new Table();

		float scale = 0.0f;
		
		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("Delete All.png")));
		hideLeftMenu = new Image(tr);
		scale = (float) (widthLeftMenu / hideLeftMenu.getWidth());
		hideLeftMenu.setWidth(widthLeftMenu/2);
		hideLeftMenu.setHeight((hideLeftMenu.getHeight()/2) * scale);
		hideLeftMenu.setX(((widthLeftMenu)/2) - (hideLeftMenu.getWidth()/2));
		hideLeftMenu.setY(heightUI - hideLeftMenu.getHeight() - 7);
		hideLeftMenu.addListener(new ShowLeftMenuEventListener(showLeftMenu, leftTable));
		table.addActor(hideLeftMenu);

		/***		OPEN		***/
		tr = new TextureRegion(skin.getRegion("open"));
		Image openImg = new Image(tr);
		openImg.setName("open");
		scale = (float) (widthLeftMenu / openImg.getWidth());
		openImg.setWidth(widthLeftMenu);
		openImg.setHeight((int) (openImg.getHeight() * scale));
		openImg.setX(0);
		openImg.setY(heightUI - openImg.getHeight() - 14 - hideLeftMenu.getHeight());
		table.addActor(openImg);
		openImg.addListener(eventListener);
		
		/***		CLOSE		***/
		tr = new TextureRegion(skin.getRegion("close"));
		Image closeImg = new Image(tr);
		closeImg.setName("close");
		scale = (float) (widthLeftMenu / closeImg.getWidth());
		closeImg.setWidth(widthLeftMenu);
		closeImg.setHeight((int) (closeImg.getHeight() * scale));
		closeImg.setX(0);
		closeImg.setY(openImg.getY() - closeImg.getHeight() - 20);
		table.addActor(closeImg);
		closeImg.addListener(eventListener);
		
		/***		CENTER		***/
		tr = new TextureRegion(skin.getRegion("center"));
		Image centerImg = new Image(tr);
		centerImg.setName("center");
		scale = (float) (widthLeftMenu / centerImg.getWidth());
		centerImg.setWidth(widthLeftMenu);
		centerImg.setHeight((int) (centerImg.getHeight() * scale));
		centerImg.setX(0);
		centerImg.setY(closeImg.getY() - centerImg.getHeight() - 20);
		table.addActor(centerImg);
		centerImg.addListener(eventListener);
		
		/***		NAME		***/
		//TODO: Add region name
		tr = new TextureRegion(skin.getRegion("center"));
		Image nameImg = new Image(tr);
		nameImg.setName("name");
		scale = (float) (widthLeftMenu / nameImg.getWidth());
		nameImg.setWidth(widthLeftMenu);
		nameImg.setHeight((int) (nameImg.getHeight() * scale));
		nameImg.setX(0);
		nameImg.setY(centerImg.getY() - nameImg.getHeight() - 20);
		table.addActor(nameImg);
		nameImg.addListener(eventListener);
		
		/***		ABOUT		***/
		tr = new TextureRegion(skin.getRegion("about"));
		Image aboutImg = new Image(tr);
		aboutImg.setName("about");
		scale = (float) (widthLeftMenu / aboutImg.getWidth());
		aboutImg.setWidth(widthLeftMenu);
		aboutImg.setHeight((int) (aboutImg.getHeight() * scale));
		aboutImg.setX(0);
		aboutImg.setY(nameImg.getY() - aboutImg.getHeight() - 20);
		table.addActor(aboutImg);
		aboutImg.addListener(eventListener);

		table.setBackground(skin.getDrawable("gray-pixel"));

		return table;
	}

	private Table rightMenu() {
		float currentY = heightUI;
		Table table = new Table();
		List<NodeSprite> selectedNodes = screen.getSelectedNodes();
//		if(selectedNodes.size() == 1){
			TextField nameTF = new TextField("Test", skin);
			nameTF.setWidth(widthRightMenu - 10);
			nameTF.setHeight(nameTF.getWidth() * 0.3f);
			nameTF.setX(5);
			currentY -= nameTF.getHeight() + 5;
			nameTF.setY(currentY);
			nameTF.setTextFieldListener(new NameTextFieldListener(nameTF));
			table.addActor(nameTF);
//		}
//		if(selectedNodes.size() >= 1){	
			TextField colorTF = new TextField("A05E1D", skin);
			colorTF.setWidth(widthRightMenu - 10);
			colorTF.setHeight(colorTF.getWidth() * 0.3f);
			colorTF.setX(5);
			currentY -= colorTF.getHeight() + 5;
			colorTF.setY(currentY);
			colorTF.setTextFieldListener(new ColorTextFieldListener(colorTF));
			table.addActor(colorTF);
//		}
		
		table.setBackground(skin.getDrawable("gray-pixel"));
		return table;
		
	}

	private Table border() {
		Table table = new Table();
		table.setBackground(skin.getDrawable("black-pixel"));
		return table;
	}
	
	public void dispose() {
		skin.dispose();
	}
}
