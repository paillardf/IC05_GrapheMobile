package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class UIEventListener extends ActorGestureListener{

	private int i = 0;
	
	public UIEventListener() {
		
	}
	
	@Override
	public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
		System.out.println("CLICK"+i+"  info(x:"+x+",y:"+y+",pointer:"+pointer+",button:"+button+")");
		i++;
	}
}
