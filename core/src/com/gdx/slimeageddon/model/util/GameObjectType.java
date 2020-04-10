package com.gdx.slimeageddon.model.util;

/***
 * Holds the identifier of each type of GameObject there is in the game.
 * This is required to inform the view of differences in rendering each GameObject */


public enum GameObjectType {
     /* Default GameObjects have no inherent visualization */
     DEFAULT,

    /* Maps */
    MAP_VALLEY,         /* The default map */

    /* Static objects */
    BRIDGE,

    /* Animated objects */
    PORTAL,

    /* Entities */
    VIKING,
    SAMURAI,
    WIZARD,

    /* Weapons */
    AXE,
    DAGGER,
    WAND
}
