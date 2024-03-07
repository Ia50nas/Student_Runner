package com.mygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mygdx.game.RunnerGame;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import java.awt.*;

import static com.mygdx.game.RunnerGame.*;

public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;
    public static Integer worldTimer;
    private float timeCount;
    public static Integer score;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label RunnerLabel;

    public  Hud(SpriteBatch sb){
        timeCount = 0;

        // initialize the variables and update in each stage
        if(Level1){
            score = 0;
            worldTimer = 300;
        } else if (Level2) {
            score = Score;
            worldTimer = WorldTimer;

        } else if (Level3) {
            score = Score;
            worldTimer = WorldTimer;
        }


        viewport = new FitViewport(RunnerGame.V_WIDTH ,RunnerGame.V_HEIGHT,new OrthographicCamera());
        stage = new Stage(viewport, sb);


        // create a table which include the hud and all the information
        Table table = new Table();
        table.top();
        table.setFillParent(true);


        // create all labels
        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("Timer", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // Set the values based on the map
        if(Level1){
            levelLabel = new Label("Level 1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            worldLabel = new Label("Leipzig Town", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            RunnerLabel = new Label("Runner", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        } else if (Level2) {
            levelLabel = new Label("Level 2", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            worldLabel = new Label("Sea", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            RunnerLabel = new Label("Diver", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        } else if (Level3) {
            levelLabel = new Label("Level 3", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            worldLabel = new Label("Lancaster's Corridor", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
            RunnerLabel = new Label("Student", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        }


        table.add(RunnerLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();
        table.add(scoreLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);

    }

    // update the timer on the hud
    public  void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }

    // increase the score of the player and show new score
    public static void addScore(int value){
        score+= value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
