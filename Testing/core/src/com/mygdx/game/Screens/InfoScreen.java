package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Tools.ScreenManager;

public class InfoScreen implements Screen {
    private  final RunnerGame game;
    private final ScreenManager screenManager;
    private final TextureRegion Bg;
    public InfoScreen(RunnerGame game , ScreenManager screenManager) {
        this.game =game;
        this.screenManager = screenManager;
        Bg = new TextureRegion(new Texture("Info_Screen.png"));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        game.batch.begin();
        game.batch.draw(Bg, -30, 80, Bg.getRegionWidth() * 7.5f, Bg.getRegionHeight() * 7.5f);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
