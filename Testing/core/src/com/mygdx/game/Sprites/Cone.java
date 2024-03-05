package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Scenes.Hud;

public class Cone extends InteractiveTileObject {
    public Cone(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(RunnerGame.OBSTACLE_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Cone", "Collision");
        Hud.addScore(-5);
        RunnerGame.manager.get("Audio/sounds/Game_hit.mp3", Sound.class).play();
    }
}
