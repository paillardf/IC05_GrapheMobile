package com.utc.graphemobile.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class hideAndShowLeftMenuButton extends Image{
	private boolean leftMenuHidden;
	private TextureRegion trHide;
	private TextureRegion trShow;
	private float x;
	private float y;
	private float w;
	private float h;
	
	public hideAndShowLeftMenuButton(Table table, float posX, float posY, float width, float height, boolean isLeftMenuHidden) {
		x = posX;
		y = posY;
		w = width;
		h = height;
		
		leftMenuHidden = isLeftMenuHidden;
		
		trHide = new TextureRegion(new Texture(Gdx.files.internal("Delete All")));
		trShow = new TextureRegion(new Texture(Gdx.files.internal("ok-icon-md.png")));
		
		if(leftMenuHidden == true) {
			//On veut afficher le menu
			
			
		}
	}
}
