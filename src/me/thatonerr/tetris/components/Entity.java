package me.thatonerr.tetris.components;

import java.awt.*;

public abstract class Entity {

    protected float x, y, velX, velY;
    protected int tileX, tileY;

    public Entity(float x, float y, float velX, float velY, int tileX, int tileY) {
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public abstract void tickPos();
    public abstract void tickX();
    public abstract void tickY();
    public abstract void render(Graphics graphics);

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

}
