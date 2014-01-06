package com.utc.graphemobile.utils;

import com.badlogic.gdx.Gdx;

public class Utils {

	public static float toPixel(float dp) {
		float px = dp / (Gdx.graphics.getPpiX() / 160f);
		return px;
	}

	public static float toDp(float px) {
		float dp = px * (Gdx.graphics.getPpiX() / 160f);
		return dp;
	}
}
