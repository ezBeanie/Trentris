package com.trent.trentris.main;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.trent.trentris.controller.WorldController;
import com.trent.trentris.screens.GameScreen;

public class TetrisGame extends Game {
	SpriteBatch batch;
	
	@Override
	public void create () {
		this.setScreen(new GameScreen(this));
		batch = new SpriteBatch();
	}
}
