package com.utc.graphemobile.element;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;

public class Node extends Actor {

	ShapeRenderer shapeRenderer;
	private final static float RADIUS = 200;

	public Node() {
		shapeRenderer = new ShapeRenderer();
		addListener(new ActorGestureListener() {
	        public boolean longPress (Actor actor, float x, float y) {
	                System.out.println("long press " + x + ", " + y);
	                return true;
	        }

	        public void fling (InputEvent event, float velocityX, float velocityY, int button) {
	        }

	        public void zoom (InputEvent event, float initialDistance, float distance) {
	        }
	});
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		
		drawCircle(batch);
	}
	
	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && this.getTouchable() != Touchable.enabled) return null;
		Vector2 center = new Vector2(getX(), getY());
		return center.dst(x, y)<RADIUS ? this : null;
		
	};
	

	public void drawCircle(SpriteBatch batch) {
		shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		shapeRenderer.begin(ShapeType.FilledCircle);
		shapeRenderer.setColor(getColor()); // last argument is alpha channel
		shapeRenderer.filledCircle(getX(), getY(), RADIUS);
		shapeRenderer.end();

	}

}
