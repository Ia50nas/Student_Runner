package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Scenes.Hud;

public class Urchin extends InteractiveTileObject {
    public Urchin(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(RunnerGame.OBSTACLE_BIT);
    }

    // When Player hits this object edit the score and play sound
    @Override
    public void onHeadHit() {
        Gdx.app.log("Urchin", "Collision");
        Hud.addScore(-20);
        RunnerGame.manager.get("Audio/sounds/Urchin_Hit.mp3", Sound.class).play();
    }
}
