package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.RunnerGame;

public class Fire extends InteractiveTileObject {
    public Fire(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(RunnerGame.FIRE_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Fire", "Collision");

        // Play the sound effect for touching the fire block
        RunnerGame.manager.get("audio/sounds/fire_hit.wav", Sound.class).play();
    }
}
