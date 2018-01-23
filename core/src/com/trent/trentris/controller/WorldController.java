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
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.trent.trentris.models.Tetromino;

import java.util.Random;

import static com.trent.trentris.models.Tetromino.*;

/**
 * Created by janu2 on 23/01/2018.
 */

public class WorldController implements InputProcessor {

    private static final int WORLD_X = 12;
    private static final int WORLD_Y = 25;
    private static final int VIEWPORT_WIDTH = 14;
    private static final int VIEWPORT_HEIGHT = 24;
    private static final int START_X = WORLD_X / 2;
    private static final int START_Y = WORLD_Y - 2;

    private int[][] worldData = new int[WORLD_X][WORLD_Y];
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private Texture texture;
    private Image image;
    private Tetromino currentTetromino = new Tetromino(Type.Z, TetrominoColor.BLUE, START_X, START_Y);
    private int worldTics = 0;
    private int stepTics = 60;
    private Random random = new Random(System.currentTimeMillis());

    public WorldController() {
        this.camera = new OrthographicCamera(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        this.shapeRenderer = new ShapeRenderer();
        this.texture = new Texture(Gdx.files.internal("tile.png"));
        this.image = new Image(new TextureRegion(texture));

        camera.position.set((VIEWPORT_WIDTH /2)+1, (VIEWPORT_HEIGHT /2)+1,0f);
        camera.update();
        camera.zoom = 1.0f;
        init();
    }

    public void update(float delta) {
        if(worldTics % stepTics == 0) {
            Gdx.app.log("WorldTics", ""+worldTics);
            if(currentTetromino.getPositionY()>0) {
                currentTetromino.setPositionY(currentTetromino.getPositionY()-1);
            }
            else {
                //placeBlock(currentTetromino, currentTetromino.getPositionX(), currentTetromino.getPositionY());
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
        shapeRenderer.setColor(currentTetromino.getType().color);

        int data = currentTetromino.getData();

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
                        Gdx.app.log("L", j + ":" + i);
                        shapeRenderer.rect(i+ currentTetromino.getPositionX(),j+ currentTetromino.getPositionY(),1,1);
                    }
                }
            }
        }
        System.out.println("END");

        for (int x=0;x<WORLD_X;x++) {
            for (int y=0;y<WORLD_Y;y++) {
                if(worldData[x][y] != 0) {
                    shapeRenderer.rect(x,y,1,1);
                }
            }
        }
        shapeRenderer.end();
    }

    private boolean checkCollision() {
        int data = currentTetromino.getData();
        for (int x=0;x<4;x++) {
            int temp = (data & (0x000F << x*4));
            //System.out.println(temp+"A");
            temp = (temp >> x*4);
            if (temp != 0) {
                for(int y=0;y<4;y++) {
                    int place = ((temp & (0x1 << y)));
                    //System.out.println(place+"B");
                    if (place != 0) {
                        //Gdx.app.log("L", j + ":" + i);
                        if(worldData[x][y] != 0) {
                            Gdx.app.log("first", "collision");
                            return true;
                        }
                        if(worldData[Math.max(x-1,0)][y] != 0) {
                            Gdx.app.log("second", "collision");

                            return true;
                        }
                        if(worldData[x][Math.max(y-1,0)] != 0) {
                            Gdx.app.log("third", "collision");

                            return true;
                        }
                        if(worldData[x][Math.min(y+1,11)] != 0) {
                            Gdx.app.log("fourth", "collision");

                            return true;
                        }
                    }
                }
            }
        }
        return false;
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
                        worldData[i+ currentTetromino.getPositionX()][j+ currentTetromino.getPositionY()] = 1;
                        //shapeRenderer.rect(i+currentTetromino.getPositionX(),j+currentTetromino.getPositionY(),1,1);
                    }
                }
            }
        }
        Gdx.app.log("TetData",""+data);
        Gdx.app.log("X", ""+positionX);
        Gdx.app.log("Y", ""+positionY);
    }

    private void getNext() {
        currentTetromino.setPositionY(START_Y);
        currentTetromino.setType(getRandomTetromino());
    }

    private Type getRandomTetromino() {
        int pick = random.nextInt(Type.values().length);
        return Type.values()[pick];
    }

    private void init() {
        for(int i = 0;i < WORLD_Y; i++) {
            worldData[0][i] = 1;
            worldData[WORLD_X-1][i] = 1;
        }
        for(int i=0;i < WORLD_X;i++) {
            worldData[i][0] = 1;
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        if(keycode == Input.Keys.UP) {
            currentTetromino.rotate();
        }
        if(keycode == Input.Keys.RIGHT) {
            currentTetromino.setPositionX(currentTetromino.getPositionX()+1);
        }
        if(keycode == Input.Keys.LEFT) {
            currentTetromino.setPositionX(currentTetromino.getPositionX()-1);
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
