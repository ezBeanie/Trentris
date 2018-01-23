package com.trent.trentris.models;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janu2 on 14/01/2018.
 */

public class Tetromino {

    public enum Type {
        I(0x4444,0x0F00,0x2222,0x00F0),
        O(0x0033,0x0033,0x0033,0x0033),
        T(0x0232,0x0270,0x0262,0x0072),
        S(0x0231,0x0360,0x0231,0x0036),
        Z(0x0132,0x0000,0x0132,0x0000),
        J(0x0226,0x0470,0x0322,0x0071),
        L(0x0622,0x0170,0x0223,0x0074);

        public int data[];
        private List<int[][]> orientations = new ArrayList<int[][]>();

        Type(int... data) {
            this.data = data;
        }

        public int[] getData() {
            return data;
        }
    }

    public enum TetrominoColor {
        RED,
        GREEN,
        BLUE,
        YELLOW
    }

    private TetrominoColor tetrominoColor;
    private Type type;
    private int orientation;
    private int positionX, positionY;

    public Tetromino(Type type, TetrominoColor tetrominoColor) {
        this.type = type;
    }

    public int getData() {
        return type.getData()[orientation];
    }

    public void rotate() {
        orientation = (orientation + 1)%4;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public TetrominoColor getTetrominoColor() {
        return tetrominoColor;
    }

    public void setTetrominoColor(TetrominoColor tetrominoColor) {
        this.tetrominoColor = tetrominoColor;
    }

    public int getOrientation() {
        return orientation;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        if(!(positionX < 0 || positionX > 11)) {
            this.positionX = positionX;
        }
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
