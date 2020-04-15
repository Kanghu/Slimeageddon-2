package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.util.Location;
import com.gdx.slimeageddon.view.util.TextureSheet;

public class GameObjectSprite extends GameObjectView {

    protected Sprite sprite;

    public GameObjectSprite(GameObject object) {
        super(object);
    }

    public void render(Batch batch) {
        Location loc = getGameObject().getLocation();
        sprite.setPosition(loc.getX(), loc.getY());

        sprite.draw(batch);
    }

    public void initialize(AssetManager assets) {
        String textureID = TextureSheet.
                getDefaultTexture(getGameObject().getType()).
                getTexturePath();

        Texture texture = assets.get(textureID, Texture.class);
        sprite = new Sprite(texture);
    }
}
