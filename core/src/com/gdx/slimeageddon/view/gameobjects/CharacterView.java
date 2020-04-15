package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.view.util.TextureSheet;

public class CharacterView extends GameObjectAnimation {

    public final float FRAMERATE = 1/10f;

    public CharacterView(GameObject object) {
        super(object);
    }

    public void initialize(AssetManager assets) {
        /* Load the appropriate sprite sheet */
        String textureID = TextureSheet.
                getTexture(getGameObject().getType(), getGameObject().getState()).
                getTexturePath();

        TextureAtlas texture = assets.get(textureID, TextureAtlas.class);
        this.animation = new Animation<TextureRegion>(FRAMERATE, texture.getRegions());
    }
}
