package com.gdx.slimeageddon.view.util;

import com.gdx.slimeageddon.model.util.GameObjectType;

/***
 * TextureSheet represents an enum that holds all the filepaths to the
 * game's texture resources. This is an intermediary layer between a
 * GameObject and its graphical representation.
 */

public enum TextureSheet {

    NONE ("", GameObjectType.DEFAULT),
    MAP_VALLEY ("resources/Sprites/Maps/map_1.png", GameObjectType.MAP_VALLEY),
    BRIDGE ("resources/Sprites/Objects/Bridge.png", GameObjectType.BRIDGE),
    PORTAL ("resources/Sprites/Objects/Portal.png", GameObjectType.PORTAL);

    private final String texturePath;
    private final GameObjectType type;

    TextureSheet(String texture, GameObjectType type){
        this.texturePath = texture;
        this.type = type;
    }

    public String getTexturePath(){
        return this.texturePath;
    }

    public GameObjectType getType() { return this.type; }

    public static TextureSheet getTextureByType(GameObjectType type){
        for(TextureSheet txt : TextureSheet.values()){
            if(txt.getType() == type){
                return txt;
            }
        }

        return TextureSheet.NONE;
    }
}
