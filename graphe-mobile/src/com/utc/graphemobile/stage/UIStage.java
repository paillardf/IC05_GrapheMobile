package com.utc.graphemobile.stage;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.UIGestureListener;

public class UIStage extends Stage{
	
	private UIGestureListener gestureListener;
	private Table table;
	
	public UIStage() {
		super();
		
		table = new Table();
		table.setFillParent(true);
		
		Skin skin1 = new Skin();
		
		// Store the default font under the name "default" in the skin
		skin1.add("default", new BitmapFont());
		
		// Configure the default LabelStyle
		LabelStyle labelStyle1 = new LabelStyle();
		labelStyle1.font = skin1.getFont("default");
		labelStyle1.fontColor = Color.GRAY;
		skin1.add("default", labelStyle1);
		/* It doesn't overwrite the font but add the labelStyle properties to the "label" default of this skin*/
		
		Skin skin2 = new Skin();
		LabelStyle labelStyle2 = new LabelStyle();
		skin2.add("default", new BitmapFont());		
		labelStyle2.font = skin2.getFont("default");
		labelStyle2.fontColor = Color.RED;
		skin2.add("default", labelStyle2);
		
		table.add(new Label("test 1", skin1));
		table.add(new Label("test 2", skin2));
		table.row();
		table.add(new Label("test 3", skin2));
		table.add(new Label("test 4", skin1));
		this.addActor(table);
		
		gestureListener = new UIGestureListener();
	}

	public UIGestureListener getGestureListener() {
		return gestureListener;
	}

	public void setGestureListener(UIGestureListener gestureListener) {
		this.gestureListener = gestureListener;
	}

	
}
