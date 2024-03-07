package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.*;

public final class ScreenManager {
    private final RunnerGame game;
    private Screen previousScreen;
    private Screen currentScreen;

    public ScreenManager(RunnerGame game){
        this.game = game;
        this.currentScreen = null;
    }
    public void putScreen(RunnerGame.Screen_Type screenType){
        if (screenType == RunnerGame.Screen_Type.RESUME) {
            Screen temp = currentScreen;
            currentScreen = previousScreen;
            previousScreen = temp;
            game.setScreen(currentScreen);
            currentScreen.resume();
            return;
        }

        if(previousScreen != null) {
            previousScreen.dispose();
        }

        if (currentScreen != null) {
            previousScreen = currentScreen;
        }

        // Detect and create when a new screen has to be projected
        switch (screenType) {
            case START:
                currentScreen = new StartScreen(game,this);
                break;
            case MENU:
                assert currentScreen != null;
                currentScreen = new MenuScreen(game,this);
                break;
            case PLAY:
                currentScreen = new PlayScreen(game, this);
                break;
            case INFO:
                currentScreen = new InfoScreen(game,this);
                break;
            case WIN:
                currentScreen = new WinScreen(game,this);
                break;
            case LOSE:
                currentScreen = new LoseScreen(game,this);
                break;
            case EXIT:
                Gdx.app.exit();
                break;
            default:
                break;
        }

        game.setScreen(currentScreen);
    }
}
