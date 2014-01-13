package com.utc.graphemobile.element;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.Node;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.utc.graphemobile.screen.IGrapheScreen;

public class EdgeSprite extends Actor {

	ShapeRenderer shapeRenderer;
	public Edge edgeModel;
	private IGrapheScreen screen;

	
	public EdgeSprite(Edge e, ShapeRenderer shapeRenderer, IGrapheScreen mScreen) {
		this.edgeModel = e;
		this.screen = mScreen;
		this.shapeRenderer = shapeRenderer;
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		Node source = edgeModel.getSource();
		setPosition(source.getNodeData().x(), source.getNodeData().y());
		
		if((!(Boolean)source.getNodeData().getAttributes().getValue("visible"))&&
				(!(Boolean)edgeModel.getTarget().getNodeData().getAttributes().getValue("visible"))){
			return;
		}
		setColor(edgeModel.getEdgeData().r(), edgeModel.getEdgeData().g(),
				edgeModel.getEdgeData().b(), edgeModel.getEdgeData().alpha());
		
		drawLine(batch);
	}

	public void drawLine(SpriteBatch batch) {
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		Gdx.gl10.glLineWidth(edgeModel.getWeight() * 2);
		
		shapeRenderer.setColor(getColor()); // last argument is alpha channel
		Node target = edgeModel.getTarget();

		Vector2 pos = new Vector2(getX(), getY());
		Vector2 dest = new Vector2(target.getNodeData().x(), target.getNodeData().y());
		
		if(!screen.isCurve()) {
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.line(pos.x, pos.y, dest.x, dest.y);
		} else {
			Vector2 vect = dest.cpy().sub(pos);
			Vector2 norm = vect.cpy().rotate(90);
			// System.out.println(vect);
			Vector2 pos1 = vect.cpy().div(3).add(norm.cpy().div(3)).add(pos);
			Vector2 pos2 = vect.cpy().rotate(180).div(3).add(norm.cpy().div(3))
					.add(dest);// new Vector2(0,0);//dest.sub(vect.div(2));
			shapeRenderer.begin(ShapeType.Curve);
			shapeRenderer.curve(getX(), getY(), pos1.x, pos1.y, pos2.x, pos2.y, dest.x, dest.y);
		}
		shapeRenderer.end();
	}
}
