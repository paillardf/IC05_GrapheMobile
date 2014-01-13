package com.utc.graphemobile.element;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.utc.graphemobile.input.UIEventListener;
import com.utc.graphemobile.screen.IGrapheScreen;
import com.utc.graphemobile.utils.Utils;

public class About extends Table{
	
	public static final float CLOSE_SIZE = 50;
	
	private Image closeBtn;
	private IGrapheScreen screen;

	public About(IGrapheScreen screen) {
		this.setBackground(screen.getSkin().getDrawable("gray-pixel"));
		this.setFillParent(true);
		this.screen = screen;

		TextureRegion tr = screen.getSkin().getRegion("close");
		closeBtn = new Image(tr);
		closeBtn.setName("aboutClose");
		
		closeBtn.setHeight(Utils.toDp(CLOSE_SIZE));
		closeBtn.setWidth(Utils.toDp(CLOSE_SIZE));

		closeBtn.setX(Gdx.graphics.getWidth() - closeBtn.getWidth() - 10);
		closeBtn.setY(Gdx.graphics.getHeight() - closeBtn.getHeight() - 10);
		closeBtn.addListener(new UIEventListener(screen));
		
		this.addActor(Text());
		
		this.addActor(closeBtn);
	}
	
	private Table Text() {
		Table table = new Table();
		table.setFillParent(true);
		
		Label empty = new Label(" ", screen.getSkin());
		Label graph = new Label("- Graphe Mobile -", screen.getSkin());
		Label floJ = new Label("Florian JEANNE", screen.getSkin());
		Label floP = new Label("Florian PAILLARD", screen.getSkin());
		Label bapP = new Label("Baptiste PIRAULT", screen.getSkin());
		Label thxTo = new Label("- Remerciements -", screen.getSkin());
		Label fraG = new Label("Franck GHITALLA", screen.getSkin());
		Label ic05 = new Label("Application développée dans le cadre de l'UV IC05 - Automne 2013", screen.getSkin());
		Label utc = new Label("UTC : Université de Technologie de Compiègne", screen.getSkin());
		
		graph.setFontScale(Utils.toDp(0.8f));
		floJ.setFontScale(Utils.toDp(0.4f));
		floP.setFontScale(Utils.toDp(0.4f));
		bapP.setFontScale(Utils.toDp(0.4f));
		thxTo.setFontScale(Utils.toDp(0.5f));
		fraG.setFontScale(Utils.toDp(0.3f));
		ic05.setFontScale(Utils.toDp(0.2f));
		utc.setFontScale(Utils.toDp(0.2f));
		
		table.add(graph);
		table.row();
		table.add(floJ);
		table.row();
		table.add(floP);
		table.row();
		table.add(bapP);
		table.row();
		table.add(empty);
		table.row();
		table.add(thxTo);
		table.row();
		table.add(fraG);
		table.row();
		table.add(empty);
		table.row();
		table.add(ic05);
		table.row();
		table.add(utc);
		
		
		return table;
	}
	
	public void onResize() {
		this.top().left();
		closeBtn.setX(Gdx.graphics.getWidth() - closeBtn.getWidth() - 10);
		closeBtn.setY(Gdx.graphics.getHeight() - closeBtn.getHeight() - 10);
		
		invalidate();
	}
}
