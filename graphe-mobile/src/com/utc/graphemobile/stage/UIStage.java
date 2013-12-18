package com.utc.graphemobile.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.utc.graphemobile.element.LeftMenu;
import com.utc.graphemobile.element.RightMenu;
import com.utc.graphemobile.screen.IGrapheScreen;

public class UIStage extends Stage {

	public IGrapheScreen screen;

	private LeftMenu leftMenu;
	private RightMenu rightMenu;

	public UIStage(IGrapheScreen screen) {
		super();
		this.screen = screen;

		leftMenu = new LeftMenu(screen);
		this.addActor(leftMenu);

		rightMenu = new RightMenu(screen);
		this.addActor(rightMenu);


		resize();
	}

	public void resize() {
		leftMenu.onResize();
		rightMenu.onResize();

	}

	public void refresh() {
		rightMenu.refresh();
	}


//	private void drawAbout() {
//		aboutTable.add(about()).height(getHeight()).width(getWidth());
//	}

//	private Table about() {
//		Table table = new Table();
//		table.setBackground(getSkin().getDrawable("gray-pixel"));
//
//		TextureRegion tr = new TextureRegion(new Texture(
//				Gdx.files.internal("Delete All.png")));
//		Image closeBtn = new Image(tr);
//		closeBtn.setName("aboutClose");
//		float scale = (float) (leftMenu.getWidth() / closeBtn.getWidth());
//		closeBtn.setWidth(leftMenu.getWidth() / 2);
//		closeBtn.setHeight((closeBtn.getHeight() / 2) * scale);
//		closeBtn.setX(getWidth() - closeBtn.getWidth() - 10);
//		closeBtn.setY(getHeight() - closeBtn.getHeight() - 10);
//		closeBtn.addListener(new UIEventListener(this.screen));
//		table.addActor(closeBtn);
//
//		return table;
//	}


	public Skin getSkin() {
		return screen.getSkin();
	}
}
