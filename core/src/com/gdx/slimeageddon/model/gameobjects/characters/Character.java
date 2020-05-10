package com.gdx.slimeageddon.model.gameobjects.characters;

import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.weapons.Weapon;
import com.gdx.slimeageddon.model.util.Location;

/**
 * Character describes a GameObject that is controllable by the player,
 * e.g. has all the functionality specific to an in-game character.
 */

public abstract class Character extends Entity {

    protected Weapon weapon;

    public Character(Location loc, float width, float height) {
        super(loc, width, height);
    }

    public void setWeapon(Weapon weap)
    {
        this.weapon = weap;
        this.weapon.equipTo(this);
        this.addChildObject(weap);
    }

    public Weapon getWeapon() { return this.weapon; }

    public void attack()
    {
        this.weapon.attack();
    }

    /***
     * The following are generic abilities that are left
     * to the implementation of each specific class.
     */

    abstract public void skill_1();

    abstract public void skill_2();

    abstract public void skill_3();

    abstract public void skill_4();
}
