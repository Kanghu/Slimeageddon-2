package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gdx.slimeageddon.model.gameobjects.GameObject;

/***
 * Acts as a view for a GameObject, represented as an Actor
 * in the scene2D implementation.
 *
 * Each GameActor is called by a Stage to simply render itself
 * upon the specified environment (e.g. SpriteBatch).
 */

abstract public class AbstractGameActor extends Actor {

    private GameObject gameObject;

    public AbstractGameActor(GameObject object) {
        this.gameObject = object;
    }

    public void setGameObject(GameObject object) {
        this.gameObject = object;
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public abstract void draw(Batch batch, float parentAlpha);

    public abstract void initialize(AssetManager assets);
}
