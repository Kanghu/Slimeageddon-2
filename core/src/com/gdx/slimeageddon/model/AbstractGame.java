package com.gdx.slimeageddon.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;

import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.gameobjects.PhysicalObject;
import com.gdx.slimeageddon.model.gameobjects.characters.Character;
import com.gdx.slimeageddon.model.gameobjects.characters.Viking;
import com.gdx.slimeageddon.model.gameobjects.weapons.Axe;
import com.gdx.slimeageddon.model.gameobjects.weapons.Weapon;
import com.gdx.slimeageddon.model.util.*;
import com.gdx.slimeageddon.model.util.Location;
import com.gdx.slimeageddon.model.gameobjects.*;

import java.util.ArrayList;
import java.util.List;

/***
 * An AbstractGame object represents a game instance, thus handling all the internal
 * game logic such as handling GameObjects and maintaing the physics engine.
 */

public class AbstractGame implements Disposable {

    /***
     * The game world's size.
     */
    private float width, height;

    /***
     * Physics engine constraints
     */
    static final float DEFAULT_GRAVITY = 9.8f;
    static final int WORLD_VELOCITY_ITERATIONS = 6;
    static final int WORLD_POSITION_ITEATIONS = 2;

    /***
     * Defines the offset between the game's size in internal
     * representation and its physics engine counterpart.
     *
     * The game has a size within the engine of: size / PHYSICS_RATIO
     */
    public final float PHYSICS_RATIO = 100f;

    /***
     * The collection of GameObjects
     */
    private List<GameObject> gameObjects;

    /***
     * The physics Box2D world.
     */
    private World world;

    public AbstractGame(float width, float height) {
        this.gameObjects = new ArrayList<GameObject>();
        this.width = width;
        this.height = height;
    }

    /***
     * Initialise GameObjects at the start of the game.
     */
    public void initGame(){
        /* Initialize the map */
        Map map = new Map(
                new Location(0f, 0f),
                843 * 2, 316 * 2);
        this.addObject(map);

        /* Initialize the player */
        Viking en = new Viking(new Location(100f, 100f));
        en.setName("Player");
        this.addObject(en);

        /* Initialize his weapon */
        Weapon axe = new Axe(en);
        this.addObject(axe);

        /* Initialize the ground */
        PhysicalObject ground = new PhysicalObject(
                new Location(0f, 0f),
                2000f, 18f,
                BodyDef.BodyType.StaticBody);
        this.addObject(ground);
    }

    /***
     * Initialize a Box2D world based on the collection of GameObjects.
     */
    public void initWorld(){
        /* Initialize World object */
        world = new World(new Vector2(0, (-1) * this.DEFAULT_GRAVITY), true);

        /* Instantiate each GameObject */
        for(GameObject obj : this.getGameObjects()){

            /* If object is present within the physics engine */
            if(obj instanceof PhysicalObject) {
                initObject(world, (PhysicalObject) obj);
            }

            if(obj instanceof Weapon) {
                world.createJoint(((Weapon) obj).getJointDef());
            }
        }
    }

    protected void initCollisionListeners() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                GameObject t1 = (GameObject) contact.getFixtureA().getBody().getUserData();
                GameObject t2 = (GameObject) contact.getFixtureB().getBody().getUserData();

                if(t1 instanceof com.gdx.slimeageddon.model.gameobjects.weapons.Weapon && t2 instanceof Entity) {
                    /* Do some damage */
                    ((Weapon) t1).hit((Entity) t2);
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
    }

    /***
     * Initiate a single abstract PhysicalObject in the physics engine
     * @param world Box2D world
     * @param obj A physical game object
     */
    protected void initObject(World world, PhysicalObject obj){
        /* Initialize the BodyDef carrying a Body definition */
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = obj.getBodyType();

        /* Translate the GameObject coordinates to Box2D coords, */
        /* this is required due to different coordinate systems. */
        Location objLoc = obj.getLocation();
        bodyDef.fixedRotation = true;
        bodyDef.position.set(
                ((objLoc.getX() - this.width / 2) + obj.getWidth() / 2) / PHYSICS_RATIO,
                ((objLoc.getY() - this.height / 2) + obj.getHeight() / 2) / PHYSICS_RATIO);

        /* Create body and define shape */
        Body body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((obj.getWidth() / 2) / PHYSICS_RATIO,
                (obj.getHeight() / 2) / PHYSICS_RATIO);

        /* Define body's Fixture */
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = obj.fixtureDef.density;
        fixtureDef.friction = obj.fixtureDef.friction;
        fixtureDef.isSensor = obj.fixtureDef.isSensor;

        Fixture fixture = body.createFixture(fixtureDef);

        /* Dispose of shape object */
        shape.dispose();

        /* Set object's physics body and pass the GameObject as user data */
        body.setUserData(obj);
        obj.setBody(body);
    }

    /***
     * Advance the game by specified amount of time
     * @param time to advance forward
     */
    public void tick(float time){
        world.step(time, WORLD_VELOCITY_ITERATIONS, WORLD_POSITION_ITEATIONS);

        /* Update positions */
        updateObjects();
    }

    /***
     * Update all GameObjects according to the physics engine
     */
    protected void updateObjects(){
        for(GameObject obj : this.getGameObjects()){
            if(obj instanceof PhysicalObject){
                PhysicalObject physObj = (PhysicalObject) obj;
                physObj.updateLocation(width, height);
            }
        }
    }

    /** Setters and getters **/

    public ArrayList<GameObject> getGameObjects() {
        return new ArrayList(this.gameObjects);
    }

    public void addObject(GameObject obj){
        this.gameObjects.add(obj);

        // TO DO: Rewrite this for generic nested GameObjects
        if(obj instanceof Character && ((Character) obj).getWeapon() != null) {
            this.gameObjects.add(((Character) obj).getWeapon());
        }
    }

    public GameObject findObjectByName(String name){
        for(GameObject obj : this.getGameObjects()){
            if(obj.toString().equals(name)){
                return obj;
            }
        }

        return null;
    }

    public World getPhysicsWorld(){
        return this.world;
    }

    // TO DO: Write this smarter
    public void execute(String name, String action){
        Character ent = (Character) this.findObjectByName(name);

        if(ent != null){
            if(action == "move"){
                ent.toggleMoving();
            } else if(action == "turn right"){
                ent.turn(Direction.RIGHT);
            } else if(action == "turn left"){
                ent.turn(Direction.LEFT);
            } else if(action == "jump"){
                ent.jump();
            } else if(action == "recharge"){
                ent.toggleRecharge();
            } else if(action == "attack") {
                ent.getWeapon().attack();
            }
        }
    }

    public void resize(float width, float height){
        this.width = width;
        this.height = height;
    }

    public float getWidth() { return this.width; }

    public float getHeight(){ return this.height; }

    /***
     * Dispose of all libGdx elements
     */
    public void dispose(){
        this.world.dispose();
    }
}
