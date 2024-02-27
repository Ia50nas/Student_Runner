package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private Skin playSkin;

    private Skin exitSkin;

    public MenuScreen(RunnerGame game) {
        this.game = game;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        initButtons();
    }

    public void initButtons() {
        Texture playTexture = new Texture(Gdx.files.internal("MenuScreen/Play.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("MenuScreen/Exit.png"));
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
        playButton.setPosition((Gdx.graphics.getWidth() - playButton.getWidth())/2, (Gdx.graphics.getHeight() - playButton.getHeight())/2);
        exitButton.setPosition((Gdx.graphics.getWidth() - exitButton.getWidth())/3, (Gdx.graphics.getHeight() - exitButton.getHeight())/3);

        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playButton.setChecked(false);
                game.setScreen(new PlayScreen(game));
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                exitButton.setChecked(false);
                game.setScreen(new PlayScreen(game));
            }
        });
        stage.addActor(exitButton);
        stage.addActor(playButton);

    }

    @Override
    public void render(float delta) {
        stage.draw();
    }
    @Override
    public void dispose() {
        playSkin.dispose();
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




