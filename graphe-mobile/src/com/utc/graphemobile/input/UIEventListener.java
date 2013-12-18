package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
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

		} else if (this.getTouchDownTarget().getName().equals("close")) {

		} else if (this.getTouchDownTarget().getName().equals("center")) {
			screen.iniCameraPos();
		} else if (this.getTouchDownTarget().getName().equals("label")) {
			isVisible = !isVisible;
			screen.showLabel(isVisible);
		} else if (this.getTouchDownTarget().getName().equals("about")) {
			screen.showAbout(true);
		} else if (this.getTouchDownTarget().getName().equals("aboutClose")) {
			screen.showAbout(true);
		} else if (this.getTouchDownTarget().getName().equals("unselect")) {
			screen.clearSelection();
		}
	}

}
