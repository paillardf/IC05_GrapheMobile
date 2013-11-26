package com.utc.graphemobile.screen;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.gephi.graph.api.HierarchicalGraph;
import org.gephi.io.ImportContainerImpl;
import org.gephi.io.ImporterGEXF;
import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.input.GestureDetector;
import com.utc.graphemobile.element.NodeSprite;
import com.utc.graphemobile.stage.GrapheStage;
import com.utc.graphemobile.stage.UIStage;

public class GrapheScreen implements Screen , IGrapheScreen{

	private GrapheStage grapheStage;
	private UIStage uiStage;
	
	public enum MODE{
		NORMAL,
		EDIT
		
	}
	
	private MODE mode = MODE.EDIT;
	
	private HierarchicalGraph graph;
	private List<NodeSprite> selectedNodes = new ArrayList<NodeSprite>();

	public GrapheScreen() throws FileNotFoundException, URISyntaxException {
		loadGraphe();
		grapheStage = new GrapheStage( graph, this);
		uiStage = new UIStage();
		uiStage.size();
		uiStage.drawMenu();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(grapheStage);
		multiplexer.addProcessor(new GestureDetector(grapheStage.getGestureListener()));
		//multiplexer.addProcessor(new GestureDetector(uiStage.getGestureListener()));
		Gdx.input.setInputProcessor(multiplexer);
	}
	
	

	
	private void loadGraphe() throws URISyntaxException, FileNotFoundException {
		
		ImporterGEXF importer = new ImporterGEXF();
        try {
        	FileHandle handle = Gdx.files.internal("data/test.gexf");
           
            importer.setReader(handle.reader());//FileHandle(handle);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
        ImportContainerImpl container = new ImportContainerImpl();
        importer.execute(container);
        graph = importer.process();
        //importer.getContainer();
       /* Dhns d = new Dhns();
        importer.
        GraphViewImpl imp = new GraphViewImpl(d, 0);
        imp.s
        graph = new HierarchicalDirectedGraphImpl(importer.getContainer()., imp);
        graph.
		/*
		 * Container container = Lookup.getDefault().lookup(ContainerFactory.class).newContainer();
		 
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		File f = new File(getClass().getResource("/com/utc/graphemobile/test.gexf").toURI());
		container = importController.importFile(f);
		*/
		//Generate a new random graph into a container
	
//		RandomGraph randomGraph = new RandomGraph();
//		randomGraph.setNumberOfNodes(20);
//		randomGraph.setWiringProbability(0.005);
//		randomGraph.generate(container.getLoader());
		 
		//Append container to graph structure
		//importController.process(container, new DefaultProcessor(), workspace);
		 
		//See if graph is well imported
		/*
		 * GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		 
		graph = graphModel.getDirectedGraph();
		System.out.println("Nodes: " + graph.getNodeCount());
		System.out.println("Edges: " + graph.getEdgeCount());
		 */
		//Layout for 1 minute - pour les effets
        
        
		
		new Thread(new Runnable() {
			         @Override
			         public void run() {
			        	 AutoLayout autoLayout = new AutoLayout(10, TimeUnit.MINUTES);
			     		autoLayout.setGraphModel(graph.getGraphModel());
			     		YifanHuLayout firstLayout = new YifanHuLayout(new StepDisplacement(1f));
			     		ForceAtlasLayout secondLayout = new ForceAtlasLayout();
			     		AutoLayout.DynamicProperty adjustBySizeProperty = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.TRUE, 0.1f);//True after 10% of layout time
			     		AutoLayout.DynamicProperty repulsionProperty = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", new Double(500.), 0f);//500 for the complete period
			     		autoLayout.addLayout(firstLayout, 0.5f);
			     		autoLayout.addLayout(secondLayout, 0.5f, new AutoLayout.DynamicProperty[]{adjustBySizeProperty, repulsionProperty});
			     		autoLayout.execute();
			         }
			    
			   }).start();
		
	}



	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

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
		return selectedNodes ;
	}
	
	

}
