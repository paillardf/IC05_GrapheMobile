package com.utc.graphemobile.input;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.utc.graphemobile.element.TextButton;
import com.utc.graphemobile.screen.GrapheScreen.MODE;
import com.utc.graphemobile.screen.IGrapheScreen;

public class UIEventListener extends ActorGestureListener {

	private IGrapheScreen screen;
	private boolean isVisible = false;

	public UIEventListener(IGrapheScreen mScreen) {
		this.screen = mScreen;
	}

	@Override
	public void touchUp(InputEvent event, float x, float y, int pointer,
			int button) {
		String targetName = this.getTouchDownTarget().getName();
		System.out.println(targetName);
		if (targetName == null) return;
		if (targetName.equals("open")) {
			screen.getOsInterface().openFileChooser();
		} else if (targetName.equals("center")) {
			screen.iniCameraPos();
		} else if (targetName.equals("spatial")) {
			screen.spatialization();
		} else if (targetName.equals("label")) {
			isVisible = !isVisible;
			screen.showLabel(isVisible);
		} else if (targetName.equals("about")) {
			screen.showAbout(true);
		} else if (targetName.equals("aboutClose")) {
			screen.showAbout(false);
		} else if (targetName.equals("closeRS")) {
			screen.getUIStage().getRightMenuSpatialization().hide();
		} else if (targetName.equals("unselect")) {
			screen.clearSelection();
		} else if (targetName.equals("delete")) {
			screen.deleteSelection();
		} else if (targetName.equals("edge")) {
			screen.setIsCurve(!screen.isCurve());
		} else if (this.getTouchDownTarget().getName().equals("edit")) {
			if(screen.getMode() == MODE.EDIT){
				((TextButton)this.getTouchDownTarget()).setText("Normal");
			}else{
				((TextButton)this.getTouchDownTarget()).setText("Edit");
				
			}
			screen.getSelectedNodes().clear();
			screen.updateSelectedNodesList();
			screen.setMode((screen.getMode() == MODE.EDIT) ? MODE.NORMAL : MODE.EDIT);
		}
	}
}
