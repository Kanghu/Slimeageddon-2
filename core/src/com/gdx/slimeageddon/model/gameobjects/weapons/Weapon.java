package com.gdx.slimeageddon.model.gameobjects.weapons;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.*;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.PhysicalObject;
import com.gdx.slimeageddon.model.gameobjects.characters.Character;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.Location;

abstract public class Weapon extends PhysicalObject {

    private Entity owner;
    private int damage;

    public Weapon(Location loc, float width, float height) {
        super(loc, width, height);
        setBodyType(BodyDef.BodyType.DynamicBody);
    }

    public Weapon(Entity owner, float width, float height) {
        this(owner.getLocation(), width, height);
        this.owner = owner;
    }

    public void hit(Entity entity) {
        if(entity != owner) {
            entity.damaged(this.damage);
        }
    }

    public JointDef getJointDef()
    {
        if(owner != null) {
            Entity entity = owner;

            WeldJointDef jointDef = new WeldJointDef();
            jointDef.initialize(this.getBody(), entity.getBody(),
                                entity.getBody().getPosition());

            return jointDef;
        }

        return null;
    }

    public void equipTo(Character chr) {
        owner = chr;
    }

    public void attack() {
        setState(GameObjectState.ATTACKING);
    }

    @Override
    public void initFixtureDef() {
        super.initFixtureDef();
        fixtureDef.density = 0.01f;
        fixtureDef.isSensor = true;
    }

    /* Apply movement in Box2D object */
}
