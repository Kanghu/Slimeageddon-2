package com.gdx.slimeageddon.model.util;

/***
 * Holds all the possible states a GameObject may have.
 * This is necessary to uniquely identify each state in rendering different
 * GameObjects, specifically Entities that may have different states (e.g. moving, attacking).
 */

public enum GameObjectState {
    /* Plain GameObjects have no explicit state */
    DEFAULT,

    /* Default states for Entities */
    BREATHING,
    WALKING,
    JUMPING,
    RECHARGING,
    STUNNED,

    /* Default state for plain animated objects */
    ACTIVE

}
