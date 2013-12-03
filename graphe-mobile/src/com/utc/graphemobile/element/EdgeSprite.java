package com.utc.graphemobile.element;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class EdgeSprite extends Actor {

	ShapeRenderer shapeRenderer;
	private Edge edgeModel;

	public EdgeSprite(Edge e, ShapeRenderer shapeRenderer2) {
		this.edgeModel = e;
		shapeRenderer = shapeRenderer2;

	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		Node source = edgeModel.getSource();
		setPosition(source.getNodeData().x(), source.getNodeData().y());
		setColor(edgeModel.getEdgeData().r(), edgeModel.getEdgeData().g(),
				edgeModel.getEdgeData().b(), edgeModel.getEdgeData().alpha());
		drawLine(batch);
	}

	public void drawLine(SpriteBatch batch) {
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.Line);
		Gdx.gl10.glLineWidth(edgeModel.getWeight());
		shapeRenderer.setColor(getColor()); // last argument is alpha channel
		Node target = edgeModel.getTarget();
		shapeRenderer.line(getX(), getY(), target.getNodeData().x(), target
				.getNodeData().y());
		shapeRenderer.end();

	}

}
