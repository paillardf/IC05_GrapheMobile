package com.utc.graphemobile.input;

import com.badlogic.gdx.input.GestureDetector.GestureListener;
import com.badlogic.gdx.math.Vector2;


public class UIGestureListener implements GestureListener{

	public UIGestureListener() {

	}

	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		System.out.println("TOUCHDOWN");
		return false;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		System.out.println("TAP");
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		System.out.println("LONGPRESS");
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		System.out.println("FLING");
		return false;
	}

	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		System.out.println("PAN");
		return false;
	}

	@Override
	public boolean zoom(float initialDistance, float distance) {
		System.out.println("ZOOM");
		return false;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
			Vector2 pointer1, Vector2 pointer2) {
		System.out.println("PINCH");
		return false;
	}
}
