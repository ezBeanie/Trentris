package com.trent.trentris.controller;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.trent.trentris.models.Tetromino;

import javax.xml.soap.Text;

import sun.security.provider.SHA;

import static com.badlogic.gdx.Gdx.input;

/**
 * Created by janu2 on 14/01/2018.
 */

public class WorldController implements InputProcessor {

    private static final int WORLD_WIDTH = 14;
    private static final int WORLD_HEIGHT = 24;
    private int[][] worldData = new int[25][10];
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Texture texture;
    private Image image;
    private Tetromino firstMongo = new Tetromino(Tetromino.Type.L, Tetromino.TetrominoColor.BLUE);

    public WorldController() {
        this.camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        this.shapeRenderer = new ShapeRenderer();
        this.texture = new Texture(Gdx.files.internal("tile.png"));
        this.image = new Image(new TextureRegion(texture));

        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0f);
        camera.update();
    }

    public void update(float delta) {

    }

    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        int data = firstMongo.getData();
        System.out.println("BEGIN");
        for(int i=0;i<4;i++) {
            int temp = (data & (0x000F << i*4));
            System.out.println(temp+"A");
            temp = (temp >> i*4);
            if(temp != 0) {
                for(int j=0;j<4;j++) {
                    int place = ((temp & (0x1 << j)));
                    System.out.println(place+"B");
                    if(place != 0) {
                        //Gdx.app.log("L", j + ":" + i);
                        shapeRenderer.rect(i+firstMongo.getPositionX(),j+firstMongo.getPositionY(),1,1);
                    }
                }
            }
        }
        System.out.println("END");
        shapeRenderer.end();

        //Gdx.app.log("Mongo", ""+(short)(firstMongo.getType().getData()&0x000F));

    }

    private void getNext() {

    }

    public static int getWorldWidth() {
        return WORLD_WIDTH;
    }

    public static int getWorldHeight() {
        return WORLD_HEIGHT;
    }

    public int[][] getWorldData() {
        return worldData;
    }

    public void setWorldData(int[][] worldData) {
        this.worldData = worldData;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.UP) {
            firstMongo.rotate();
        }
        if(keycode == Input.Keys.RIGHT) {
            firstMongo.setPositionX(firstMongo.getPositionX()+1);
        }
        if(keycode == Input.Keys.LEFT) {
            firstMongo.setPositionX(firstMongo.getPositionX()-1);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
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
