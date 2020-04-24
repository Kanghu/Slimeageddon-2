package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.util.Direction;
import com.gdx.slimeageddon.model.util.Location;
import com.gdx.slimeageddon.view.util.TextureSheet;

public class StaticActor extends AbstractGameActor {

    protected Sprite sprite;

    public StaticActor(GameObject object) {
        super(object);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Location loc = getGameObject().getLocation();
        sprite.setPosition(loc.getX(), loc.getY());
        sprite.draw(batch);
    }

    /**
     * Initialize the StaticActor's texture and adjust it
     * appropriately.
     * @param assets the AssetManager
     */
    @Override
    public void initialize(AssetManager assets) {
        String textureID = TextureSheet.
                getDefaultTexture(getGameObject().getType()).
                getTexturePath();

        Texture texture = assets.get(textureID, Texture.class);
        sprite = adjustSprite(new Sprite(texture));
    }

    /**
     * Adjusts the sprite according to the GameObject size.
     * In case the texture is larger than an object's size, only the region
     * equal to the object's size is considered (starting from the middle).
     */
    public Sprite adjustSprite(Sprite sprite) {
        float width = getGameObject().getWidth();
        float height = getGameObject().getHeight();
        float realX = 0f, realY = 0f;
        Sprite adjusted = new Sprite(sprite);

        if(width < sprite.getWidth()) {
            realX = (sprite.getWidth() - width) / 2;
            if(height < sprite.getHeight()) {
                realY = (sprite.getHeight() - height) / 2;
            }

            adjusted = new Sprite(sprite, (int) realX,
                    (int) realY,
                    (int) width,
                    (int) height);
        }

        if(getGameObject() instanceof Entity) {
            if(((Entity) getGameObject()).getDirection() == Direction.LEFT)
                adjusted.flip(true, false);
        }

        return adjusted;
    }
}
