package com.utc.graphemobile.stage;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.utc.graphemobile.element.LeftMenu;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.input.ColorTextFieldListener;
import com.utc.graphemobile.input.NameTextFieldListener;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;

public class UIStage extends Stage {
	
	public IGrapheScreen screen;
	private UIEventListener eventListener;
	private Table rightTable;
	private Table aboutTable;
	private Image hideLeftMenu;
	private Image showLeftMenu;
	private Skin skin;

	private float widthUI = 0.0f;
	private float widthRightMenu = 0.0f;


	private float heightUI = 0.0f;
	private LeftMenu leftMenu;

	public UIStage(IGrapheScreen screen) {
		super();
		this.screen = screen;
		eventListener = new UIEventListener(screen);
		skin = new Skin(Gdx.files.internal("data/style.json"),
				new TextureAtlas("data/style.atlas"));
		
		leftMenu = new LeftMenu(this);
		this.addActor(leftMenu);
		
		
		rightTable = new Table();
		aboutTable = new Table();

		resize();
		drawRightMenu();
		drawAbout();
	}

	public void resize() {
		leftMenu.onResize();
		
		widthUI = this.getWidth();
		heightUI = this.getHeight();

		widthRightMenu = (float) (widthUI * 0.25);

//		leftTable.reset();
//		leftTable.left().top();
//		leftTable.setFillParent(true);
//		leftTable.setWidth(widthUI);
//		leftTable.setHeight(heightUI);
//		leftTable.setX(-widthLeftMenu-widthBorder);
//		drawLeftMenu();

		rightTable.reset();
		rightTable.left().top();
		rightTable.setFillParent(true);
		rightTable.setWidth(widthUI);
		rightTable.setHeight(heightUI);
		drawRightMenu();
		
		aboutTable.reset();
		aboutTable.setFillParent(true);
		rightTable.setWidth(widthUI);
		rightTable.setHeight(heightUI);
		drawAbout();
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
	


	

	public void showRightMenu() {
		this.addActor(rightTable);
	}
	
	public void showAbout() {
		this.addActor(aboutTable);
	}

	
	public void hideRightMenu() {
		rightTable.remove();
	}
	
	public void hideAbout() {
		aboutTable.remove();
	}

	

	private void drawRightMenu() {
		// Right Menu
		rightTable.add().height(heightUI).width(widthUI - widthRightMenu);
		rightTable.add(rightMenu()).height(heightUI).width(widthRightMenu);
	}
	
	private void drawAbout() {
		aboutTable.add(about()).height(heightUI).width(widthUI);
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
	
	private Table about() {
		Table table = new Table();
		table.setBackground(skin.getDrawable("gray-pixel"));

		TextureRegion tr = new TextureRegion(new Texture(Gdx.files.internal("Delete All.png")));
		Image closeBtn = new Image(tr);
		closeBtn.setName("aboutClose");
		float scale = (float) (leftMenu.getWidth() / closeBtn.getWidth());
		closeBtn.setWidth(leftMenu.getWidth()/2);
		closeBtn.setHeight((closeBtn.getHeight()/2) * scale);
		closeBtn.setX(widthUI - closeBtn.getWidth() - 10);
		closeBtn.setY(heightUI - closeBtn.getHeight() - 10);
		closeBtn.addListener(new UIEventListener(this.screen));
		table.addActor(closeBtn);
		
		return table;
	}

//	private Table border() {
//		Table table = new Table();
//		table.setBackground(skin.getDrawable("black-pixel"));
//		return table;
//	}
	
	public void dispose() {
		skin.dispose();
	}
	
	
	
	public Table getRightTable() {
		return rightTable;
	}
	
	public Table getAbout() {
		return aboutTable;
	}
	
	public Skin getSkin() {
		return skin;
	}
}
