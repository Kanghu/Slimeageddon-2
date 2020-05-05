package com.gdx.slimeageddon.model.gameobjects.characters;

import com.gdx.slimeageddon.model.gameobjects.weapons.Axe;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.model.util.GameObjectType;
import com.gdx.slimeageddon.model.util.Location;

public class Viking extends Character {

    public Viking(Location loc) {
        super(loc, 32f, 32f);
        setWeapon(new Axe(this));

        setType(GameObjectType.VIKING);
        setState(GameObjectState.BREATHING);
    }

    @Override
    public void skill_1() {

    }

    @Override
    public void skill_2() {

    }

    @Override
    public void skill_3() {

    }

    @Override
    public void skill_4() {

    }
}
