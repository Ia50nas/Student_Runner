package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Tools.ScreenManager;

public class WinScreen implements Screen {
    private  final RunnerGame game;
    private final ScreenManager screenManager;
    private final Stage stage;
    private final TextureRegion Bg;
    private Skin crossSkin;
    public WinScreen(RunnerGame game, ScreenManager screenManager) {
        this.game =game;
        this.screenManager = screenManager;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Bg = new TextureRegion(new Texture("Win_Screen.png"));

        RunnerGame.manager.get("audio/sounds/Win_Sound.wav", Sound.class).play();
        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        game.batch.begin();
        game.batch.draw(Bg, 0, 0, Bg.getRegionWidth() * 1.6f, Bg.getRegionHeight() * 0.8f);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    public void initButtons() {
        Texture crossTexture = new Texture(Gdx.files.internal("Buttons/X-cross.png"));

        crossSkin = new Skin();

        crossSkin.add("CrossButton", crossTexture);


        ImageButton.ImageButtonStyle crossButtonStyle = new ImageButton.ImageButtonStyle();
        crossButtonStyle.imageUp = crossSkin.getDrawable("CrossButton");


        final ImageButton crossButton = new ImageButton(crossButtonStyle);


        crossButton.setPosition(Gdx.graphics.getWidth() -crossButton.getWidth(), Gdx.graphics.getHeight() - crossButton.getHeight());

        crossButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                crossButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.EXIT);
            }
        });


        stage.addActor(crossButton);
    }
    @Override
    public void dispose() {
        if (crossSkin!= null) {
            crossSkin.dispose();
        }

        stage.dispose();
    }
    @Override
    public void show() {

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
}
