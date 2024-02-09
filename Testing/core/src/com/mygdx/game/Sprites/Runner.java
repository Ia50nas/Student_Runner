package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.PlayScreen;

public class Runner extends Sprite {
    public World world;
    public Body b2body;
    private TextureRegion RunnerStand;
    public Runner(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Run_Right1"));
        this.world = world;
        defineRunner();
        RunnerStand = new TextureRegion(getTexture(), 87, 16, 17, 17);
        setBounds(0,0,48/RunnerGame.PPM,48/RunnerGame.PPM);
        setRegion(RunnerStand);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);
    }
    public  void defineRunner(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32 / RunnerGame.PPM, 32 / RunnerGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / RunnerGame.PPM);

        fdef.shape = shape;
        b2body.createFixture(fdef);
    }
}
