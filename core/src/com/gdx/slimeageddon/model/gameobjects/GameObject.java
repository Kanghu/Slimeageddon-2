package com.gdx.slimeageddon.model.gameobjects;

import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.GameObjectType;
import com.gdx.slimeageddon.model.util.Location;

/***
 * Abstract class representing a game object from which all other
 * game objects are derived.
 */

public class GameObject {

    /***
     * The ID is an unique identifier pertant to a specific GameObject.
     * This identifies the specific GameObject across all other GameObjects (unique per object).
     */
    private int ID;

    /***
     * The GameObject type. This identifies specific classes of GameObjects from
     * other types deriving from the same parent. (unique per class).
     */
    private GameObjectType type = GameObjectType.DEFAULT;

    /***
     * The GameObject state. This identifies specific states in which
     * this GameObject may be in. For plain GameObjects, a Default state
     * is assumed.
     */
    private GameObjectState state = GameObjectState.DEFAULT;

    private String name = "GameObject";

    /***
     * The object's location in game's representation.
     */
    protected Location location;

    /***
     * Width and height of the physical object.
     * X and Y are the bottom-left corner of this shape, by convention.
     */
    private float width, height;

    public GameObject(Location loc){
        this.location = loc;

        /* Simple GameObject has no physical size by default */
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

    public int getID() { return this.ID; }

    public void setWidth(float w) { this.width = w; }

    public void setHeight(float h) { this.height = h; }

    public GameObjectType getType() {
        return this.type;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }

    public void setName(String name) { this.name = name; }

    public GameObjectState getState() { return this.state; }

    public void setState(GameObjectState state) { this.state = state; }

    @Override
    public String toString(){
        return this.name;
    }

}
