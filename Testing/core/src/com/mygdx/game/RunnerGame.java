package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.StartScreen;

public class RunnerGame extends Game {
	public static  final int V_WIDTH = 400;//400
	public static final int V_HEIGHT = 208;//208
	public static final float PPM = 100; // pixels per meter
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new StartScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
