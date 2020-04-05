package com.gdx.slimeageddon.view;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.gdx.slimeageddon.model.AbstractGame;
import com.gdx.slimeageddon.model.Entity;
import com.gdx.slimeageddon.model.Location;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;

	AbstractGameView gameView;
	AbstractGame game;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		game = new AbstractGame();
		game.addObject(new Entity(
				new Location(50f, 50f),
				32f, 32f
		));

		game.initWorld();

		gameView = new AbstractGameView(game);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		game.tick(Gdx.graphics.getDeltaTime());
		gameView.draw(batch);

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

		gameView.dispose();
		game.dispose();
	}
}
