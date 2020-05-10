package com.gdx.slimeageddon.model.gameobjects.weapons;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.characters.Character;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.GameObjectType;
import com.gdx.slimeageddon.model.util.Location;

public class Axe extends Weapon {

    public static final float TORQUE = 0.5f;

    public Axe(Location loc) {
        super(loc, 64, 64);
        setType(GameObjectType.AXE);
        setState(GameObjectState.IDLE);
    }

    public Axe(Entity owner) {
        super(owner, 64, 64);
        setType(GameObjectType.AXE);
        setState(GameObjectState.IDLE);
        equipTo((Character) owner);
    }

    @Override
    public void attack() {
        this.getBody().applyTorque(Axe.TORQUE, true);
    }
}
