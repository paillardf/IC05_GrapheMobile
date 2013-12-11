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
	private Image hideAndShowLeftMenu;
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
		hideAndShowLeftMenu = new Image(tr);
		float scale = (float) (widthLeftMenu / hideAndShowLeftMenu.getWidth());
		hideAndShowLeftMenu.setWidth(widthLeftMenu/2);
		hideAndShowLeftMenu.setHeight((int) ((hideAndShowLeftMenu.getHeight()/2) * scale));
		hideAndShowLeftMenu.setX(7);
		hideAndShowLeftMenu.setY(heightUI - hideAndShowLeftMenu.getHeight() - 7);
		
		this.addActor(hideAndShowLeftMenu);
		
		hideAndShowLeftMenu.addListener(new ShowLeftMenuEventListener(hideAndShowLeftMenu, leftTable, heightUI));
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

		/***		OPEN		***/
		TextureRegion tr = new TextureRegion(skin.getRegion("open"));
		Image image1 = new Image(tr);
		scale = (float) (widthLeftMenu / image1.getWidth());
		image1.setWidth(widthLeftMenu);
		image1.setHeight((int) (image1.getHeight() * scale));
		image1.setX(0);
		image1.setY(heightUI - image1.getHeight());
		table.addActor(image1);
		image1.addListener(eventListener);
		
		/***		CENTER		***/
		tr = new TextureRegion(skin.getRegion("center"));
		Image image2 = new Image(tr);
		scale = (float) (widthLeftMenu / image2.getWidth());
		image2.setWidth(widthLeftMenu);
		image2.setHeight((int) (image2.getHeight() * scale));
		image2.setX(0);
		image2.setY(image1.getY() - image2.getHeight() - 20);
		table.addActor(image2);
		image2.addListener(eventListener);
		
		/***		CENTER		***/
		tr = new TextureRegion(new Texture(Gdx.files.internal("ok-icon-md.png")));
		Image image3 = new Image(tr);
		scale = (float) (widthLeftMenu / image3.getWidth());
		image3.setWidth(widthLeftMenu);
		image3.setHeight((int) (image3.getHeight() * scale));
		image3.setX(0);
		image3.setY(image2.getY() - image3.getHeight() - 20);
		table.addActor(image3);
		image3.addListener(eventListener);

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
