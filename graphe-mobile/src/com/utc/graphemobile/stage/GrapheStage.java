package com.utc.graphemobile.stage;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.utc.graphemobile.element.EdgeSprite;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.input.GrapheGestureListener;
import com.utc.graphemobile.screen.IGrapheScreen;

public class GrapheStage extends Stage {

	private GrapheGestureListener gestureListener;
	private IGrapheScreen screen;
	private TextureRegion regionCircle;

	public GrapheStage(IGrapheScreen mScreen) {
		super();
		this.screen = mScreen;

		gestureListener = new GrapheGestureListener(
				(OrthographicCamera) getCamera(), screen);
		regionCircle = mScreen.getSkin().getRegion("circle");

		loadObjects();

		// screen.setMode(MODE.EDIT);//TODO remove me
	}

	@Override
	public boolean scrolled(int amount) {
		((OrthographicCamera) getCamera()).zoom += amount / 4.0f;
		if (((OrthographicCamera) getCamera()).zoom < 0)
			((OrthographicCamera) getCamera()).zoom = 0.001f;
		return false;
		// super.scrolled(amount);
	};

	public void loadObjects() {
		if (screen.getGraph() == null)
			return;
		getActors().clear();

		ShapeRenderer shapeRenderer = new ShapeRenderer();
		for (Edge e : screen.getGraph().getEdges()) {
			EdgeSprite eSprite = new EdgeSprite(e, shapeRenderer, screen);
			this.addActor(eSprite);
		}

		for (Node n : screen.getGraph().getNodes()) {
			NodeSprite nSprite = new NodeSprite(n, regionCircle, screen);
			this.addActor(nSprite);
		}
	}

	public GestureListener getGestureListener() {
		return gestureListener;
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
