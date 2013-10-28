package com.utc.graphemobile.input;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;

public class GrapheGestureListener implements GestureListener {

	private OrthographicCamera camera;
	private float iniZoomCamera = 0;
	

	public GrapheGestureListener(OrthographicCamera camera) {
		this.camera = camera;
	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		iniZoomCamera = camera.zoom;
		//iniAngleCamera = camera.rotate(angle)

		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		//camera.lookAt(x, y, 0);
		camera.translate(-deltaX, deltaY);
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {

		camera.zoom = iniZoomCamera * (initialDistance / distance);
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		// TODO Auto-generated method stub
		//System.out.println(pointer1.sub(pointer2).angle());
		//camera.rotate(pointer1.sub(pointer2).angle());
		return false;
	}

}
