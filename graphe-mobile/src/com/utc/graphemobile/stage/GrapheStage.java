package com.utc.graphemobile.stage;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.Node;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.input.GrapheGestureListener;

public class GrapheStage extends Stage{

	private GrapheGestureListener gestureListener;
	private DirectedGraph graph;

	public GrapheStage(DirectedGraph graph) {
		super();
		this.graph = graph;
		
		gestureListener = new GrapheGestureListener((OrthographicCamera) getCamera());
		
		attachObject();
	}

	private void attachObject() {
		
		for(Node n : graph.getNodes()) {
			NodeSprite nSprite = new NodeSprite(n);
			this.addActor(nSprite);
		}
		
		
		
	}

	public GestureListener getGestureListener() {
		return gestureListener;
	}
	
	
}
