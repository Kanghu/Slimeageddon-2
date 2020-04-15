package com.gdx.slimeageddon.model.gameobjects;

import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.Location;
import com.gdx.slimeageddon.model.util.Direction;

/***
 * Entity describes a PhysicalObject that is able of moving, that is,
 * exerting a force onto itself in an arbitrary direction (sideways or upwards).
 *
 * This entails an additional field: direction, yielding a characteristic beyond that of Location.
 */

public class Entity extends PhysicalObject {

    /**
     * The direction we are currently facing
     */
    private Direction direction = Direction.LEFT;

    /**
     * The speed we are capable of moving at
     */
    float movingSpeed = 2f;

    /**
     * The force we may apply upwards (jumping)
     * @param loc
     */
    float jumpingSpeed = 98f;

    public Entity(Location loc){
        super(loc);
    }

    public Entity(Location loc, float width, float height){
        super(loc, width, height);
        setState(GameObjectState.BREATHING);
    }

    /***
     * Apply a linear velocity of 'movingSpeed' according to 'direction'
     */
    public void move(){
        switch(this.direction){
            case RIGHT:
                this.getBody().setLinearVelocity(movingSpeed, 0f);
                break;
            case LEFT:
                this.getBody().setLinearVelocity((-1) * movingSpeed, 0f);
                break;
        }
    }

    /**
     * Apply an upwards force equal to 'jumpingSpeed'
     */
    public void jump(){
        this.getBody().applyForceToCenter(0f, jumpingSpeed, true);
    }

    /**
     * Turn the direction towards the opposite
     */
    public void turn(Direction dir){
        this.direction = dir;
    }

    /** Setters and getters **/

    public void setDirection(Direction direction){
        this.direction = direction;
    }

    public Direction getDirection(){
        return this.direction;
    }

    public float getMovingSpeed(){
        return this.movingSpeed;
    }

    public float getJumpingSpeed(){
        return this.jumpingSpeed;
    }

    public void setMovingSpeed(float ms){
        this.movingSpeed = ms;
    }

    public void setJumpingSpeed(float js){
        this.jumpingSpeed = js;
    }

    public boolean isMoving() { return (getState() == GameObjectState.WALKING); }

    public void toggleMoving() {
        if(getState() != GameObjectState.WALKING) {
            setState(GameObjectState.WALKING);
        } else {
            setState(GameObjectState.BREATHING);
        }

        if(isMoving()){
            switch(this.direction){
                case RIGHT:
                    this.getBody().setLinearVelocity(movingSpeed, 0f);
                    break;
                case LEFT:
                    this.getBody().setLinearVelocity((-1) * movingSpeed, 0f);
                    break;
            }
        } else {
            this.getBody().setLinearVelocity(0f, 0f);
        }
    }
}
