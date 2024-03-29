package com.mygdx.game.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Scenes.Hud;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.RunnerGame.*;

public class CourseWork extends InteractiveTileObject{
    private boolean needsTeleportation = false;
    private static TiledMapTileSet set;
    private float x, y;
    private TextureRegion region;
    private PlayScreen playScreen;

    public CourseWork(World world, TiledMap map, Rectangle bounds, PlayScreen playScreen) {
        super(world, map, bounds);
        fixture.setUserData(this);
        setCategoryFilter(RunnerGame.COURSEWORK_BIT);

        this.x = bounds.getX() / RunnerGame.PPM;
        this.y = bounds.getY() / RunnerGame.PPM;
        this.playScreen = playScreen;

        if(Level1) {
            region = new TextureRegion(new Texture(Gdx.files.internal("Tilemaps/Coursework.png")));
        } else if (Level2) {
            region = new TextureRegion(new Texture(Gdx.files.internal("Tilemaps/Coursework_L2.png")));
        } else if (Level3) {
            region = new TextureRegion(new Texture(Gdx.files.internal("Tilemaps/CourseworkMarked.png")));
        }

    }

    public void render(SpriteBatch batch) {
        batch.begin();
        batch.draw(region, x, y, region.getRegionWidth() / RunnerGame.PPM, region.getRegionHeight() / RunnerGame.PPM);
        batch.end();
    }

    // When Player hits this object edit the score and play sound
    @Override
    public void onHeadHit() {
        Gdx.app.log("Coursework", "Collision");
        setCategoryFilter(RunnerGame.DESTROYED_BIT);
        playScreen.removeCourseWork(this);
        Hud.addScore(20);
        RunnerGame.manager.get("Audio/sounds/Coursework.mp3", Sound.class).play();
    }


}


