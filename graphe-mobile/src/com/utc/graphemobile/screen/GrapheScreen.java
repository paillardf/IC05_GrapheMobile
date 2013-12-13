package com.utc.graphemobile.screen;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.graph.api.Node;
import org.gephi.io.ImportContainerImpl;
import org.gephi.io.ImporterGEXF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.utils.Array;
import com.utc.graphemobile.GrapheMobile;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.stage.GrapheStage;
import com.utc.graphemobile.stage.UIStage;

public class GrapheScreen implements Screen, IGrapheScreen {

	private GrapheStage grapheStage;
	private UIStage uiStage;
	private GrapheMobile game;
	
	private Array<String> nodeLabels;
	private boolean nodeLabelsVisible = true;

	public enum MODE {
		NORMAL, EDIT
	}

	private MODE mode = MODE.NORMAL;

	private HierarchicalGraph graph;
	private List<NodeSprite> selectedNodes = new ArrayList<NodeSprite>();
	private BitmapFont font;

	public GrapheScreen(GrapheMobile game) throws FileNotFoundException, URISyntaxException {
		this.game = game;
		
		font = new BitmapFont();
		font.getRegion().getTexture()
				.setFilter(TextureFilter.Linear, TextureFilter.Linear);

		grapheStage = new GrapheStage(this);
		uiStage = new UIStage(this);
		uiStage.showLeftMenu();
		uiStage.showRightMenu();
		uiStage.showHideAndShowButton();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(grapheStage);
		multiplexer.addProcessor(new GestureDetector(grapheStage
				.getGestureListener()));
		// multiplexer.addProcessor(new
		// GestureDetector(uiStage.getGestureListener()));
		Gdx.input.setInputProcessor(multiplexer);
		
		loadGraphe(Gdx.files.internal("data/test.gexf"));//TODO to REMOVE
		
		this.getNodes();
	}
	
	private void getNodes() {
		// on récupère les labels des nodes
		Array<String> nodeLabels = new Array<String>();
		for(Node n : this.getGraph().getNodes()) {
			nodeLabels.add(n.getNodeData().getLabel());
		}
		this.setNodeLabels(nodeLabels);
	}

	
	public void loadGraphe(FileHandle handle) throws URISyntaxException, FileNotFoundException {
		
		ImporterGEXF importer = new ImporterGEXF();
        try {
        	//FileHandle handle = Gdx.files.internal("data/test.gexf");
           
            importer.setReader(handle.reader());//FileHandle(handle);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
        ImportContainerImpl container = new ImportContainerImpl();
        importer.execute(container);
        graph = importer.process();   
		grapheStage.loadObjects();
//		new Thread(new Runnable() {
//			         @Override
//			         public void run() {
//			        	 AutoLayout autoLayout = new AutoLayout(10, TimeUnit.MINUTES);
//			     		autoLayout.setGraphModel(graph.getGraphModel());
//			     		YifanHuLayout firstLayout = new YifanHuLayout(new StepDisplacement(1f));
//			     		ForceAtlasLayout secondLayout = new ForceAtlasLayout();
//			     		AutoLayout.DynamicProperty adjustBySizeProperty = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.TRUE, 0.1f);//True after 10% of layout time
//			     		AutoLayout.DynamicProperty repulsionProperty = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", new Double(500.), 0f);//500 for the complete period
//			     		autoLayout.addLayout(firstLayout, 0.5f);
//			     		autoLayout.addLayout(secondLayout, 0.5f, new AutoLayout.DynamicProperty[]{adjustBySizeProperty, repulsionProperty});
//			     		autoLayout.execute();
//			         }
//			    
//			   }).start();
		

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
		uiStage.dispose();
		uiStage = new UIStage(this);
		uiStage.showLeftMenu();
		uiStage.showRightMenu();
		uiStage.showHideAndShowButton();
	}

	@Override
	public void dispose() {
		grapheStage.dispose();
		uiStage.dispose();
		font.dispose();
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
	public BitmapFont getFont() {
		return font;
	}

	@Override
	public HierarchicalGraph getGraph() {
		return graph;
	}


	@Override
	public void updateSelectedNodesList() {
		// TODO Auto-generated method stub
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
}
