package com.utc.graphemobile.element;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ColorVisualisation extends Actor {
	
	private TextureRegion textureRegion;

	public ColorVisualisation(TextureRegion regionCircle) {
		this.textureRegion = regionCircle;
		this.setOrigin(0, 0);
	}

	public void draw(SpriteBatch batch, float parentAlpha) {
		batch.setColor(getColor());
		batch.draw(textureRegion, getX(), getY(), 0, 0, getWidth(),
				getHeight(), getScaleX(), getScaleY(), 0);
	}
}
