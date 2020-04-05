package com.gdx.slimeageddon.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import java.util.ArrayList;
import java.util.List;

/***
 * A Game object represents a game instance, thus handling all the game logic
 * such as physics and the set of GameObjects
 */

public class AbstractGame implements Disposable {

    static final float DEFAULT_GRAVITY = 10f;
    static final int WORLD_VELOCITY_ITERATIONS = 6;
    static final int WORLD_POSITION_ITEATIONS = 2;

    /***
     * The collection of PhysicalObjects
     */
    private List<PhysicalObject> gameObjects;

    /***
     * The physics world
     */
    private World world;

    public AbstractGame() {
        this.gameObjects = new ArrayList<PhysicalObject>();
    }

    /***
     * Initialize a Box2D world based on the collection of GameObjects
     */
    public void initWorld(){
        /* Initialize World object */
        world = new World(new Vector2(0, (-1) * this.DEFAULT_GRAVITY), true);

        /* Instantiate each abstract PhysicalObject in the physics engine */
        for(PhysicalObject obj : this.getGameObjects()){
            initObject(world, obj);
        }
    }

    /***
     * Initiate a single abstract PhysicalObject in the physics engine
     * @param world Box2D world
     * @param obj A physical game object
     */
    protected void initObject(World world, PhysicalObject obj){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(
                obj.getLocation().getX(),
                obj.getLocation().getY());

        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(obj.getWidth() / 2, obj.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();

        obj.setBody(body);
    }

    public void tick(float time){
        world.step(time, WORLD_VELOCITY_ITERATIONS, WORLD_POSITION_ITEATIONS);

        /* Update positions */
        for(PhysicalObject obj : this.getGameObjects()) {
            obj.updateLocation();
        }
    }

    public ArrayList<PhysicalObject> getGameObjects() {
        return new ArrayList(this.gameObjects);
    }

    public void addObject(PhysicalObject obj){
        this.gameObjects.add(obj);
    }

    public void dispose(){
        this.world.dispose();
    }
}
