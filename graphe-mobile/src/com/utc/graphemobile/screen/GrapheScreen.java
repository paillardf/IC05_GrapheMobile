package com.utc.graphemobile.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.utc.graphemobile.stage.GrapheStage;
import com.utc.graphemobile.stage.UIStage;

public class GrapheScreen implements Screen {

	private GrapheStage grapheStage;
	private UIStage uiStage;

	public GrapheScreen() {
		grapheStage = new GrapheStage();
		uiStage = new UIStage();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(grapheStage);
		multiplexer.addProcessor(new GestureDetector(grapheStage.getGestureListener()));
		Gdx.input.setInputProcessor(multiplexer);
		System.out.println("ok");
	}
	
	
	
	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		grapheStage.act(delta);
		grapheStage.draw();
		uiStage.act(delta);
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		grapheStage.setViewport(width, height, true);
		uiStage.setViewport(width, height, true);
		
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		grapheStage.dispose();
		uiStage.dispose();
		
	}

}
