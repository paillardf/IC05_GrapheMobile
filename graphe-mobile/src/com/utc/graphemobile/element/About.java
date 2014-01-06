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
	
	private Image closeBtn;

	public About(IGrapheScreen screen) {
		this.setBackground(screen.getSkin().getDrawable("gray-pixel"));
		this.setFillParent(true);

		TextureRegion tr = screen.getSkin().getRegion("close");
		closeBtn = new Image(tr);
		closeBtn.setName("aboutClose");
		
		closeBtn.setHeight(Utils.toDp(CLOSE_SIZE));
		closeBtn.setWidth(Utils.toDp(CLOSE_SIZE));

		closeBtn.setX(Gdx.graphics.getWidth() - closeBtn.getWidth() - 10);
		closeBtn.setY(Gdx.graphics.getHeight() - closeBtn.getHeight() - 10);
		closeBtn.addListener(new UIEventListener(screen));
		
		this.addActor(closeBtn);
	}
	
	public void onResize() {
		this.top().left();
		closeBtn.setX(Gdx.graphics.getWidth() - closeBtn.getWidth() - 10);
		closeBtn.setY(Gdx.graphics.getHeight() - closeBtn.getHeight() - 10);
		
		invalidate();
	}
}
