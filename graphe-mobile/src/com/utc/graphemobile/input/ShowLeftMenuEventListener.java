package com.utc.graphemobile.input;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.AddAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class ShowLeftMenuEventListener extends ActorGestureListener{

	private Image thisImage;
	private Table leftMenu;
	private float posY;
	
	public ShowLeftMenuEventListener(Image im, Table table, float y) {
		thisImage = im;
		leftMenu = table;
		posY = y;
	}
	
	@Override
	public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
		thisImage.setVisible(false);
		
		leftMenu.addAction(Actions.moveTo(0, 0, 0.7f, Interpolation.fade));
	}
}
