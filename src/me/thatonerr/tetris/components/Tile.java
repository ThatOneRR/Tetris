package me.thatonerr.tetris.components;

import me.thatonerr.tetris.Game;

import java.awt.*;
import java.awt.image.BufferedImage;

import static me.thatonerr.tetris.enums.Settings.*;

public class Tile extends Entity {

    private Game game;
    private Piece parent;
    private BufferedImage sprite;

    public Tile(float x, float y, float velX, float velY, int tileX, int tileY, Game game, Piece parent, BufferedImage sprite) {
        super(x, y, velX, velY, tileX, tileY);
        this.game = game;
        this.parent = parent;
        this.sprite = sprite;
    }

    @Override
    public void tickX() {
        tileX += velX;
    }

    @Override
    public void tickY() {
        tileY -= velY;
    }

    @Override
    public void tickPos() {
        x = game.getGameStartX() + tileX * TILE_SIZE;
        y = game.getGameStartY() + tileY * TILE_SIZE;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(sprite, (int) x, (int) y, TILE_SIZE, TILE_SIZE, null);
    }

    public Piece getParent() {
        return parent;
    }

    public void setParent(Piece parent) {
        this.parent = parent;
    }

    public BufferedImage getSprite() {
        return sprite;
    }

    public void setSprite(BufferedImage sprite) {
        this.sprite = sprite;
    }

}
