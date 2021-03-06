package com.gdx.slimeageddon.model.gameobjects;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.gdx.slimeageddon.model.util.Location;

/***
 * Simple GameObject that has a physical dimension, that is,
 * a body within the physics engine (Box2D).
 */

public class PhysicalObject extends GameObject {

    /***
     * Defines the offset between the object's true location
     * and its location within the physics engine.
     *
     * The object has a size within the engine of: size / PHYSICS_RATIO
     */
    static final float PHYSICS_RATIO = 100f;

    /**
     * The Box2D Body object
     */
    private Body body;

    /***
     * The Box2D Body's type
     */
    private BodyDef.BodyType bodyType;

    /***
     * This object's internal fixture definition (Box2D)
     */
    public FixtureDef fixtureDef = new FixtureDef();

    public PhysicalObject(Location loc){
        super(loc);
        initFixtureDef();
    }

    public PhysicalObject(Location loc, float width, float height){
        this(loc);
        setWidth(width);
        setHeight(height);

        /* By default, the body is instantiated as dynamic */
        this.bodyType = BodyDef.BodyType.DynamicBody;
    }

    public PhysicalObject(Location loc, float width, float height, BodyDef.BodyType bodyType){
        this(loc, width, height);
        this.bodyType = bodyType;
    }

    public void initFixtureDef() {

        /* Default fixture definitions */
        fixtureDef.density = 1f;
        fixtureDef.friction = 0f;
    }

    /***
     * Update GameObject's Location according to the body's
     * position. The object's coordinates are translated
     * into Box2D coordinates and any offset is taken into consideration.
     */
    public void updateLocation(float worldWidth, float worldHeight){
        /* Adjust for physics engine ratio and different origin (bottom-left vs center) */
        float realX = (body.getPosition().x * PHYSICS_RATIO) - (this.getWidth() / 2);
        float realY = (body.getPosition().y * PHYSICS_RATIO) - (this.getHeight() / 2);

        /* Adjust for different coordinate system (bottom-left vs center) */
        realX = realX + (worldWidth / 2);
        realY = realY + (worldHeight / 2);

        this.location.setX(realX);
        this.location.setY(realY);
    }

    /***
     * Update body according to the GameObject's Location.
     */
    protected void updateBody(){
        this.body.getPosition().set(this.getLocation().getX(),
                                    this.getLocation().getY());
    }

    /** Setters and getters **/

    @Override
    public void setLocation(Location loc){
        super.setLocation(loc);
        this.updateBody();
    }

    public void setBody(Body body){
        this.body = body;
    }

    public Body getBody(){
        return this.body;
    }

    public BodyDef.BodyType getBodyType() { return this.bodyType; }

    public void setBodyType(BodyDef.BodyType type) { this.bodyType = type; }

}
