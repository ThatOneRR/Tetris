package me.thatonerr.tetris.components;

import me.thatonerr.tetris.Game;

import java.awt.*;

public abstract class UIObject {

    protected Game game;
    protected int x, y, endX, endY, width, height;

    public UIObject(Game game, int x, int y, int width, int height) {
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        endX = x + width;
        endY = y + height;
    }

    public abstract void tick();
    public abstract void render(Graphics graphics);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
