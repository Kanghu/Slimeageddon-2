package com.gdx.slimeageddon.model;

/***
 * Abstract class representing a game object from which all other
 * game objects are derived.
 */

abstract public class GameObject {

    /***
     * The object's location in game's representation
     */
    Location location;

    float width, height;

    public GameObject(Location loc){
        this.location = loc;
    }

    /** Setters and getters **/

    public Location getLocation(){
        return this.location;
    }

    public void setLocation(Location loc){
        this.location = loc;
    }

    public float getWidth() { return this.width; }

    public float getHeight() { return this.height; }

    public void setWidth(float w) { this.width = w; }

    public void setHeight(float h) { this.height = h; }
}
