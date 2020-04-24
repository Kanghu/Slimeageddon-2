package com.gdx.slimeageddon.view;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.util.GameObjectState;
import com.gdx.slimeageddon.view.gameobjects.*;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.util.GameObjectType;
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
     * The Stage object we will be maintaning as a view.
     */
    private Stage stage;

    public AbstractGameView(AbstractGame game){
        this.game = game;

        assetManager = new AssetManager();
        stage = new Stage(new FitViewport(game.getWidth(), game.getHeight()));
        this.loadTextures();
        this.initStage();
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
     * Initialize appropriate Actors for all GameObjects and
     * add them to the Stage.
     */
    public void initStage() {
        for(GameObject obj : game.getGameObjects()){
            /* Default GameObjects have no Actors */
            if(obj.getType() != GameObjectType.DEFAULT) {
                AbstractGameActor actor;

                if(obj.getState() != GameObjectState.DEFAULT) {
                    actor = new AnimatedActor(obj);
                } else {
                    actor = new StaticActor(obj);
                }

                actor.initialize(this.assetManager);
                this.stage.addActor(actor);
            }
        }
    }

    /***
     * Render each GameObject on a SpriteBatch according to the TextureSheet
     */
    public void draw(){
        stage.draw();
    }

    public void updateView() {
        for(Actor actor : this.getStage().getActors()) {
            if(actor instanceof AnimatedActor) {
                ((AnimatedActor) actor).initialize(this.assetManager);
            }
        }
    }


    /***
     * Dispose of all the allocated resources.
     */
    public void dispose(){
        assetManager.dispose();
    }


    /** Getters and setters **/
    public Stage getStage() { return this.stage; }
    public void setStage(Stage stage) { this.stage = stage; }
}
