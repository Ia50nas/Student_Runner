package com.mygdx.game.Screens;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.Sprites.CourseWork;
import com.mygdx.game.Sprites.Runner;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Tools.B2WorldCreator;
import com.mygdx.game.Tools.ScreenManager;
import com.mygdx.game.Tools.WorldContactListener;

import java.util.LinkedList;

import static com.mygdx.game.RunnerGame.*;

public class PlayScreen implements Screen {
    private RunnerGame game;
    private ScreenManager screenManager;
    private TextureAtlas atlas;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;
    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    //box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    private Runner player;
    private LinkedList<CourseWork> courseWorks;
    private Music music;

    public PlayScreen(RunnerGame game, ScreenManager screenManager) {

        atlas = new TextureAtlas("Tilemaps/All.pack");
        this.game = game;
        this.screenManager = screenManager;
        Gdx.input.setInputProcessor(null);
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(16 * 100 / RunnerGame.PPM, 16 * 51 / RunnerGame.PPM, gamecam);
        hud = new Hud(game.batch);

        courseWorks = new LinkedList<>();
        //Load map
        maploader = new TmxMapLoader();
        map = maploader.load("Tilemaps/Town2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / RunnerGame.PPM);
    
        //Center camera at the start
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        // create Box2D world
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        //create world
        new B2WorldCreator(world, map, courseWorks, this);

        //Create the player
        player = new Runner(world, this);

        world.setContactListener(new WorldContactListener());

        //Play in loop the main theme song of the game
        music = RunnerGame.manager.get("Audio/music/Runner_Game_Music.mp3");
        music.setLooping(true);
        music.play();
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }


    // handle user control input
    public boolean handleInput() {
        boolean noInput = true;
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            if (canJump > 0) {
                player.b2body.applyLinearImpulse(new Vector2(0, RunnerGame.VERTICAL_SPEED), player.b2body.getWorldCenter(), true);
                canJump--;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.b2body.setLinearVelocity(HORIZONTAL_SPEED, player.b2body.getLinearVelocity().y);
            noInput = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.b2body.setLinearVelocity(-HORIZONTAL_SPEED, player.b2body.getLinearVelocity().y);
            noInput = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            screenManager.putScreen(RunnerGame.Screen_Type.MENU);
        }
        if (noInput) {
            player.b2body.setLinearVelocity(0, player.b2body.getLinearVelocity().y);
        }
        return true;
    }

    public void update(float dt) {
        handleInput();
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        hud.update(dt);

        // camera moving with the runner
        gamecam.position.x = player.b2body.getPosition().x;

        // initialize camera y-axis
        if(RunnerGame.Level1) {
            gamecam.position.y = 12;
        }

        // Level 1 Power Up
        if(RunnerGame.Level1 && player.b2body.getPosition().x > 34 ){
            DoubleJump = true;
            VERTICAL_SPEED = 4.5f;
        }

        // Level 2 Transferring from level 1 and Resetting Power Up
        if((RunnerGame.Level1 && player.b2body.getPosition().x > 62.7 && player.b2body.getPosition().x < 64) && player.b2body.getPosition().y < 8 ){
            RunnerGame.Level1 = false;
            RunnerGame.Level2 = true;
            RunnerGame.Level3 = false;
            RunnerGame.DoubleJump = false;
            VERTICAL_SPEED = 5f;
            player = new Runner(world, this);
            gamecam.position.y = 4;
            RunnerGame.Score = Hud.score;
            RunnerGame.WorldTimer = Hud.worldTimer;
            hud = new Hud(game.batch);
        }

        // Level 2 Power Up
        if(RunnerGame.Level2 && player.b2body.getPosition().x > 20.7 ){
            VERTICAL_SPEED = 6f;
        }

        // Level 3 Transferring from level 2 and Resetting Power Up
        if((RunnerGame.Level2 && player.b2body.getPosition().x > 65 ) && player.b2body.getPosition().y < 0 ){
            RunnerGame.Level1 = false;
            RunnerGame.Level2 = false;
            RunnerGame.Level3 = true;
            VERTICAL_SPEED = 5f;
            player = new Runner(world, this);
            gamecam.position.y = player.b2body.getPosition().y;
            RunnerGame.Score = Hud.score;
            RunnerGame.WorldTimer = Hud.worldTimer;
            hud = new Hud(game.batch);
        }

        // Level 3 includes two levels of field so the camera will follow the player at both x and y axis
        if(RunnerGame.Level3){
            gamecam.position.y = player.b2body.getPosition().y;
        }

        // Level 3 Power Up
        if (Level3 && player.b2body.getPosition().x < 109.4 && player.b2body.getPosition().y < 5 && player.b2body.getPosition().y > 4){
            HORIZONTAL_SPEED = 5f;
        }

        // Win and Lose Conditions
        // If timer less than 0 late submission reduction applied
        if (RunnerGame.Level3 && player.b2body.getPosition().x < 85 ) {
            if (hud.worldTimer > 0) {
                if (hud.score >= 500) {
                    music.stop();
                    screenManager.putScreen(RunnerGame.Screen_Type.WIN);
                } else {
                    music.stop();
                    screenManager.putScreen(RunnerGame.Screen_Type.LOSE);
                }
            } else {
                if ((hud.score -40*hud.score/100) > 500) {
                    music.stop();
                    screenManager.putScreen(RunnerGame.Screen_Type.WIN);
                } else {
                    music.stop();
                    screenManager.putScreen(RunnerGame.Screen_Type.LOSE);
                }
            }
        }



        gamecam.update();
        renderer.setView(gamecam);


    }

    @Override
    public void render(float delta) {
        //clear game screen
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        //render map
        renderer.render();

        //render box2d
        //b2dr.render(world, gamecam.combined);

        //player
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        for (CourseWork courseWork : courseWorks) {
            courseWork.render(game.batch);
        }

        //set to draw
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    public void removeCourseWork(CourseWork courseWork) {
        courseWorks.remove(courseWork);
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
    }

    public TiledMap getMap() {
        return map;
    }

    public World getWorld() {
        return world;
    }
    public Runner getPlayer() {
        return player;
    }

    @Override
    public void show() {

    }
    @Override
    public void pause() {

    }
    @Override
    public void resume() { Gdx.input.setInputProcessor(null); }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
    }

}

