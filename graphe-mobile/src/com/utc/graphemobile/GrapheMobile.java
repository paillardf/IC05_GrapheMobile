package com.utc.graphemobile;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.utc.graphemobile.screen.GrapheScreen;
import com.utc.graphemobile.specific.SpecificInterface;

public class GrapheMobile extends Game {
	private BitmapFont font;
	private SpecificInterface mInterface;

	public GrapheMobile(SpecificInterface mInterface) {
		this.mInterface = mInterface;
	}

	@Override
	public void create() {

		// Use LibGDX's default Arial font.
		font = new BitmapFont();

		try {
			this.setScreen(new GrapheScreen(this));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void dispose() {
		super.dispose();
		font.dispose();
	}

	@Override
	public void render() {
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}

	public void openGraphe(File file) {
		try {
			((GrapheScreen) getScreen()).loadGraphe(new FileHandle(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	public SpecificInterface getOsInterface() {
		return mInterface;
		
	}
}
