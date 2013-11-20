package com.utc.graphemobile.stage;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.utc.graphemobile.element.EdgeSprite;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.input.GrapheGestureListener;

public class GrapheStage extends Stage{

	private GrapheGestureListener gestureListener;
	private HierarchicalGraph graph;

	public GrapheStage(HierarchicalGraph graph2) {
		super();
		this.graph = graph2;
		gestureListener = new GrapheGestureListener((OrthographicCamera) getCamera());
		
		
		attachObject();
		
		
	}

	@Override
	public boolean scrolled(int amount) {
		((OrthographicCamera)getCamera()).zoom += amount/4.0f;
		if(((OrthographicCamera)getCamera()).zoom<0)
			((OrthographicCamera)getCamera()).zoom = 0.001f;
		return false;
		//super.scrolled(amount);
	};
	
	private void attachObject() {
		
		ShapeRenderer shapeRenderer = new ShapeRenderer();
		for(Edge e : graph.getEdges()) {
			EdgeSprite eSprite = new EdgeSprite(e, shapeRenderer);
			this.addActor(eSprite);
		}
		
		for(Node n : graph.getNodes()) {
			NodeSprite nSprite = new NodeSprite(n, shapeRenderer);
			this.addActor(nSprite);
		}
		
	}

	public GestureListener getGestureListener() {
		return gestureListener;
	}
	
	
}
