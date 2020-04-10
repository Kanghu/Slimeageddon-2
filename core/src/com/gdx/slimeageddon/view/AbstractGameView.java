package com.gdx.slimeageddon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.gameobjects.PhysicalObject;
import com.gdx.slimeageddon.model.util.GameObjectType;
import com.gdx.slimeageddon.model.util.Location;
import com.gdx.slimeageddon.view.util.TextureSheet;

import java.util.ArrayList;
import java.util.List;

public class AbstractGameView implements Disposable {

    /***
     * The AbstractGame instance for which we are acting as a view for.
     */
    private AbstractGame game;

    /***
     * AssetManager to manage the game's resources.
     */
    private AssetManager assetManager;

    public AbstractGameView(AbstractGame game){
        this.game = game;

        assetManager = new AssetManager();
        this.loadTextures();

    }

    /***
     * Load textures for all GameObjects into the AssetManager.
     */
    public void loadTextures(){
        for(TextureSheet txt : TextureSheet.values()){
            if(txt.getType() != GameObjectType.DEFAULT){
                assetManager.load(txt.getTexturePath(), Texture.class);
            }
        }

        assetManager.finishLoading();
    }

    /***
     * Render each GameObject on a SpriteBatch according to the TextureSheet
     * @param batch the SpriteBatch on which to draw on
     */
    public void draw(SpriteBatch batch){
        for(GameObject obj : game.getGameObjects()){
            if(obj.getType() != GameObjectType.DEFAULT){
                String textureID = TextureSheet.
                        getTextureByType(obj.getType()).
                        getTexturePath();
                Texture txt = assetManager.get(textureID, Texture.class);
                Sprite sprite = new Sprite(txt);
                Location loc = obj.getLocation();

                sprite.setPosition(loc.getX(), loc.getY());
                sprite.setSize(obj.getWidth(), obj.getHeight());
                sprite.draw(batch);
            }
        }
    }

    /***
     * Dispose of all the allocated resources.
     */
    public void dispose(){
        assetManager.dispose();
    }
}
