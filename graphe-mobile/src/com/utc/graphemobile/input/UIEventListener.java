package com.utc.graphemobile.input;

import org.gephi.graph.api.Node;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.screen.GrapheScreen;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.stage.GrapheStage;

public class UIEventListener extends ActorGestureListener{

	private GrapheScreen screen;
	
	public UIEventListener(GrapheScreen mScreen) {
		this.screen = mScreen;
	}
	
	@Override
	public void touchDown (InputEvent event, float x, float y, int pointer, int button) {
		System.out.println(this.getTouchDownTarget().getName());
		if(this.getTouchDownTarget() instanceof Image) {
			if(this.getTouchDownTarget().getName().equals("open")) {
				
			} else if(this.getTouchDownTarget().getName().equals("close")) {
				
			} else if(this.getTouchDownTarget().getName().equals("center")) {				
				screen.getGrapheStage().getCamera().position.set(0, 0, 0);
			} else if(this.getTouchDownTarget().getName().equals("name")) {
				if(screen.getNodeLabelsVisible()) {
					for(Node n : screen.getGraph().getNodes()) {
						n.getNodeData().setLabel("");
					}
					screen.setNodeLabelsVisible(false);
				} else {
					int it = 0;
					for(Node n : screen.getGraph().getNodes()) {
						n.getNodeData().setLabel(screen.getNodeLabels().get(it++));
					}
					screen.setNodeLabelsVisible(true);
				}
				
			} else if(this.getTouchDownTarget().getName().equals("about")) {
				
			}
		}
	}
}
