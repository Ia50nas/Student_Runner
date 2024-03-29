package com.mygdx.game.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.PlayScreen;
import com.mygdx.game.Sprites.*;

import java.util.LinkedList;

public class B2WorldCreator {

    private final PlayScreen playScreen;
    public B2WorldCreator(World world, TiledMap map, LinkedList<CourseWork> courseworks, PlayScreen playScreen) {
        //create body definition for objects
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        this.playScreen = playScreen;

        // By using TILED we have layers to the bodies we create. Background layers are just visuals that are loaded at once but different body layers have to be created.
        // Layer count starts from 0 0 - 9 are just visuals and backgrounds

        //  create cone body
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Cone(world,map,rect);
        }
        //create Ground body
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Obstacle(world,map,rect);
        }
        //create Boundaries body
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Obstacle(world,map,rect);
        }
        //create car Bodies
        for (MapObject object : map.getLayers().get(13).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Obstacle(world,map,rect);
        }
        //create wall Bodies
        for (MapObject object : map.getLayers().get(14).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Obstacle(world,map,rect);
        }
        //create fire Bodies
        for (MapObject object : map.getLayers().get(15).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Fire(world,map,rect);
        }
        //create coursework body
        for (MapObject object : map.getLayers().get(16).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            courseworks.add(new CourseWork(world,map,rect, playScreen));
        }
        //create Mine Bodies
        for (MapObject object : map.getLayers().get(17).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Mine(world,map,rect);
        }
        //create urchin bodies
        for (MapObject object : map.getLayers().get(18).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Urchin(world,map,rect);
        }
        //create Book bodies
        for (MapObject object : map.getLayers().get(19).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Book(world,map,rect);
        }
        //create water bodies
        for (MapObject object : map.getLayers().get(20).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Water(world,map,rect);
        }
    }
}
