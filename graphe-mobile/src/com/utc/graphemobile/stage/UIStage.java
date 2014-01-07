package com.utc.graphemobile.stage;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.utc.graphemobile.element.About;
import com.utc.graphemobile.element.LeftMenu;
import com.utc.graphemobile.element.RightMenuEdit;
import com.utc.graphemobile.element.RightMenuSpatialization;
import com.utc.graphemobile.screen.IGrapheScreen;

public class UIStage extends Stage {

	public IGrapheScreen screen;

	private LeftMenu leftMenu;
	private RightMenuEdit rightMenuEdit;
	private RightMenuSpatialization rightMenuSpa;
	private About about;

	public UIStage(IGrapheScreen screen) {
		super();
		this.screen = screen;

		leftMenu = new LeftMenu(screen);
		this.addActor(leftMenu);

		rightMenuEdit = new RightMenuEdit(screen);
		this.addActor(rightMenuEdit);
		
		rightMenuSpa = new RightMenuSpatialization(screen);
		this.addActor(rightMenuSpa);

		about = new About(screen);
		// this.addActor(about);

		resize();
	}

	public void resize() {
		leftMenu.onResize();
		rightMenuEdit.onResize();
		about.onResize();
	}

	public void refresh() {
		rightMenuEdit.refresh();
	}

	public Skin getSkin() {
		return screen.getSkin();
	}

	public LeftMenu getLeftMenu() {
		return leftMenu;
	}

	public RightMenuEdit getRightMenuEdit() {
		return rightMenuEdit;
	}
	
	public RightMenuSpatialization getRightMenuSpatialization() {
		return rightMenuSpa;
	}

	public About getAbout() {
		return about;
	}
}
