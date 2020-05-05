package com.gdx.slimeageddon.model.gameobjects.weapons;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.GameObjectType;

public class Axe extends Weapon {

    public static final float TORQUE = 0.5f;

    public Axe(Entity owner) {
        super(owner, 64, 64);
        setType(GameObjectType.AXE);
        setState(GameObjectState.IDLE);

        this.setBodyType(BodyDef.BodyType.KinematicBody);
    }

    @Override
    public void attack() {
        this.getBody().applyTorque(Axe.TORQUE, true);
    }
}
