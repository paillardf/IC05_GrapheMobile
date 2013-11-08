package com.utc.graphemobile;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.utc.graphemobile.screen.GrapheScreen;

public class GrapheMobile extends Game {

	private BitmapFont font;

	@Override
	public void create() {
		//Use LibGDX's default Arial font.
		font = new BitmapFont();
		
		
		
		try {
			this.setScreen(new GrapheScreen());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
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
