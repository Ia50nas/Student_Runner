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

public class LoseScreen implements Screen {
    private  final RunnerGame game;
    private final ScreenManager screenManager;
    private final Stage stage;
    private final TextureRegion Bg;
    private Skin ScreenBSkin;
    public LoseScreen(RunnerGame game, ScreenManager screenManager) {
        this.game =game;
        this.screenManager = screenManager;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Bg = new TextureRegion(new Texture("Art/Lose_Screen.png"));

        RunnerGame.manager.get("Audio/sounds/Lose.mp3", Sound.class).play();

        initButtons();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);           //clear screen

        game.batch.begin();

        // background and sound for Losing the game
        game.batch.draw(Bg, 0, 0, Bg.getRegionWidth() * 1.6f, Bg.getRegionHeight() * 0.8f);
        game.batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    // Initialize all buttons that are on the screen
    // Create the skin = image and its use
    public void initButtons() {
        Texture ScreenBTexture = new Texture(Gdx.files.internal("Buttons/Screen-Button.png"));

        ScreenBSkin = new Skin();

        ScreenBSkin.add("CrossButton", ScreenBTexture);


        ImageButton.ImageButtonStyle ScreenBButtonStyle = new ImageButton.ImageButtonStyle();
        ScreenBButtonStyle.imageUp = ScreenBSkin.getDrawable("CrossButton");


        final ImageButton ScreeBButton = new ImageButton(ScreenBButtonStyle);


        ScreeBButton.setPosition(Gdx.graphics.getWidth()/2 - ScreeBButton.getWidth()/2 , Gdx.graphics.getHeight()/2 -ScreeBButton.getHeight()/2 );

        ScreeBButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreeBButton.setChecked(false);
                screenManager.putScreen(RunnerGame.Screen_Type.EXIT);
            }
        });


        stage.addActor(ScreeBButton);
    }
    @Override
    public void dispose() {
        if (ScreenBSkin!= null) {
            ScreenBSkin.dispose();
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
