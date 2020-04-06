package com.gdx.slimeageddon.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.util.Location;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;

	AbstractGameView gameView;
	AbstractGame game;

	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		game = new AbstractGame();
		Entity en = new Entity(
				new Location(50f, 50f),
				32f, 32f);
		en.setName("Player");

		game.addObject(en);
		game.initWorld();

		/* Box2D Debug */
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.
				getHeight());




		gameView = new AbstractGameView(game);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		game.tick(Gdx.graphics.getDeltaTime());
		gameView.draw(batch);

		batch.setProjectionMatrix(camera.combined);
		// Scale down the sprite batches projection matrix to box2D size
		debugMatrix = batch.getProjectionMatrix().cpy().scale(game.PHYSICS_RATIO,
				game.PHYSICS_RATIO, 0);

		batch.end();

		debugRenderer.render(game.getPhysicsWorld(), debugMatrix);
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		gameView.dispose();
		game.dispose();
	}

	@Override
	public boolean keyUp(int keycode) {
		/* Intermediary movement */
		if (keycode == Input.Keys.D) {
			System.out.println("Right key");
			game.execute("Player", "turn right");
			game.execute("Player", "move");
		}

		if (keycode == Input.Keys.A) {
			game.execute("Player", "turn left");
			game.execute("Player", "move");
		}

		if(keycode == Input.Keys.W){
			game.execute("Player", "jump");
		}

		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	// On touch we apply force from the direction of the users touch.
	// This could result in the object "spinning"
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//body.applyForce(1f,1f,screenX,screenY,true);
		//body.applyTorque(0.4f,true);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
}
