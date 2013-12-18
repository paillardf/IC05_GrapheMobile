package com.utc.graphemobile.screen;

import java.util.List;

import org.gephi.graph.api.HierarchicalGraph;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.screen.GrapheScreen.MODE;
import com.utc.graphemobile.specific.SpecificInterface;

public interface IGrapheScreen {

	public MODE getMode();

	public void setMode(MODE mode);

	public List<NodeSprite> getSelectedNodes();

	public void updateSelectedNodesList();

	public Skin getSkin();

	public BitmapFont getFont();

	public HierarchicalGraph getGraph();

	public void iniCameraPos();

	public void showLabel(boolean isVisible);

	public boolean isLabelVisible();

	public void showAbout(boolean b);

	public void clearSelection();

	public SpecificInterface getOsInterface();
}