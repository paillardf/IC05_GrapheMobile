package com.utc.graphemobile.input;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.element.LeftMenu;
import com.utc.graphemobile.utils.Utils;

public class ShowLeftMenuEventListener extends ActorGestureListener {
	static public boolean isLeftMenuHidden = true;

	private LeftMenu leftMenu;

	public ShowLeftMenuEventListener(LeftMenu menu) {
		leftMenu = menu;
	}

	@Override
	public void touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		if (isLeftMenuHidden == false) {
			isLeftMenuHidden = true;
			leftMenu.addAction(Actions.moveTo(-Utils.toDp(LeftMenu.WIDTH), 0,
					0.7f, Interpolation.fade));
		} else {
			isLeftMenuHidden = false;
			leftMenu.addAction(Actions.moveTo(0, 0, 0.7f, Interpolation.fade));
		}
	}
}
