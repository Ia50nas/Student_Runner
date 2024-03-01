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
import com.mygdx.game.Tools.WorldContactListener;

import java.util.LinkedList;


public class PlayScreen implements Screen {
    private RunnerGame game;
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



    public PlayScreen(RunnerGame game) {
        atlas = new TextureAtlas("All.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(16 * 100 / RunnerGame.PPM, 16 * 51 / RunnerGame.PPM, gamecam);
        hud = new Hud(game.batch);

        courseWorks = new LinkedList<>();
        //Load map
        maploader = new TmxMapLoader();
        map = maploader.load("Town2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / RunnerGame.PPM);
    
        //Center camera at the start
        gamecam.position.set(gameport.getWorldWidth() / 2, gameport.getWorldHeight() / 2, 0);

        // create Box2D world
        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();


        new B2WorldCreator(world, map, courseWorks, this);

        //Create the player
        player = new Runner(world, this);

        world.setContactListener(new WorldContactListener());

        //Play in loop the main theme song of the game
        music = RunnerGame.manager.get("audio/music/Runner_Game_Music.wav");
        music.setLooping(true);
        music.play();

    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {

    }

    public void handleInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2) {
            player.b2body.applyLinearImpulse(new Vector2(0.05f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2) {
            player.b2body.applyLinearImpulse(new Vector2(-0.05f, 0), player.b2body.getWorldCenter(), true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            this.dispose();
            game.setScreen(new MenuScreen(game));
        }
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP))
            player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && player.b2body.getLinearVelocity().x <= 2)
            player.b2body.applyLinearImpulse(new Vector2(0.3f, 0), player.b2body.getWorldCenter(), true);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && player.b2body.getLinearVelocity().x >= -2)
            player.b2body.applyLinearImpulse(new Vector2(-0.3f, 0), player.b2body.getWorldCenter(), true);
    }

    public void update(float dt) {
        handleInput(dt);
        world.step(1 / 60f, 6, 2);
        player.update(dt);
        hud.update(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        if(RunnerGame.Level1 == true) {
            gamecam.position.y = 12;
        }

        if((RunnerGame.Level1 && player.b2body.getPosition().x > 62.7 && player.b2body.getPosition().x < 64) && player.b2body.getPosition().y < 8 ){
            RunnerGame.Level1 = false;
            RunnerGame.Level2 = true;
            RunnerGame.Level3 = false;
            player = new Runner(world, this);
            gamecam.position.y = 4;
            RunnerGame.Score = Hud.score;
            RunnerGame.WorldTimer = Hud.worldTimer;
            hud = new Hud(game.batch);

        }
        if((RunnerGame.Level2 && player.b2body.getPosition().x > 65 ) && player.b2body.getPosition().y < 0 ){
            RunnerGame.Level1 = false;
            RunnerGame.Level2 = false;
            RunnerGame.Level3 = true;
            player = new Runner(world, this);
            gamecam.position.y = player.b2body.getPosition().y;
            RunnerGame.Score = Hud.score;
            RunnerGame.WorldTimer = Hud.worldTimer;
            hud = new Hud(game.batch);
        }
        if(RunnerGame.Level3){
            gamecam.position.y = player.b2body.getPosition().y;
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

        handleInput();
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
    public void pause() {

    }

    @Override
    public void resume() {

    }

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

