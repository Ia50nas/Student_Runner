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

	public enum Power_Up {
		None, Speed, DoubleJUmp, BigJump
	}

	public static AssetManager manager;
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		ScreenManager screenManager = new ScreenManager(this);

		manager.load("Audio/music/Runner_Game_Music.mp3" , Music.class);
		manager.load("Audio/sounds/Coursework.mp3" , Sound.class);
		manager.load("Audio/sounds/Fire.mp3" , Sound.class);
		manager.load("Audio/sounds/Game_hit.mp3" , Sound.class);
		manager.load("Audio/sounds/Mine_Hit.mp3", Sound.class);
		manager.load("Audio/sounds/Urchin_Hit.mp3", Sound.class);
		manager.load("Audio/sounds/Books.mp3", Sound.class);
		manager.load("Audio/sounds/Water.mp3", Sound.class);
		manager.load("Audio/sounds/Win_Sound.mp3", Sound.class);
		manager.load("Audio/sounds/Lose.mp3", Sound.class);
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
