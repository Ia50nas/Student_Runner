package com.mygdx.game.Screens;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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

public class PlayScreen implements Screen{
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
    public PlayScreen(RunnerGame game){
        atlas = new TextureAtlas("Runner.pack");
        this.game = game;
        gamecam = new OrthographicCamera();
        gameport = new FitViewport(16 * 100/ RunnerGame.PPM,16 * 51 / RunnerGame.PPM, gamecam);
        hud = new Hud(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("Town2.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/RunnerGame.PPM);
        gamecam.position.set(gameport.getWorldWidth()/2, gameport.getWorldHeight()/2,0);
        world = new World(new Vector2(0,-10),true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world,map);
        player = new Runner(world,this);

    }
    public TextureAtlas getAtlas(){
        return  atlas;
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
    public void update(float dt){
        world.step(1/60f,6,2);
        player.update(dt);
        gamecam.position.x = player.b2body.getPosition().x;
        gamecam.update();
        renderer.setView(gamecam);
        System.out.println(player.b2body.getPosition());
    }
    @Override
    public void render(float delta) {
        //clear game screen
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        //render map
        renderer.render();

        //render box2d
        b2dr.render(world, gamecam.combined);

        //player
        game.batch.setProjectionMatrix(gamecam.combined);

        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        //set to draw
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        handleInput();
    }

    @Override
    public void resize(int width, int height) {
        gameport.update(width, height);
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
