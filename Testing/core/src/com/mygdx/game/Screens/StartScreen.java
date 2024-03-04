package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

public class StartScreen implements Screen {
    private final ScreenManager screenManager;
    private final Stage stage;
    private Skin startSkin, infoSkin, exitSkin;
    private final RunnerGame game;
    private final TextureRegion Bg;

    public StartScreen(RunnerGame game, ScreenManager screenManager) {
        this.game = game;
        this.screenManager = screenManager;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Bg = new TextureRegion(new Texture("Main_Screen.png"));
        initButtons();
    }

    public void initButtons() {
        Texture startTexture = new Texture(Gdx.files.internal("Buttons/Start.png"));
        Texture infoTexture = new Texture(Gdx.files.internal("Buttons/Info.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("Buttons/Exit.png"));

        startSkin = new Skin();
        infoSkin = new Skin();
        exitSkin = new Skin();

        startSkin.add("StartButton", startTexture);
        infoSkin.add("InfoButton", infoTexture);
        exitSkin.add("ExitButton", exitTexture);

        ImageButton.ImageButtonStyle startButtonStyle = new ImageButton.ImageButtonStyle();
        startButtonStyle.imageUp = startSkin.getDrawable("StartButton");

        ImageButton.ImageButtonStyle infoButtonStyle = new ImageButton.ImageButtonStyle();
        infoButtonStyle.imageUp = infoSkin.getDrawable("InfoButton");

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = exitSkin.getDrawable("ExitButton");

        final ImageButton startButton = new ImageButton(startButtonStyle);
        final ImageButton infoButton = new ImageButton(infoButtonStyle);
        final ImageButton exitButton = new ImageButton(exitButtonStyle);

        startButton.setPosition((Gdx.graphics.getWidth() - startButton.getWidth())/2, (Gdx.graphics.getHeight() - startButton.getHeight())/2);
        infoButton.setPosition((Gdx.graphics.getWidth() - infoButton.getWidth())/2, (Gdx.graphics.getHeight() - infoButton.getHeight())/2 - 150);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, (Gdx.graphics.getHeight() - exitButton.getHeight())/2 - 300);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.PLAY);
            }
        });

        infoButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                infoButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.INFO);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.EXIT);
            }
        });

        stage.addActor(startButton);
        stage.addActor(infoButton);
        stage.addActor(exitButton);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        game.batch.begin();
        game.batch.draw(Bg, -30, 80, Bg.getRegionWidth() * 1f, Bg.getRegionHeight() * 1f);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    @Override
    public void dispose() {

        if (startSkin!= null) {
            startSkin.dispose();
        }
        if (infoSkin!= null) {
            infoSkin.dispose();
        }
        if (exitSkin!= null) {
            exitSkin.dispose();
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

