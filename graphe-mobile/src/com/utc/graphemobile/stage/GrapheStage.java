package com.utc.graphemobile.stage;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.utc.graphemobile.element.Node;
import com.utc.graphemobile.input.GrapheGestureListener;

public class GrapheStage extends Stage{

	private GrapheGestureListener gestureListener;

	public GrapheStage() {
		super();
		gestureListener = new GrapheGestureListener((OrthographicCamera) getCamera());
		
		attachObject();
	}

	private void attachObject() {

		Node n = new Node();
		this.addActor(n);
		
	}

	public GestureListener getGestureListener() {
		return gestureListener;
	}
	
	
}
