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

import java.util.Random;

import static com.trent.trentris.models.Tetromino.*;

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
    private Tetromino firstMongo = new Tetromino(Type.L, TetrominoColor.BLUE, 5, 4);
    private int worldTics = 0;
    private int stepTics = 60;
    private Random random = new Random(System.currentTimeMillis());

    public enum GameState {

    }

    public WorldController() {
        this.camera = new OrthographicCamera(WORLD_WIDTH,WORLD_HEIGHT);
        this.shapeRenderer = new ShapeRenderer();
        this.texture = new Texture(Gdx.files.internal("tile.png"));
        this.image = new Image(new TextureRegion(texture));

        camera.position.set(WORLD_WIDTH/2,WORLD_HEIGHT/2,0f);
        camera.update();
    }

    public void update(float delta) {
        if(worldTics % stepTics == 0) {
            Gdx.app.log("WorldTics", ""+worldTics);
            if(firstMongo.getPositionY()>0) {
                firstMongo.setPositionY(firstMongo.getPositionY()-1);
            }
            else {
                placeBlock(firstMongo, firstMongo.getPositionX(), firstMongo.getPositionY());
                getNext();
            }
        }
        worldTics++;
    }

    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        int data = firstMongo.getData();
        //System.out.println("BEGIN");
        for(int i=0;i<4;i++) {
            int temp = (data & (0x000F << i*4));
            //System.out.println(temp+"A");
            temp = (temp >> i*4);
            if(temp != 0) {
                for(int j=0;j<4;j++) {
                    int place = ((temp & (0x1 << j)));
                    //System.out.println(place+"B");
                    if(place != 0) {
                        //Gdx.app.log("L", j + ":" + i);
                        shapeRenderer.rect(i+firstMongo.getPositionX(),j+firstMongo.getPositionY(),1,1);
                    }
                }
            }
        }
        for (int x=0;x<25;x++) {
            for (int y=0;y<10;y++) {
                if(worldData[x][y] == 1) {
                    shapeRenderer.rect(x,y,1,1);
                }
            }
        }
        //System.out.println("END");
        shapeRenderer.end();

        //Gdx.app.log("Mongo", ""+(short)(firstMongo.getType().getData()&0x000F));

    }

    private void getNext() {

    }

    private void placeBlock(Tetromino tetromino, int positionX, int positionY) {
        int data = tetromino.getData();
        //System.out.println("BEGIN");
        for(int i=0;i<4;i++) {
            int temp = (data & (0x000F << i*4));
            //System.out.println(temp+"A");
            temp = (temp >> i*4);
            if(temp != 0) {
                for(int j=0;j<4;j++) {
                    int place = ((temp & (0x1 << j)));
                    //System.out.println(place+"B");
                    if(place != 0) {
                        //Gdx.app.log("L", j + ":" + i);
                        worldData[i+firstMongo.getPositionX()][j+firstMongo.getPositionY()] = 1;
                        //shapeRenderer.rect(i+firstMongo.getPositionX(),j+firstMongo.getPositionY(),1,1);
                    }
                }
            }
        }
        firstMongo.setPositionY(5);
        firstMongo.setType(getRandomTetromino());
        Gdx.app.log("TetData",""+data);
        Gdx.app.log("X", ""+positionX);
        Gdx.app.log("Y", ""+positionY);
    }

    private void checkCollision(Tetromino tetromino) {

    }

    private Type getRandomTetromino() {
        int pick = random.nextInt(Type.values().length);
        return Type.values()[pick];
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
