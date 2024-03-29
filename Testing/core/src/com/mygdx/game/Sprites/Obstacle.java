package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.RunnerGame;

public class Obstacle extends InteractiveTileObject{
    public Obstacle(World world, TiledMap map, Rectangle bounds){
        super(world , map , bounds);
        fixture.setUserData(this);
        setCategoryFilter(RunnerGame.BRICK_BIT);
    }

    @Override
    public void onHeadHit() {
        Gdx.app.log("Brick", "Collision");

    }
}
