package com.gdx.slimeageddon.view.util;

import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.GameObjectType;

/***
 * TextureSheet represents an enum that holds all the filepaths to the
 * game's texture resources. This is an intermediary layer between a
 * GameObject and its graphical representation.
 */

public enum TextureSheet {

    /* Static sprites with DEFAULT state */
    NONE ("", GameObjectType.DEFAULT, GameObjectState.DEFAULT),
    MAP_VALLEY ("resources/Sprites/Maps/map_1.png", GameObjectType.MAP_VALLEY, GameObjectState.DEFAULT),
    BRIDGE ("resources/Sprites/Objects/Bridge.png", GameObjectType.BRIDGE, GameObjectState.DEFAULT),

    /* Objects with a single active state */
    PORTAL ("resources/Sprites/Objects/Atlas/portal_atlas.txt", GameObjectType.PORTAL, GameObjectState.ACTIVE),

    /* Character sprites that are state-dependent */
    VIKING_BREATHING ("resources/Sprites/Characters/Viking/Atlas/breathe_atlas.txt", GameObjectType.VIKING, GameObjectState.BREATHING),
    VIKING_WALKING ("resources/Sprites/Characters/Viking/Atlas/walking_atlas.txt", GameObjectType.VIKING, GameObjectState.WALKING),
    VIKING_JUMPING ("resources/Sprites/Characters/Viking/Atlas/jump_atlas.txt", GameObjectType.VIKING, GameObjectState.JUMPING),
    VIKING_RECHARGING ("resources/Sprites/Characters/Viking/Atlas/recharge_atlas.txt", GameObjectType.VIKING, GameObjectState.RECHARGING),
    VIKING_STUNNED ("resources/Sprites/Characters/Viking/Atlas/daze_atlas.txt", GameObjectType.VIKING, GameObjectState.STUNNED),

    /* Weapons */
    AXE_IDLE ("resources/Sprites/Characters/Viking/Atlas/idle_axe_atlas.txt", GameObjectType.AXE, GameObjectState.IDLE),
    AXE_ATTACKING ("resources/Sprites/Characters/Viking/Atlas/attack_atlas.txt", GameObjectType.AXE, GameObjectState.ATTACKING);



    private final String texturePath;
    private final GameObjectType type;
    private final GameObjectState state;

    TextureSheet(String texture, GameObjectType type, GameObjectState state){
        this.texturePath = texture;
        this.state = state;
        this.type = type;
    }

    public String getTexturePath(){
        return this.texturePath;
    }

    public GameObjectType getType() { return this.type; }

    public GameObjectState getState() { return this.state; }

    public static TextureSheet getDefaultTexture(GameObjectType type) {
        return getTexture(type, GameObjectState.DEFAULT);
    }

    public static TextureSheet getTexture(GameObjectType type, GameObjectState state) {
        for(TextureSheet txt : TextureSheet.values()){
            if(txt.getType() == type && txt.getState() == state){
                return txt;
            }
        }

        return TextureSheet.NONE;
    }
}
