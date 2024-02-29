package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.RunnerGame;

public class StartScreen implements Screen {
    private final RunnerGame game;
    private final Stage stage;
    private Skin playSkin, exitSkin,optionsSkin,infoSkin;

    public StartScreen(RunnerGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        initButtons();
    }

    public void initButtons() {
        Texture playTexture = new Texture(Gdx.files.internal("Buttons/Start.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("Buttons/Exit.png"));

        playSkin = new Skin();
        exitSkin = new Skin(); //


        playSkin.add("PlayButton", playTexture);
        exitSkin.add("ExitButton", exitTexture);//

        ImageButton.ImageButtonStyle playButtonStyle = new ImageButton.ImageButtonStyle();
        playButtonStyle.imageUp = playSkin.getDrawable("PlayButton");

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = exitSkin.getDrawable("ExitButton");

        final ImageButton playButton = new ImageButton(playButtonStyle);
        final ImageButton exitButton = new ImageButton(exitButtonStyle);
        playButton.setPosition((Gdx.graphics.getWidth() - playButton.getWidth())/2, (Gdx.graphics.getHeight() - playButton.getHeight())/2 +200);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, (Gdx.graphics.getHeight() - exitButton.getHeight())/2 -200);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButton.setChecked(false);
                game.setScreen((new PlayScreen(game)));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitButton.setChecked(false);
                Gdx.app.exit();
            }
        });




        stage.addActor(playButton);
        stage.addActor(exitButton);


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        stage.draw();
    }
    @Override
    public void dispose() {
        playSkin.dispose();
        exitSkin.dispose();
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

