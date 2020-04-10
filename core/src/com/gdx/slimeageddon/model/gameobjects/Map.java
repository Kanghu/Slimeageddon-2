package com.gdx.slimeageddon.model.gameobjects;

import com.gdx.slimeageddon.model.util.GameObjectType;
import com.gdx.slimeageddon.model.util.Location;

public class Map extends GameObject {

    public static GameObjectType TYPE;

    public Map(Location loc, float width, float height){
        super(loc);
        setWidth(width);
        setHeight(height);

        /* Default map */
        TYPE = GameObjectType.MAP_VALLEY;
    }

    public void instantiateMap(GameObjectType map){
        this.TYPE = map;
    }
}
