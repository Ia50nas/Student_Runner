package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Scenes.Hud;

public class Mine extends InteractiveTileObject {
    public Mine(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(RunnerGame.FIRE_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Mine", "Collision");
        Hud.addScore(-20);
        RunnerGame.manager.get("audio/sounds/Mine_Hit.wav", Sound.class).play();
    }
}