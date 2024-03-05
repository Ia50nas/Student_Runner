package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.StartScreen;
import com.mygdx.game.Tools.ScreenManager;

public class RunnerGame extends Game {
	public static  final int V_WIDTH = 400;//400
	public static final int V_HEIGHT = 208;//208
	public static final float PPM = 100; // pixels per meter
	public  static final short DEFAULT_BIT = 1;
	public  static final short RUNNER_BIT = 2;
	public  static  final  short BRICK_BIT = 4;
	public static final short OBSTACLE_BIT = 4;
	public  static  final  short COURSEWORK_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public SpriteBatch batch;
	public static boolean Level1 = false;
	public static boolean Level2 = false;
	public static boolean Level3 = true;
	public static boolean canJump = true;
	public static int Score = 0;
	public static int WorldTimer = 0;
	public enum Screen_Type {
		START, MENU, RESUME, PLAY, INFO, WIN, LOSE, EXIT
	}

	public static AssetManager manager;
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		ScreenManager screenManager = new ScreenManager(this);


		manager.load("audio/music/Runner_Game_Music.wav" , Music.class);
		manager.load("audio/sounds/Coursework.wav" , Sound.class);
		manager.load("audio/sounds/fire.wav" , Sound.class);
		manager.load("audio/sounds/Game_hit.wav" , Sound.class);
		manager.load("audio/sounds/Mine_Hit.wav", Sound.class);
		manager.load("audio/sounds/Urchin_Hit.wav", Sound.class);
		manager.load("audio/sounds/Books.wav", Sound.class);
		manager.load("audio/sounds/Water.wav", Sound.class);
		manager.load("audio/sounds/Win_Sound.wav", Sound.class);
		manager.load("audio/sounds/Lose.wav", Sound.class);
		manager.finishLoading();

		screenManager.putScreen(Screen_Type.START);
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
