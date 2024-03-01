package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.PlayScreen;

import static com.mygdx.game.RunnerGame.*;

public class Runner extends Sprite {
    public enum State{FALLING, JUMPING,STANDING, RUNNING};
    public  State CurrentState;
    public  State PreviousState;
    public World world;
    public Body b2body;
    private TextureRegion RunnerStand;
    private Animation RunnerRun;
    private  Animation RunnerJump;
    private float stateTimer;
    private  boolean RunningRight;

    public Runner(World world, PlayScreen screen){
        super(screen.getAtlas().findRegion("Run_Right1"));
        this.world = world;
        CurrentState = State.STANDING;
        PreviousState = State.STANDING;
        stateTimer = 0;
        RunningRight = true;

    Array<TextureRegion> frames = new Array<TextureRegion>();
        if(Level1 ) {
            frames.add(new TextureRegion(getTexture(), 1, 42, 17, 17));
            frames.add(new TextureRegion(getTexture(), 1, 23, 17, 17));
            frames.add(new TextureRegion(getTexture(), 67, 42, 17, 17));
            frames.add(new TextureRegion(getTexture(), 1, 4, 17, 17));
        } else if (Level2) {
            frames.add(new TextureRegion(getTexture(), 441, 35, 18, 24));
            frames.add(new TextureRegion(getTexture(), 461, 35, 18, 24));
            frames.add(new TextureRegion(getTexture(), 481, 35, 18, 24));
            frames.add(new TextureRegion(getTexture(), 135, 1, 18, 24));
        }else if (Level3) {
            frames.add(new TextureRegion(getTexture(), 1, 42, 17, 17));
            frames.add(new TextureRegion(getTexture(), 1, 23, 17, 17));
            frames.add(new TextureRegion(getTexture(), 67, 42, 17, 17));
            frames.add(new TextureRegion(getTexture(), 1, 4, 17, 17));
        }
        RunnerRun = new Animation(0.1f, frames);
        frames.clear();



        if(Level1 ) {
            frames.add(new TextureRegion(getTexture(), 1, 42, 17, 17));
            RunnerJump = new Animation(0.1f, frames);
        }
        if(Level2){
            frames.add(new TextureRegion(getTexture(), 441, 35, 18, 24));
            RunnerJump = new Animation(0.1f, frames);
        }
        if(Level3) {
            frames.add(new TextureRegion(getTexture(), 1, 42, 17, 17));
            RunnerJump = new Animation(0.1f, frames);
        }
        defineRunner();

        if(Level1) {
            frames.add(new TextureRegion(getTexture(), 155, 8, 16,17));
            RunnerStand = new TextureRegion(getTexture(), 155, 8, 17, 17);
        }
        if(Level2){
            frames.add(new TextureRegion(getTexture(), 135, 1, 18,24));
            RunnerStand = new TextureRegion(getTexture(), 135, 1, 18, 24);
        }
        if(Level3) {
            frames.add(new TextureRegion(getTexture(), 155, 8, 16,17));
            RunnerStand = new TextureRegion(getTexture(), 155, 8, 17, 17);
        }

        setBounds(0,0,48/RunnerGame.PPM,48/RunnerGame.PPM);
        setRegion(RunnerStand);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));

        System.out.println("Player Position: (" + b2body.getPosition().x + ", " + b2body.getPosition().y + ")");

    }

    private TextureRegion getFrame(float dt) {
        CurrentState = getState();

        TextureRegion region;
        switch (CurrentState){
            case JUMPING:
                region = (TextureRegion) RunnerJump.getKeyFrame(stateTimer);
                break;
            case FALLING:
            case RUNNING:
                region = (TextureRegion) RunnerRun.getKeyFrame(stateTimer, true);
                break;
            case STANDING:
                region = RunnerStand;
                break;
            default:
                region = RunnerStand;
                break;
        }

    if((b2body.getLinearVelocity().x < 0 || !RunningRight) && !region.isFlipX()){
        region.flip(true, false);
        RunningRight = false;
    }
    else if((b2body.getLinearVelocity().x > 0 || RunningRight) && region.isFlipX()){
        region.flip(true, false);
        RunningRight = true;
    }
    stateTimer = CurrentState == PreviousState ? stateTimer + dt : 0;
    PreviousState = CurrentState;
    return region;
    }
    public State getState(){
        if(b2body.getLinearVelocity().y > 0)
            return State.JUMPING;
        else if(b2body.getLinearVelocity().y < 0 || (b2body.getLinearVelocity().y < 0 && PreviousState == State.JUMPING ))
            return State.FALLING;
        else if(b2body.getLinearVelocity().x != 0)
            return  State.RUNNING;
        else
            return  State.STANDING;
        }


    public  void defineRunner(){
        BodyDef bdef = new BodyDef();
        if(Level1){
            bdef.position.set(1, 10); //Test = (60,10)
        } else if (Level2) {
            bdef.position.set(55,1);    //Test = (60,1)
        } else if (Level3) {
            bdef.position.set(89, 9);   //Test = (89,9)
        }

        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6 / RunnerGame.PPM);
        fdef.filter.categoryBits = RunnerGame.RUNNER_BIT;
        fdef.filter.maskBits = RunnerGame.DEFAULT_BIT | RunnerGame.COURSEWORK_BIT | RunnerGame.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/RunnerGame.PPM,7/RunnerGame.PPM), new Vector2(7/RunnerGame.PPM,5/RunnerGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;

        b2body.createFixture(fdef).setUserData("head");

        // Create a new fixture for the foot sensor
        PolygonShape footSensor = new PolygonShape();
        footSensor.setAsBox(5 / RunnerGame.PPM, 2 / RunnerGame.PPM, new Vector2(0, -6 / RunnerGame.PPM), 0);

        FixtureDef footFixtureDef = new FixtureDef();
        footFixtureDef.shape = footSensor;
        footFixtureDef.isSensor = true; // Set the sensor flag to true

        // Set the collision category and mask bits for the foot sensor fixture
        footFixtureDef.filter.categoryBits = RunnerGame.RUNNER_BIT;
        footFixtureDef.filter.maskBits = RunnerGame.DEFAULT_BIT | RunnerGame.COURSEWORK_BIT | RunnerGame.BRICK_BIT;

        // Create the foot sensor fixture and attach it to the runner's body
        b2body.createFixture(footFixtureDef).setUserData("foot");
    }
}
