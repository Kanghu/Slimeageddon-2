package com.gdx.slimeageddon.model;

import com.badlogic.gdx.physics.box2d.Body;

/***
 * Simple GameObject that has a physical dimension, that is,
 * a body within the physics engine (Box2D).
 */

public class PhysicalObject extends GameObject {

    /** The physical body **/
    Body body;

    public PhysicalObject(Location loc){
        super(loc);
    }

    public PhysicalObject(Location loc, Body body){
        super(loc);
    }

    /***
     * Update GameObject's Location according to the body's
     * position.
     */
    public void updateLocation(){
        this.getLocation().setX(body.getPosition().x);
        this.getLocation().setY(body.getPosition().y);
    }

    /***
     * Update body according to the GameObject's Location
     */
    protected void updateBody(){
        this.body.getPosition().set(this.getLocation().getX(),
                                    this.getLocation().getY()
        );
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

}
