package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.InfoScreen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Screens.StartScreen;

public class ScreenManager {
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
            return;
        }

        if(previousScreen != null) {
            previousScreen.dispose();
        }

        if (currentScreen != null) {
            previousScreen = currentScreen;
        }

        switch (screenType) {
            case START:
                currentScreen = new StartScreen(game,this);
                break;
            case MENU:
                currentScreen = new MenuScreen(game,this);
                break;
            case PLAY:
                currentScreen = new PlayScreen(game, this);
                break;
            case INFO:
                currentScreen = new InfoScreen(game,this);
                break;
            case WIN:
            case LOSE:
            case EXIT:
                Gdx.app.exit();
                break;
            default:
                break;
        }

        game.setScreen(currentScreen);
    }
}
