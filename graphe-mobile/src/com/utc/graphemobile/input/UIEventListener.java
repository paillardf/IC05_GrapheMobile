package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.screen.GrapheScreen;
import com.utc.graphemobile.stage.GrapheStage;

public class UIEventListener extends ActorGestureListener{

	private int i = 0;
	
	public UIEventListener() {
		
	}
	
	@Override
	public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
		System.out.println(this.getTouchDownTarget().getName());
		if(this.getTouchDownTarget() instanceof Image) {
			if(this.getTouchDownTarget().getName().equals("open")) {
				
			} else if(this.getTouchDownTarget().getName().equals("close")) {
				
			} else if(this.getTouchDownTarget().getName().equals("center")) {				
				GrapheScreen.getGrapheStage().getCamera().position.set(0, 0, 0);
			} else if(this.getTouchDownTarget().getName().equals("name")) {
				
			} else if(this.getTouchDownTarget().getName().equals("about")) {
				
			}
		}
	}
}
