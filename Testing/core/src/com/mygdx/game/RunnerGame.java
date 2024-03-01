package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;

public class RunnerGame extends Game {
	public static  final int V_WIDTH = 400;//400
	public static final int V_HEIGHT = 208;//208
	public static  final float PPM = 100; // pixels per meter
	public  static final short DEFAULT_BIT = 1;
	public  static final short RUNNER_BIT = 2;
	public  static  final  short BRICK_BIT = 4;
	public static final short OBSTACLE_BIT = 4;
	public  static  final  short COURSEWORK_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public SpriteBatch batch;
	public static boolean Level1 = false;
	public static boolean Level2 = true;
	public static boolean Level3 = false;

	public static int Score = 0;
	public static int WorldTimer = 0;

	public static AssetManager manager;
	@Override
	public void create () {
		batch = new SpriteBatch();

		manager = new AssetManager();
		manager.load("audio/music/Runner_Game_Music.wav" , Music.class);
		manager.load("audio/sounds/Coursework.wav" , Sound.class);
		manager.load("audio/sounds/fire.wav" , Sound.class);
		manager.load("audio/sounds/Game_hit.wav" , Sound.class);
		manager.load("audio/sounds/Mine_Hit.wav", Sound.class);
		manager.load("audio/sounds/Urchin_Hit.wav", Sound.class);
		manager.load("audio/sounds/Books.wav", Sound.class);
		manager.load("audio/sounds/Water.wav", Sound.class);
		manager.finishLoading();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		manager.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();
	}
}
