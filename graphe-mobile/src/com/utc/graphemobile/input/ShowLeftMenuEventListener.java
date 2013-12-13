package com.utc.graphemobile.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class ShowLeftMenuEventListener extends ActorGestureListener{
	static public boolean isLeftMenuHidden = true;
	
	private Image imageShowMenu;
	private Table leftMenu;
	
	public ShowLeftMenuEventListener(Image imageShowMenu, Table table) {
		this.imageShowMenu = imageShowMenu;
		leftMenu = table;
	}
	
	@Override
	public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
		if(isLeftMenuHidden == true) {
			isLeftMenuHidden = false;
			leftMenu.addAction(Actions.moveTo(0, 0, 0.7f, Interpolation.fade));
			imageShowMenu.setVisible(false);
		} else {
			isLeftMenuHidden = true;
			leftMenu.addAction(Actions.moveTo(-leftMenu.getWidth(), 0, 0.7f, Interpolation.fade));
			imageShowMenu.addAction(Actions.delay(0.5f, Actions.show()));
			//imageShowMenu.setVisible(true);
			
		}
	}
}
