package com.utc.graphemobile;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.utc.graphemobile.specific.FileChooser;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "graphe-mobile";
		cfg.useGL20 = false;
		cfg.width = 480;
		cfg.height = 320;
		
		new LwjglApplication(new GrapheMobile(new FileChooser() {
			
			@Override
			public void openFileChooser() {
				// TODO Auto-generated method stub
				
			}
		}), cfg);
	}
}
