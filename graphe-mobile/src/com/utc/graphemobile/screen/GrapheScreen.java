package com.utc.graphemobile.screen;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.gephi.graph.api.Edge;
import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.io.ImportContainerImpl;
import org.gephi.io.ImporterGEXF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.utc.graphemobile.GrapheMobile;
import com.utc.graphemobile.element.EdgeSprite;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.specific.SpecificInterface;
import com.utc.graphemobile.stage.GrapheStage;
import com.utc.graphemobile.stage.UIStage;

public class GrapheScreen implements Screen, IGrapheScreen {

	private GrapheStage grapheStage;
	private UIStage uiStage;
	private GrapheMobile game;
	private Skin skin;

	private Array<String> nodeLabels;
	private boolean nodeLabelsVisible = true;

	public enum MODE {
		NORMAL, EDIT
	}

	private MODE mode = MODE.NORMAL;

	private HierarchicalGraph graph;
	private List<NodeSprite> selectedNodes = new ArrayList<NodeSprite>();
	private boolean isLabelVisible;
	private boolean isCurve = true;

	public GrapheScreen(GrapheMobile game) throws FileNotFoundException,
			URISyntaxException {
		this.game = game;

		skin = new Skin(Gdx.files.internal("data/style.json"),
				new TextureAtlas("data/style.atlas"));

		grapheStage = new GrapheStage(this);
		uiStage = new UIStage(this);
		uiStage.refresh();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(grapheStage);
		multiplexer.addProcessor(new GestureDetector(grapheStage
				.getGestureListener()));
		// multiplexer.addProcessor(new
		// GestureDetector(uiStage.getGestureListener()));
		Gdx.input.setInputProcessor(multiplexer);
		final String path = Gdx.app.getPreferences(getClass().getName()).getString(
				"filepath", null);

		if (path != null) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						
						loadGraphe(Gdx.files.absolute(path));
					} catch (Exception e) {
						Preferences pref = Gdx.app.getPreferences(getClass().getName());
						pref.clear();
						pref.flush();
					}
					
				}
			}).run();
			

		}

	}

	public void loadGraphe(FileHandle handle) throws URISyntaxException,
			FileNotFoundException {
		clearSelection();
		if (graph != null) {
			graph.clear();
		}
		Preferences pref = Gdx.app.getPreferences(getClass().getName());
		pref.putString("filepath", handle.path());
		pref.flush();
		ImporterGEXF importer = new ImporterGEXF();
		try {
			// FileHandle handle = Gdx.files.internal("data/test.gexf");

			importer.setReader(handle.reader());// FileHandle(handle);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		ImportContainerImpl container = new ImportContainerImpl();
		importer.execute(container);
		graph = importer.process();
		grapheStage.loadObjects();

	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL11.GL_POINT_SMOOTH);

		grapheStage.act(delta);
		grapheStage.draw();
		uiStage.act(delta);
		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		grapheStage.setViewport(width, height, true);
		grapheStage.getCamera().position.x = 0;
		grapheStage.getCamera().position.y = 0;
		uiStage.setViewport(width, height, true);
		uiStage.resize();
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
		// uiStage.dispose();
		// uiStage = new UIStage(this);
		// uiStage.showRightMenu();
	}

	@Override
	public void dispose() {
		grapheStage.dispose();
		uiStage.dispose();
		skin.dispose();
	}

	@Override
	public MODE getMode() {
		return mode;
	}

	@Override
	public void setMode(MODE mode) {
		this.mode = mode;
	}

	@Override
	public List<NodeSprite> getSelectedNodes() {
		return selectedNodes;
	}

	@Override
	public void clearSelection() {
		for (NodeSprite nodeSprite : selectedNodes) {
			nodeSprite.setSelected(false);
		}
		selectedNodes.clear();
		updateSelectedNodesList();
	}

	@Override
	public BitmapFont getFont() {
		return skin.getFont("default-font");
	}

	@Override
	public HierarchicalGraph getGraph() {
		return graph;
	}

	@Override
	public void updateSelectedNodesList() {
		uiStage.refresh();
	}

	public GrapheStage getGrapheStage() {
		return grapheStage;
	}

	public UIStage getUIStage() {
		return uiStage;
	}

	public Array<String> getNodeLabels() {
		return nodeLabels;
	}

	public void setNodeLabels(Array<String> array) {
		this.nodeLabels = array;
	}

	public boolean getNodeLabelsVisible() {
		return nodeLabelsVisible;
	}

	public void setNodeLabelsVisible(boolean bool) {
		this.nodeLabelsVisible = bool;
	}

	@Override
	public void iniCameraPos() {
		grapheStage.getCamera().position.x = 0;
		grapheStage.getCamera().position.y = 0;

	}

	@Override
	public void showLabel(boolean isVisible) {
		this.isLabelVisible = isVisible;
	}

	@Override
	public boolean isLabelVisible() {
		return isLabelVisible;
	}

	@Override
	public void setIsCurve(boolean isCurve) {
		this.isCurve = isCurve;
	}

	@Override
	public boolean isCurve() {
		return isCurve;
	}

	@Override
	public void showAbout(boolean b) {
		if (b) {
			this.getUIStage().addActor(this.getUIStage().getAbout());
		} else {
			this.getUIStage().getAbout().remove();
		}
	}

	@Override
	public Skin getSkin() {
		return skin;
	}

	@Override
	public SpecificInterface getOsInterface() {
		return game.getOsInterface();

	}

	@Override
	public void spatialization() {
		if (this.uiStage.getRightMenuSpatialization().getIsVisible()) {
			this.uiStage.getRightMenuSpatialization().hide();
		} else {
			this.uiStage.getRightMenuSpatialization().show();
		}
	}

	@Override
	public void deleteSelection() {
		for (NodeSprite nodeSprite : selectedNodes) {
			Node n = nodeSprite.getNodeModel();

			for (Edge e : graph.getEdges(n)) {
				for (Actor a : grapheStage.getActors()) {
					if (a instanceof EdgeSprite) {
						EdgeSprite eSprite = (EdgeSprite) a;
						if (eSprite.edgeModel.equals(e)) {
							grapheStage.getActors().removeValue(eSprite, true);
							break;
						}
					}
				}
				graph.removeEdge(e);
			}

			grapheStage.getActors().removeValue(nodeSprite, true);
			graph.removeNode(n);
		}

		clearSelection();

	}

	@Override
	public void selecteColor(Color color) {
		selectedNodes.clear();
		for (NodeSprite nodeSprite : grapheStage.getNodeSprites()) {
			if (nodeSprite.getColor().equals(color)) {
				selectedNodes.add(nodeSprite);
			}
			nodeSprite.setSelected(nodeSprite.getColor().equals(color));
		}
		updateSelectedNodesList();
	}
}
