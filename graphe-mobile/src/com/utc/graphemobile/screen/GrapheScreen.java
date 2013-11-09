package com.utc.graphemobile.screen;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import org.gephi.graph.api.DirectedGraph;
import org.gephi.graph.api.GraphController;
import org.gephi.graph.api.GraphModel;
import org.gephi.io.importer.api.Container;
import org.gephi.io.importer.api.ContainerFactory;
import org.gephi.io.importer.api.ImportController;
import org.gephi.io.processor.plugin.DefaultProcessor;
import org.gephi.project.api.ProjectController;
import org.gephi.project.api.Workspace;
import org.openide.util.Lookup;

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
	
	
	private DirectedGraph graph;

	public GrapheScreen() throws FileNotFoundException, URISyntaxException {
		loadGraphe();
		grapheStage = new GrapheStage(graph);
		uiStage = new UIStage();
		InputMultiplexer multiplexer = new InputMultiplexer();
		multiplexer.addProcessor(uiStage);
		multiplexer.addProcessor(grapheStage);
		multiplexer.addProcessor(new GestureDetector(grapheStage.getGestureListener()));
		//multiplexer.addProcessor(new GestureDetector(uiStage.getGestureListener()));
		Gdx.input.setInputProcessor(multiplexer);
		System.out.println("ok");
	}
	
	
	
	private void loadGraphe() throws URISyntaxException, FileNotFoundException {
		ProjectController pc = Lookup.getDefault().lookup(ProjectController.class);
		pc.newProject();
		Workspace workspace = pc.getCurrentWorkspace();
		 
		
		Container container = Lookup.getDefault().lookup(ContainerFactory.class).newContainer();
		ImportController importController = Lookup.getDefault().lookup(ImportController.class);
		File f = new File(getClass().getResource("/com/utc/graphemobile/test.gexf").toURI());
		container = importController.importFile(f);
		
		//Generate a new random graph into a container
	
//		RandomGraph randomGraph = new RandomGraph();
//		randomGraph.setNumberOfNodes(20);
//		randomGraph.setWiringProbability(0.005);
//		randomGraph.generate(container.getLoader());
		 
		//Append container to graph structure
		importController.process(container, new DefaultProcessor(), workspace);
		 
		//See if graph is well imported
		GraphModel graphModel = Lookup.getDefault().lookup(GraphController.class).getModel();
		graph = graphModel.getDirectedGraph();
		System.out.println("Nodes: " + graph.getNodeCount());
		System.out.println("Edges: " + graph.getEdgeCount());
		 
		//Layout for 1 minute - pour les effets
//		AutoLayout autoLayout = new AutoLayout(1, TimeUnit.MINUTES);
//		autoLayout.setGraphModel(graphModel);
//		YifanHuLayout firstLayout = new YifanHuLayout(null, new StepDisplacement(1f));
//		ForceAtlasLayout secondLayout = new ForceAtlasLayout(null);
//		AutoLayout.DynamicProperty adjustBySizeProperty = AutoLayout.createDynamicProperty("forceAtlas.adjustSizes.name", Boolean.TRUE, 0.1f);//True after 10% of layout time
//		AutoLayout.DynamicProperty repulsionProperty = AutoLayout.createDynamicProperty("forceAtlas.repulsionStrength.name", new Double(500.), 0f);//500 for the complete period
//		autoLayout.addLayout(firstLayout, 0.5f);
//		autoLayout.addLayout(secondLayout, 0.5f, new AutoLayout.DynamicProperty[]{adjustBySizeProperty, repulsionProperty});
//		autoLayout.execute();
		
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

}
