package com.gdx.slimeageddon.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.gameobjects.Entity;
import com.gdx.slimeageddon.model.gameobjects.GameObject;
import com.gdx.slimeageddon.model.gameobjects.PhysicalObject;
import com.gdx.slimeageddon.model.util.Location;

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	SpriteBatch batch;

	AbstractGameView gameView;
	AbstractGame game;

	Box2DDebugRenderer debugRenderer;
	Matrix4 debugMatrix;
	OrthographicCamera camera;

	BitmapFont font;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		game = new AbstractGame(1600, 900);
		game.initGame();
		game.initWorld();

		gameView = new AbstractGameView(game);

		/* Box2D Debug */
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(game.getWidth(), game.getHeight());

		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render () {
		camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();

		game.tick(Gdx.graphics.getDeltaTime());
		gameView.getStage().act(Gdx.graphics.getDeltaTime());
		gameView.updateView();
		gameView.draw();

		batch.setProjectionMatrix(camera.combined);
		/* Scale down the sprite batches projection matrix to box2D size */
		debugMatrix = batch.getProjectionMatrix().cpy().scale(game.PHYSICS_RATIO,
				game.PHYSICS_RATIO, 0);

		/* Print position */
		font.draw(batch, String.valueOf(game.findObjectByName("Player").getLocation().getX())
				+ " " + String.valueOf(game.findObjectByName("Player").getLocation().getY()),
				game.getWidth() / 3,
				game.getHeight() / 2);

		batch.end();

		/* Render Box2D debug view */
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
			game.execute("Player", "move");
		}

		if (keycode == Input.Keys.A) {
			game.execute("Player", "move");
		}

		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		/* Intermediary movement */
		if (keycode == Input.Keys.D) {
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

		if(keycode == Input.Keys.S) {
			game.execute("Player", "recharge");
		}

		return true;
	}

	@Override
	public void resize (int width, int height) {
		this.gameView.getStage().getViewport().update(width, height);
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}


	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
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
