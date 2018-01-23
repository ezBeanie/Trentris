package com.trent.trentris.models;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by janu2 on 14/01/2018.
 */

public class Tetromino {

    public enum Type {
        I(new Color(Color.RED),     0x4444,0x0F00,0x2222,0x00F0),
        O(new Color(Color.YELLOW),  0x0033,0x0033,0x0033,0x0033),
        T(new Color(Color.PURPLE),  0x0232,0x0270,0x0262,0x0072),
        S(new Color(Color.GREEN),   0x0231,0x0360,0x0231,0x0036),
        Z(new Color(Color.RED),     0x0264,0x0630,0x0132,0x0063),
        J(new Color(Color.BLUE),    0x0226,0x0470,0x0322,0x0071),
        L(new Color(Color.ORANGE),  0x0622,0x0170,0x0223,0x0074);

        public int data[];
        public Color color;
        private List<int[][]> orientations = new ArrayList<int[][]>();

        Type(Color color, int... data) {
            this.data = data;
            this.color = color;
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

    public Tetromino(Type type, TetrominoColor tetrominoColor, int positionX, int positionY) {
        this.type = type;
        this.tetrominoColor = tetrominoColor;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getData() {
        return type.data[orientation];
    }

    public void rotate() {
        orientation = (orientation + 1) % 4;
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
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
