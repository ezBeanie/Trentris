package com.trent.trentris.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.trent.trentris.controller.WorldController;

/**
 * Created by janu2 on 14/01/2018.
 */

public class GameScreen implements Screen {

    private Game tetrisGame;
    private WorldController worldController;
    private SpriteBatch spriteBatch;

    public GameScreen(Game tetrisGame) {
        this.tetrisGame = tetrisGame;
        this.worldController = new WorldController();
        this.spriteBatch = new SpriteBatch();
        Gdx.input.setInputProcessor(worldController);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        spriteBatch.begin();
        worldController.render(spriteBatch);
        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
