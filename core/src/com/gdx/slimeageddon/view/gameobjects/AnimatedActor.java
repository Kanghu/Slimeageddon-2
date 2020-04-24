package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.*;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.view.util.TextureSheet;

public class AnimatedActor extends StaticActor {

    public static final float FRAMERATE = 1/10f;

    protected Animation<TextureRegion> animation;
    protected float stateTime;

    public AnimatedActor(GameObject object) {
        super(object);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        this.sprite = adjustSprite(new Sprite(currentFrame));
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        stateTime += delta;
    }

    @Override
    public void initialize(AssetManager assets) {
        /* Load the appropriate sprite sheet */
        String textureID = TextureSheet.
                getTexture(getGameObject().getType(), getGameObject().getState()).
                getTexturePath();

        TextureAtlas texture = assets.get(textureID, TextureAtlas.class);
        this.animation = new Animation<TextureRegion>(FRAMERATE, texture.getRegions());
    }
}
