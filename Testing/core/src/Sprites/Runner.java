package Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.RunnerGame;

public class Runner extends Sprite {
    public World world;
    public Body b2body;

    public Runner(World world){
        this.world = world;
        defineRunner();
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
