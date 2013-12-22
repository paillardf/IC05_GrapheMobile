package com.utc.graphemobile.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.element.About;
import com.utc.graphemobile.element.LeftMenu;
import com.utc.graphemobile.element.RightMenu;
import com.utc.graphemobile.screen.IGrapheScreen;

public class UIStage extends Stage {

	public IGrapheScreen screen;

	private LeftMenu leftMenu;
	private RightMenu rightMenu;
	private About about;

	public UIStage(IGrapheScreen screen) {
		super();
		this.screen = screen;

		leftMenu = new LeftMenu(screen);
		this.addActor(leftMenu);

		rightMenu = new RightMenu(screen);
		this.addActor(rightMenu);
		
		about = new About(screen, this.getWidth(), this.getHeight());
		//this.addActor(about);


		resize();
	}

	public void resize() {
		leftMenu.onResize();
		rightMenu.onResize();

	}

	public void refresh() {
		rightMenu.refresh();
	}

	public Skin getSkin() {
		return screen.getSkin();
	}
	
	public LeftMenu getLeftMenu() {
		return leftMenu;
	}
	
	public RightMenu getRightMenu() {
		return rightMenu;
	}
	
	public About getAbout() {
		return about;
	}
}
