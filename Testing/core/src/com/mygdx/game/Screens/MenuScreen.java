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

public class MenuScreen implements Screen {
    private final RunnerGame game;
    private final Stage stage;
    private Skin restartSkin, infoSkin, exitSkin;

    public MenuScreen(RunnerGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        initButtons();
    }

    public void initButtons() {
        Texture restartTexture = new Texture(Gdx.files.internal("Buttons/Start.png"));
        Texture infoTexture = new Texture(Gdx.files.internal("Buttons/Info.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("Buttons/Exit.png"));

        restartSkin = new Skin();
        infoSkin = new Skin();
        exitSkin = new Skin();

        restartSkin.add("RestartButton", restartTexture);
        infoSkin.add("InfoButton", infoTexture);
        exitSkin.add("ExitButton", exitTexture);

        ImageButton.ImageButtonStyle restartButtonStyle = new ImageButton.ImageButtonStyle();
        restartButtonStyle.imageUp = restartSkin.getDrawable("RestartButton");

        ImageButton.ImageButtonStyle infoButtonStyle = new ImageButton.ImageButtonStyle();
        infoButtonStyle.imageUp = infoSkin.getDrawable("InfoButton");

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = exitSkin.getDrawable("ExitButton");

        final ImageButton restartButton = new ImageButton(restartButtonStyle);
        final ImageButton infoButton = new ImageButton(infoButtonStyle);
        final ImageButton exitButton = new ImageButton(exitButtonStyle);

        restartButton.setPosition((Gdx.graphics.getWidth() - restartButton.getWidth())/2, (Gdx.graphics.getHeight() - restartButton.getHeight())/2 + 300);
        infoButton.setPosition((Gdx.graphics.getWidth() - infoButton.getWidth())/2, (Gdx.graphics.getHeight() - infoButton.getHeight())/2);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, (Gdx.graphics.getHeight() - exitButton.getHeight())/2 - 300);

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartButton.setChecked(false);
                game.setScreen(new PlayScreen(game));
            }
        });

        infoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                infoButton.setChecked(false);
                game.setScreen(new InfoScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitButton.setChecked(false);
                Gdx.app.exit();
            }
        });

        stage.addActor(restartButton);
        stage.addActor(infoButton);
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
        restartSkin.dispose();
        infoSkin.dispose();
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




