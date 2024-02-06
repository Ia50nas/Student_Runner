package com.mygdx.game.Screens;

import Sprites.Runner;
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

public class PlayScreen implements Screen{
    private RunnerGame game;
    Texture texture;
    private OrthographicCamera gamecam;
    private Viewport gameport;
    private Hud hud;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

        //box2d variables
    private World world;
    private Box2DDebugRenderer b2dr;
    public PlayScreen(RunnerGame game){
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

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        new Runner(world);

        //create ground body
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / RunnerGame.PPM,(rect.getY() + rect.getHeight() / 2)/RunnerGame.PPM);

            body = world.createBody(bdef);
            shape.setAsBox(rect.getWidth() / 2 / RunnerGame.PPM, rect.getHeight() / 2 / RunnerGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }
    @Override
    public void show() {

    }
    public void handleInput(float dt){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){

        }

    }
    public void update(float dt){
        handleInput(dt);
        world.step(1/60f,6,2);
        gamecam.update();
        renderer.setView(gamecam);
    }
    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        renderer.render();

        //render box2d
        b2dr.render(world, gamecam.combined);

        //set to draw

        hud.stage.draw();

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

    }
}
