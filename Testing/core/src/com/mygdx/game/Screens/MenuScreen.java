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

public class MenuScreen implements Screen {
    private final ScreenManager screenManager;
    private final Stage stage;
    private Skin restartSkin, infoSkin, exitSkin, resumeSkin;
    private final TextureRegion Bg;
    private final RunnerGame game;

    public MenuScreen(RunnerGame game, ScreenManager screenManager) {
        this.game = game;
        this.screenManager = screenManager;
        this.stage = new Stage(new ScreenViewport());

        // background and sound for Menu
        Gdx.input.setInputProcessor(stage);
        Bg = new TextureRegion(new Texture("Art/Menu_Screen.png"));

        initButtons();
    }

    // Initialize all buttons that are on the screen
    // Create the skin = image and its use
    public void initButtons() {
        Texture restartTexture = new Texture(Gdx.files.internal("Buttons/Restart.png"));
        Texture infoTexture = new Texture(Gdx.files.internal("Buttons/Info.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("Buttons/Exit.png"));
        Texture resumeTexture = new Texture(Gdx.files.internal("Buttons/Resume.png"));

        restartSkin = new Skin();
        infoSkin = new Skin();
        exitSkin = new Skin();
        resumeSkin = new Skin();

        restartSkin.add("RestartButton", restartTexture);
        infoSkin.add("InfoButton", infoTexture);
        exitSkin.add("ExitButton", exitTexture);
        resumeSkin.add("ResumeButton", resumeTexture);

        ImageButton.ImageButtonStyle restartButtonStyle = new ImageButton.ImageButtonStyle();
        restartButtonStyle.imageUp = restartSkin.getDrawable("RestartButton");

        ImageButton.ImageButtonStyle infoButtonStyle = new ImageButton.ImageButtonStyle();
        infoButtonStyle.imageUp = infoSkin.getDrawable("InfoButton");

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = exitSkin.getDrawable("ExitButton");

        ImageButton.ImageButtonStyle resumeButtonStyle = new ImageButton.ImageButtonStyle();
        resumeButtonStyle.imageUp = resumeSkin.getDrawable("ResumeButton");

        final ImageButton restartButton = new ImageButton(restartButtonStyle);
        final ImageButton infoButton = new ImageButton(infoButtonStyle);
        final ImageButton exitButton = new ImageButton(exitButtonStyle);
        final ImageButton resumeButton = new ImageButton(resumeButtonStyle);

        restartButton.setPosition((Gdx.graphics.getWidth() - restartButton.getWidth())/2, (Gdx.graphics.getHeight() - restartButton.getHeight())/2 + 200);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/2, (Gdx.graphics.getHeight() - exitButton.getHeight())/2 - 200);
        resumeButton.setPosition((Gdx.graphics.getWidth() - resumeButton.getWidth())/2, (Gdx.graphics.getHeight() - resumeButton.getHeight())/2 );

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restartButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.PLAY);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.EXIT);
            }
        });
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resumeButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.RESUME);
            }
        });

        stage.addActor(restartButton);
        stage.addActor(resumeButton);
        stage.addActor(exitButton);
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
    @Override
    public void dispose() {
        if (restartSkin!= null) {
            restartSkin.dispose();
        }

        if (exitSkin!= null) {
            exitSkin.dispose();
        }
        if (resumeSkin!= null) {
            resumeSkin.dispose();
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




