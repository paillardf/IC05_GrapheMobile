package com.utc.graphemobile;

import javax.swing.JFileChooser;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.utc.graphemobile.specific.SpecificInterface;

public class Main {
	private static GrapheMobile graphe;

	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "graphe-mobile";
		cfg.useGL20 = false;
		cfg.width = 900;
		cfg.height = 500;
		cfg.addIcon("data/ic_launcher.png", Files.FileType.Internal);
		cfg.title = "Graphe Mobile";
		graphe = new GrapheMobile(new SpecificInterface() {
			
			@Override
			public void openFileChooser() {
				JFileChooser choix = new JFileChooser();
				int retour=choix.showOpenDialog(null);
				if(retour==JFileChooser.APPROVE_OPTION){
				   // un fichier a été choisi (sortie par OK)
				   // nom du fichier  choisi 
					graphe.openGraphe(choix.getSelectedFile());
				}
			}
		});
		new LwjglApplication(graphe, cfg);
	}
}
