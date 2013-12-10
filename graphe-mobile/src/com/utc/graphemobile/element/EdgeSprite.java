package com.utc.graphemobile.element;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
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
		shapeRenderer.begin(ShapeType.Curve);
		Gdx.gl10.glLineWidth(edgeModel.getWeight()*2);
		
		shapeRenderer.setColor(getColor()); // last argument is alpha channel
		Node target = edgeModel.getTarget();
//		shapeRenderer.line(getX(), getY(), target.getNodeData().x(), target
//				.getNodeData().y());
	
		Vector2 pos = new Vector2(getX(), getY());
		Vector2 dest = new Vector2(target.getNodeData().x(), target
				.getNodeData().y());
		Vector2 vect = dest.cpy().sub(pos);
		Vector2 norm = vect.cpy().rotate(90);
		//System.out.println(vect);
		Vector2 pos1 = vect.cpy().div(3).add(norm.cpy().div(3)).add(pos);
		Vector2 pos2 = vect.cpy().rotate(180).div(3).add(norm.cpy().div(3)).add(dest);//new Vector2(0,0);//dest.sub(vect.div(2));
		//shapeRenderer.line(pos.x, pos.y, pos1.x, pos1.y);
				
		shapeRenderer.curve(getX(), getY(),pos1.x, pos1.y, pos2.x, pos2.y,
				target.getNodeData().x(), target.getNodeData().y());
		shapeRenderer.end();
	}

}
