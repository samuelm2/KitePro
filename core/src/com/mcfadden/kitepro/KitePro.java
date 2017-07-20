package com.mcfadden.kitepro;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class KitePro extends Game {

	public static SpriteBatch batch;

	public static Skin cloudFormUI;

	public static float SCREEN_WIDTH;
	public static float SCREEN_HEIGHT;
	public static float UNIT;
	public static ShapeRenderer shapeRenderer;
	@Override
	public void create () {
		batch = new SpriteBatch();
		shapeRenderer = new ShapeRenderer();
		SCREEN_WIDTH = Gdx.graphics.getWidth();
		SCREEN_HEIGHT = Gdx.graphics.getHeight();
		UNIT = Gdx.graphics.getWidth()/10;

		cloudFormUI = new Skin(Gdx.files.internal("cloud-form/skin/cloud-form-ui.json"));

		this.setScreen(new StartingMenu(this));
	}

	@Override
	public void render () {
			super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
