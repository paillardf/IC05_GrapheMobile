package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.screen.GrapheScreen.MODE;
import com.utc.graphemobile.screen.IGrapheScreen;

public class UIEventListener extends ActorGestureListener {

	private IGrapheScreen screen;
	private boolean isVisible = false;

	public UIEventListener(IGrapheScreen mScreen) {
		this.screen = mScreen;
	}

	@Override
	public void touchDown(InputEvent event, float x, float y, int pointer,
			int button) {
		System.out.println(this.getTouchDownTarget().getName());
		if (this.getTouchDownTarget().getName() == null)
			return;
		if (this.getTouchDownTarget().getName().equals("open")) {
			screen.getOsInterface().openFileChooser();
		} else if (this.getTouchDownTarget().getName().equals("center")) {
			screen.iniCameraPos();
		} else if (this.getTouchDownTarget().getName().equals("spatial")) {
			// TODO
		} else if (this.getTouchDownTarget().getName().equals("label")) {
			isVisible = !isVisible;
			screen.showLabel(isVisible);
		} else if (this.getTouchDownTarget().getName().equals("about")) {
			screen.showAbout(true);
		} else if (this.getTouchDownTarget().getName().equals("aboutClose")) {
			screen.showAbout(false);
		} else if (this.getTouchDownTarget().getName().equals("unselect")) {
			screen.clearSelection();
		} else if (this.getTouchDownTarget().getName().equals("edge")) {
			screen.setIsCurve(!screen.isCurve());
		} else if (this.getTouchDownTarget().getName().equals("edit")) {
			screen.setMode((screen.getMode() == MODE.EDIT) ? MODE.NORMAL : MODE.EDIT);
		}
	}

}
