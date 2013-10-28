package com.utc.graphemobile;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.utc.graphemobile.screen.GrapheScreen;

public class GrapheMobile extends Game {

	private BitmapFont font;

	@Override
	public void create() {
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		
		this.setScreen(new GrapheScreen());
		
	}
	
	
	@Override
	public void render() {
		super.render();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		font.dispose();
		

	}
	
	
}
