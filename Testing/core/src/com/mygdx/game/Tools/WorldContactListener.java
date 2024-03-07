package com.mygdx.game.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.game.RunnerGame;
import com.mygdx.game.Screens.*;
import com.mygdx.game.Sprites.InteractiveTileObject;

import static com.mygdx.game.RunnerGame.DoubleJump;

public class WorldContactListener implements ContactListener {

    // Use a head and foot sensor to detect when the players hear or foot has hit any of the other objects
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        if(fixA.getUserData() == "head" || fixB.getUserData() == "head" || fixA.getUserData() == "foot" || fixB.getUserData() == "foot"){
            Fixture head = fixA.getUserData() == "head" ? fixA : fixB;
            Fixture object = head == fixA ? fixB : fixA;

            if(object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).onHeadHit();
                if(DoubleJump ){
                    RunnerGame.canJump = 2; // if double jump enabled give two jumps to the player after interaction with object
                } else{
                    RunnerGame.canJump = 1; // else only one jump
                }
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
