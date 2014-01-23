package com.utc.graphemobile.input;

import java.util.concurrent.TimeUnit;

import org.gephi.layout.plugin.AutoLayout;
import org.gephi.layout.plugin.force.StepDisplacement;
import org.gephi.layout.plugin.force.yifanHu.YifanHuLayout;
import org.gephi.layout.plugin.forceAtlas.ForceAtlasLayout;
import org.gephi.layout.plugin.fruchterman.FruchtermanReingold;
import org.gephi.layout.plugin.multilevel.MaximalMatchingCoarsening;
import org.gephi.layout.plugin.multilevel.MultiLevelLayout;
import org.gephi.layout.plugin.random.RandomLayout;
import org.gephi.layout.plugin.rotate.RotateLayout;
import org.gephi.layout.spi.Layout;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.screen.IGrapheScreen;

public class StartButtonListener extends ActorGestureListener {

	private IGrapheScreen screen;
	private Layout layout;

	public StartButtonListener(IGrapheScreen mScreen) {
		this.screen = mScreen;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		String targetName = this.getTouchDownTarget().getName();
		System.out.println(targetName);

		if (targetName == null || screen.getGraph() == null) {
			System.out.println("NULL");
			return;
		}

		if (targetName.equals("start")) {
			if (getSpatialization() != null && getSpatialization().isAlive()) {
				return;
			}

			System.out.println(getList().getSelection());

			if (getList().getSelection().equals("Abstract Force")) {
				layout = (Layout) new ForceAtlasLayout();
			} else if (getList().getSelection().equals("FruchtermanReingold")) {
				layout = (Layout) new FruchtermanReingold();
			} else if (getList().getSelection().equals("MultiLevel")) {
				layout = (Layout) new MultiLevelLayout(
						new MaximalMatchingCoarsening());
			} else if (getList().getSelection().equals("Random")) {
				layout = (Layout) new RandomLayout(433);
			} else if (getList().getSelection().equals("Rotate")) {
				layout = (Layout) new RotateLayout(90);
			} else if (getList().getSelection().equals("Scale")) {
				// layout = (Layout) new ScaleLayout(45);
			} else if (getList().getSelection().equals("Yifan Hu")) {
				layout = (Layout) new YifanHuLayout(new StepDisplacement(1f));
			}


			Thread spatialization = new SpatializationThread(
					new SpatializationRunnable());

			screen.getUIStage().getRightMenuSpatialization()
					.setSpatialization(spatialization);
			getSpatialization().start();
		} else if (targetName.equals("stop")) {
			if (getSpatialization() == null) {
				return;
			}
			((SpatializationThread)getSpatialization()).finish();
			screen.getUIStage().getRightMenuSpatialization()
					.setSpatialization(null);
		}
	}

	private Thread getSpatialization() {
		return screen.getUIStage().getRightMenuSpatialization()
				.getSpatialization();
	}

	private List getList() {
		return screen.getUIStage().getRightMenuSpatialization().getList();
	}

	public class SpatializationRunnable implements Runnable {

		public AutoLayout autoLayout;

		public SpatializationRunnable() {
			autoLayout = new AutoLayout(20, TimeUnit.SECONDS);
			autoLayout.setGraphModel(screen.getGraph().getGraphModel());
			autoLayout.addLayout(layout, 1.0f);

		}

		@Override
		public void run() {
			autoLayout.execute();

		}
	}

	public class SpatializationThread extends Thread {

		private SpatializationRunnable r;

		public SpatializationThread(SpatializationRunnable r) {
			super(r);
			this.r = r;
		}

		public void finish() {
			r.autoLayout.cancel();
		}
	}

}
