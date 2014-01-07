package com.utc.graphemobile.element;

import java.util.List;

import org.gephi.graph.api.Node;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.screen.GrapheScreen.MODE;
import com.utc.graphemobile.screen.IGrapheScreen;

public class NodeSprite extends Actor {

	ShapeRenderer shapeRenderer;
	private Node nodeModel;

	private IGrapheScreen screen;

	private boolean selected = false;

	private TextureRegion textureRegion;

	public NodeSprite(Node n, TextureRegion regionCircle, IGrapheScreen mScreen) {
		this.nodeModel = n;
		nodeModel.getNodeData().getAttributes().setValue("visible", true);
		this.screen = mScreen;
		this.textureRegion = regionCircle;
		this.setOrigin(0, 0);

		ActorGestureListener mActorGestureListener = new ActorGestureListener() {

			@Override
			public boolean handle(Event e) {
				switch (screen.getMode()) {
				case EDIT:
					return super.handle(e);
				default:
					return false;
				}

			};

			@Override
			public boolean longPress(Actor actor, float x, float y) {
				switch (screen.getMode()) {
				case EDIT:
					List<NodeSprite> selectedNodes = screen.getSelectedNodes();
					if (!selected) {
						selectedNodes.add(NodeSprite.this);
					} else {
						selectedNodes.remove(NodeSprite.this);
					}
					setSelected(!selected);
					screen.updateSelectedNodesList();
					return true;

				default:
					return false;
				}
			}

			@Override
			public void pan(InputEvent event, float x, float y, float deltaX,
					float deltaY) {
				if (selected) {
					List<NodeSprite> selectedNodes = screen.getSelectedNodes();
					for (NodeSprite nodeSprite : selectedNodes) {
						nodeSprite.nodeModel.getNodeData()
								.setX(nodeSprite.nodeModel.getNodeData().x()
										+ deltaX);
						nodeSprite.nodeModel.getNodeData()
								.setY(nodeSprite.nodeModel.getNodeData().y()
										+ deltaY);
					}
				}
			}

			public void fling(InputEvent event, float velocityX,
					float velocityY, int button) {

			}

			public void zoom(InputEvent event, float initialDistance,
					float distance) {
			}
		};
		mActorGestureListener.getGestureDetector().setLongPressSeconds(0.5f);
		addListener(mActorGestureListener);
	}

	public Node getNodeModel() {
		return nodeModel;
	}

	private final int MARGING = 30;
	public void draw(SpriteBatch batch, float parentAlpha) {
		if (selected && screen.getMode() == MODE.NORMAL) {
			selected = false;
			clearActions();
		}
		
		
		OrthographicCamera cam = (OrthographicCamera)screen.getGrapheStage().getCamera();
		float h = cam.viewportHeight*cam.zoom;
		float w = cam.viewportWidth*cam.zoom;
		Vector3 pos = cam.position;
		float x = pos.x - w/2.0f;
		float y = pos.y - h/2.0f;
		float maxx = pos.x+w/2.0f;
		float maxy= pos.y+h/2.0f;
		setPosition(nodeModel.getNodeData().x(), nodeModel.getNodeData().y());
		if(getX()+MARGING<=x||getX()-MARGING>maxx||getY()+MARGING<=y||getY()-MARGING>maxy){
			
			nodeModel.getNodeData().getAttributes().setValue("visible", false);
			return;
		}else{
			nodeModel.getNodeData().getAttributes().setValue("visible", true);
		}
		
		

		
		setColor(nodeModel.getNodeData().r(), nodeModel.getNodeData().g(),
				nodeModel.getNodeData().b(), nodeModel.getNodeData().alpha());

		drawCircle(batch);
		if (screen.isLabelVisible()) {
			screen.getFont().setColor(Color.BLACK);
			screen.getFont().setScale(
					getRadius() / textureRegion.getRegionWidth() * 2);
			screen.getFont().drawMultiLine(batch,
					nodeModel.getNodeData().getLabel(), getX(), getY() + 5, 0,
					HAlignment.CENTER);
		}
	}

	@Override
	public Actor hit(float x, float y, boolean touchable) {
		if (touchable && this.getTouchable() != Touchable.enabled)
			return null;
		Vector2.tmp2.x = 0;
		Vector2.tmp2.y = 0;

		return Vector2.tmp2.dst(x, y) < getRadius() ? this : null;

	};

	public void drawCircle(SpriteBatch batch) {

		batch.setColor(getColor());
		batch.draw(textureRegion, getX() - getRadius() * getScaleX(), getY()
				- getRadius() * getScaleY(), 0, 0, getRadius() * 2,
				getRadius() * 2, getScaleX(), getScaleY(), 0);

		// shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
		// shapeRenderer.begin(ShapeType.FilledCircle);
		// shapeRenderer.setColor(getColor()); // last argument is alpha channel
		// shapeRenderer.filledCircle(getX(), getY(), getRadius());
		// shapeRenderer.end();
		// System.out.println(getScaleX());

	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			addAction(Actions.forever(Actions.sequence(
					Actions.scaleTo(1.1f, 1.1f, 0.5f),
					Actions.scaleTo(1, 1, 0.5f))));
		} else {
			clearActions();
		}
	}

	public float getRadius() {
		return nodeModel.getNodeData().getRadius();
	}
}
