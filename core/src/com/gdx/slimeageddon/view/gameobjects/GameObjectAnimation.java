package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.util.Direction;
import com.gdx.slimeageddon.model.util.Location;

abstract public class GameObjectAnimation extends GameObjectView {

    protected Animation<TextureRegion> animation;
    protected float stateTime;

    public GameObjectAnimation(GameObject object) {
        super(object);
    }

    public void render(Batch batch) {
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
        Location loc = getGameObject().getLocation();
        Sprite sprite = new Sprite(currentFrame);
        sprite.setPosition(loc.getX(), loc.getY());

        Direction dir = ((Entity) getGameObject()).getDirection();

        if(dir == Direction.LEFT) {
            sprite.flip(true, false);
        }

        sprite.draw(batch);
    }

    public void advance(float time) {
        stateTime += time;
    }

    abstract public void initialize(AssetManager assets);
}
