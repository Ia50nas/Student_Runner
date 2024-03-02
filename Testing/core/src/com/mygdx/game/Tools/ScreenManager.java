package com.mygdx.game.Tools;

import com.badlogic.gdx.Screen;
import com.mygdx.game.RunnerGame;

public class ScreenManager {
    private final RunnerGame game;
    private Screen PreviousScreen;
    private Screen CurrentScreen;

    public ScreenManager(RunnerGame game){
        this.game = game;
        this.CurrentScreen = null;
    }
    public void putScreen(RunnerGame.Screen_Type screenType){
        if (screenType==RunnerGame.Screen_Type.RESUME || screenType==RunnerGame.Screen_Type.INFO) {
            Screen temp = CurrentScreen;
            CurrentScreen = PreviousScreen;
            PreviousScreen = temp;
            game.setScreen(CurrentScreen);
            return;
        }


    }
}
