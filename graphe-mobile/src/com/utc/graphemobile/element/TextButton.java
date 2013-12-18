package com.utc.graphemobile.element;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.utc.graphemobile.utils.Utils;

public class TextButton extends Group {

	private final int size = 20;
	private final int margin = 5;
	private TextureRegion tr;

	public TextButton(String name, String title, TextureRegion imageTR, Skin s) {
		this.tr = imageTR;
		this.setName(name);
		Label l = new Label(title, s);
		l.setName(name);
		l.setPosition(Utils.toDp(margin + size), 0);
		this.addActor(l);
		Image image = new Image(tr);
		image.setWidth(Utils.toDp(size));
		image.setHeight(Utils.toDp(size));
		image.setX(0);
		image.setY(0);
		image.setName(name);
		this.addActor(image);
		this.setWidth(Utils.toDp(LeftMenu.WIDTH));
		this.setHeight(Utils.toDp(size));
	}
}
