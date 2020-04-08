package com.gdx.slimeageddon.model.gameobjects;

import com.gdx.slimeageddon.model.util.Location;

/***
 * Abstract class representing a game object from which all other
 * game objects are derived.
 */

public class GameObject {

    public String name = "GameObject";

    /***
     * The object's location in game's representation
     */
    protected Location location;

    float width, height;

    public GameObject(Location loc){
        this.location = loc;
        setWidth(0);
        setHeight(0);
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

    public void setName(String name) { this.name = name; }

    @Override
    public String toString(){
        return this.name;
    }

}
