package com.gdx.slimeageddon.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.PhysicalObject;

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

        for(PhysicalObject obj : game.getGameObjects()){
            Sprite sprite = new Sprite(img);
            sprite.setPosition(obj.getLocation().getX(),
                    obj.getLocation().getY());
            sprites.add(sprite);
        }
    }

    public void draw(SpriteBatch batch){
        for(int i=0; i<sprites.size(); i++){
            sprites.get(i).setPosition(game.getGameObjects().get(i).getLocation().getX(),
                    game.getGameObjects().get(i).getLocation().getY());
            batch.draw(sprites.get(i), sprites.get(i).getX(), sprites.get(i).getY());
        }
    }

    public ArrayList<Sprite> getSprites(){
        return new ArrayList<Sprite>(sprites);
    }

    public void dispose(){
        img.dispose();
    }
}
