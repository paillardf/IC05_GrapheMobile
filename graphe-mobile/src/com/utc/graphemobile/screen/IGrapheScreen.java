package com.utc.graphemobile.screen;

import java.util.List;

import org.gephi.graph.api.HierarchicalGraph;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.screen.GrapheScreen.MODE;

public interface IGrapheScreen{
	
	public MODE getMode();
	
	public List<NodeSprite> getSelectedNodes();

	void setMode(MODE mode);
	
	public BitmapFont getFont();

	public HierarchicalGraph getGraph();
}