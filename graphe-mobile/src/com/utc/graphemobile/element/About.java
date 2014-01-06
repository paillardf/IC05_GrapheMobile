package com.utc.graphemobile.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

public class About extends Table{
	
	public static final float CLOSE_SIZE = 50;

	public About(IGrapheScreen screen, float width, float height) {
		this.setBackground(screen.getSkin().getDrawable("gray-pixel"));
		this.setFillParent(true);

		TextureRegion tr = screen.getSkin().getRegion("close");
		Image closeBtn = new Image(tr);
		closeBtn.setName("aboutClose");
		/*float scale = (float) (screen.getUIStage().getLeftMenu().getWidth() / closeBtn.getWidth());
		closeBtn.setWidth(screen.getUIStage().getLeftMenu().getWidth() / 2);
		closeBtn.setHeight((closeBtn.getHeight() / 2) * scale);*/
		
		closeBtn.setHeight(Utils.toDp(CLOSE_SIZE));
		closeBtn.setWidth(Utils.toDp(CLOSE_SIZE));

		closeBtn.setX(width - closeBtn.getWidth() - 10);
		closeBtn.setY(height - closeBtn.getHeight() - 10);
		closeBtn.addListener(new UIEventListener(screen));
		
		this.addActor(closeBtn);
		
	}
	
	private void drawAbout() {
		//aboutTable.add(about()).height(getHeight()).width(getWidth());
	}
	
	public void onResize() {
		setHeight(Gdx.graphics.getHeight());
		
		invalidate();
		this.top().left();
	}
}
