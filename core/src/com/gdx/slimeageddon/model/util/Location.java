package com.gdx.slimeageddon.model.util;

public class Location {
    float x, y;

    public Location(float x, float y){
        this.x = x;
        this.y = y;
    }

    public float getX(){
        return this.x;
    }

    public float getY(){
        return this.y;
    }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }
}
