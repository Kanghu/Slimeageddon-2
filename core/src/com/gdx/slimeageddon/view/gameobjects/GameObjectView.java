package com.gdx.slimeageddon.view.gameobjects;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.gdx.slimeageddon.model.gameobjects.GameObject;

/***
 * Acts as a view for a GameObject, thus allowing for smooth separation
 * between the model and its graphical representation.
 *
 * Each GameObjectView is called by a Game View to simply render itself
 * upon the specified environment (e.g. SpriteBatch).
 */

abstract public class GameObjectView {

    private GameObject gameObject;

    public GameObjectView(GameObject object) {
        this.gameObject = object;
    }

    public void setGameObject(GameObject object) {
        this.gameObject = object;
    }

    public GameObject getGameObject() {
        return this.gameObject;
    }

    public abstract void render(Batch batch);

    public abstract void initialize(AssetManager assets);
}
