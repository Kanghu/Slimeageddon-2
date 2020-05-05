package com.gdx.slimeageddon.model.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.PhysicalObject;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.Location;

abstract public class Weapon extends PhysicalObject {

    private Entity owner;
    private int damage;

    public Weapon(Entity owner, float width, float height) {
        super(owner.getLocation(), width, height);
        this.owner = owner;
    }

    public void hit(Entity entity) {
        if(entity != owner) {
            entity.damaged(this.damage);
        }
    }

    public void equipTo(Entity entity) {
        RevoluteJointDef jointDef = new RevoluteJoint();
        jointDef.initialize(this.getBody(), entity.getBody(), new Vector2(0,0), new Vector2(32, 16));

        jointDef.lowerAngle = -0.5f * 3.14f; // -90 degrees
        jointDef.upperAngle = 0.25f * 3.14f; // 45 degrees

        jointDef.enableLimit = true;
        jointDef.enableMotor = true;

        jointDef.maxMotorTorque = 10.0f;
        jointDef.motorSpeed = 0.0f;

        this.owner = entity;
    }

    public void attack() {
        setState(GameObjectState.ATTACKING);
    }

    /* Apply movement in Box2D object */
}
