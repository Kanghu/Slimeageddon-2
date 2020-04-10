package com.gdx.slimeageddon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.gameobjects.PhysicalObject;
import com.gdx.slimeageddon.model.util.Location;

import java.util.ArrayList;
import java.util.List;

public class AbstractGameView implements Disposable {

    private AbstractGame game;
    private List<Sprite> sprites;
    private Texture img;

    public AbstractGameView(AbstractGame game){
        this.game = game;
        sprites = new ArrayList<Sprite>();

        img = new Texture(Gdx.files.internal("resources/Sprites/Characters/Samurai/Ice Stun.png"));

        for(GameObject obj : game.getGameObjects()){
            Sprite sprite = new Sprite(img);
            sprite.setPosition(obj.getLocation().getX(),
                    obj.getLocation().getY());
            sprites.add(sprite);
        }
    }

    public void draw(SpriteBatch batch){
        for(int i=0; i<sprites.size(); i++){
            Sprite toDraw = sprites.get(i);
            GameObject object = game.getGameObjects().get(i);
            Location loc = object.getLocation();

            toDraw.setPosition(loc.getX(), loc.getY());

            batch.draw(toDraw, toDraw.getX(), toDraw.getY(),
                    object.getWidth(), object.getHeight());
        }
    }

    public ArrayList<Sprite> getSprites(){
        return new ArrayList<Sprite>(sprites);
    }

    public void dispose(){

        img.dispose();
    }
}
