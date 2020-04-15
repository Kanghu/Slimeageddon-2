package com.gdx.slimeageddon.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.Map;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.view.gameobjects.*;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
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

    /***
     * The view objects for all internal GameObjects
     */
    private List<GameObjectView> gameObjects;

    public AbstractGameView(AbstractGame game){
        this.game = game;

        assetManager = new AssetManager();
        this.loadTextures();
        this.initGameView();

    }

    /***
     * Load textures for all GameObjects into the AssetManager.
     */
    public void loadTextures(){
        for(TextureSheet txt : TextureSheet.values()){
            if(txt.getType() != GameObjectType.DEFAULT){

                if(txt.getState() == GameObjectState.DEFAULT) {
                    assetManager.load(txt.getTexturePath(), Texture.class);
                } else {
                    assetManager.load(txt.getTexturePath(), TextureAtlas.class);
                }
            }
        }

        assetManager.finishLoading();
    }

    /***
     * Initialize appropriate view objects for all GameObjects.
     */
    public void initGameView() {
        gameObjects = new ArrayList<GameObjectView>();

        for(GameObject obj : game.getGameObjects()){
            /* Default GameObjects have no views */
            if(obj.getType() != GameObjectType.DEFAULT) {
                GameObjectView view;

                if(obj.getState() != GameObjectState.DEFAULT) {
                    view = new CharacterView(obj);
                } else {
                    view = new GameObjectSprite(obj);
                }

                view.initialize(this.assetManager);
                this.gameObjects.add(view);
            }
        }
    }

    /***
     * Render each GameObject on a SpriteBatch according to the TextureSheet
     * @param batch the SpriteBatch on which to draw on
     */
    public void draw(SpriteBatch batch){
        for(GameObjectView view : this.gameObjects) {
            view.render(batch);
        }
    }

    public void advance(float time) {
        for(GameObjectView view : this.gameObjects) {
            if(view instanceof GameObjectAnimation) {
                ((GameObjectAnimation) view).advance(time);
            }
        }
    }

    public void updateView() {
        for(GameObjectView view : this.gameObjects) {
            if(view instanceof GameObjectAnimation) {
                view.initialize(this.assetManager);
            }
        }
    }

    /***
     * Chop a TextureSheet into equally sized textures.
     *
     * For now, all Textures are split into 64x64 textures.
     */
    public void chopTextureSheet(Texture texture) {
        int TILE_SIZE = 64;

        int FRAME_COLS = texture.getWidth() / TILE_SIZE;
        int FRAME_ROWS = texture.getHeight() / TILE_SIZE;

        TextureRegion[][] tmp = TextureRegion.split(texture, TILE_SIZE, TILE_SIZE);
        TextureRegion[] frames = new TextureRegion[tmp.length * tmp[0].length];
    }

    /***
     * Dispose of all the allocated resources.
     */
    public void dispose(){
        assetManager.dispose();
    }
}
