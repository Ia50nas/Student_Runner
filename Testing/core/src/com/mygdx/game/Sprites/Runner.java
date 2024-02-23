package com.mygdx.game.Sprites;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.PlayScreen;

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
            frames.add(new TextureRegion(getTexture(), 87, 16, 17,17));
            frames.add(new TextureRegion(getTexture(), 106, 16, 17,17));
            frames.add(new TextureRegion(getTexture(), 125, 16, 17,17));
            frames.add(new TextureRegion(getTexture(), 144, 16, 17,17));
        RunnerRun = new Animation(0.1f, frames);
        frames.clear();


        frames.add(new TextureRegion(getTexture(), 87, 16, 17,17));
        RunnerJump = new Animation(0.1f, frames);

        defineRunner();


        frames.add(new TextureRegion(getTexture(), 163, 24, 16,17));
        RunnerStand = new TextureRegion(getTexture(), 163, 24, 17, 17);

        setBounds(0,0,48/RunnerGame.PPM,48/RunnerGame.PPM);
        setRegion(RunnerStand);
    }
    public void update(float dt){
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight()/2);
        setRegion(getFrame(dt));
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
        bdef.position.set(32 / RunnerGame.PPM, 32 / RunnerGame.PPM);
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
    }
}
