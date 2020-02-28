package me.thatonerr.tetris.components;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.enums.Sprites;
import me.thatonerr.tetris.framework.KeyInput;
import me.thatonerr.tetris.managers.GameManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static me.thatonerr.tetris.enums.Settings.TILE_SIZE;

public class Piece extends Shape {

    private Game game;
    private GameManager gameManager;
    private KeyInput keyInput;
    private List<Tile> tiles = new ArrayList<>();
    private int shapeID;
    private boolean placed = false, changed = false;
    private int velX = 0, velY = -1, startTileX, startTileY, endTileX, endTileY, point;
    private int xCounter = 0, xSpeed = 5, gravCounter = 0, gravSpeed, downCounter = 0, downSpeed = 20, placeCounter = 0, placeSpeed = 60;

    public Piece(Game game, int startTileX, int startTileY, int shapeID, int gravSpeed) {
        super(shapeID);
        this.game = game;
        gameManager = game.getGameManager();
        keyInput = game.getKeyInput();
        this.startTileX = startTileX;
        this.startTileY = startTileY - shapeHeight;
        this.shapeID = shapeID;
        this.gravSpeed = gravSpeed;
        point = startTileX;
        endTileX = this.startTileX + shapeWidth - 1;
        endTileY = this.startTileY + shapeHeight - 1;
        int[][] formMap = currentForm.getMap();
        for (int y = 0; y < formMap.length; y++) {
            for (int x = 0; x < formMap[y].length; x++) {
                if (formMap[y][x] == 1) {
                    Tile tile = new Tile(game.getGameStartX() + (this.startTileX + x) * TILE_SIZE, game.getGameStartY() + (this.startTileY + y) * TILE_SIZE, velX, velY, this.startTileX + x, this.startTileY + y, game, this, Sprites.getTileSprite(shapeID));
                    tiles.add(tile);
                }
            }
        }
    }

    public void updatePiece(int startTileX, int startTileY) {
        tiles.clear();
        this.startTileX = startTileX;
        this.startTileY = startTileY - shapeHeight;
        endTileX = this.startTileX + shapeWidth - 1;
        endTileY = this.startTileY + shapeHeight - 1;
        int[][] formMap = currentForm.getMap();
        for (int y = 0; y < formMap.length; y++) {
            for (int x = 0; x < formMap[y].length; x++) {
                if (formMap[y][x] == 1) {
                    Tile tile = new Tile(game.getGameStartX() + (this.startTileX + x) * TILE_SIZE, game.getGameStartY() + (this.startTileY + y) * TILE_SIZE, velX, velY, this.startTileX + x, this.startTileY + y, game, this, Sprites.getTileSprite(shapeID));
                    tiles.add(tile);
                }
            }
        }
    }

    public void showPrediction(Graphics graphics) {
        if (gameManager == null) {
            return;
        }
        boolean done = false;
        int maxDy = 0;
        while (true) {
            for (Tile tile : tiles) {
                int y = tile.getTileY() + maxDy;
                if (y >= 20) {
                    maxDy = y - tile.getTileY();
                    done = true;
                    break;
                }
                if (gameManager.getTileAt(tile, tile.getTileX(), y) != null) {
                    done = true;
                    break;
                }
            }
            if (done) {
                break;
            }
            maxDy++;
        }
        maxDy--;
        for (Tile tile : tiles) {
            graphics.drawImage(Sprites.getTransparentTileSprite(shapeID), (int) tile.getX(), (int) (tile.getY() + maxDy * TILE_SIZE), TILE_SIZE, TILE_SIZE, null);
        }
    }

    public void tick() {
        if (gameManager == null) {
            gameManager = game.getGameManager();
        }
        gravSpeed = gameManager.getGravSpeed();

        // Reset velX every tick
        velX = 0;

        // Check for hold (Swap)
        if (keyInput.isHold()) {
            gameManager.switchPieces();
        }

        // Check for x key inputs
        if (keyInput.isLeft() || keyInput.isRight()) {
            // If move, counter decreases slightly slower (if it's y colliding)
            if (placeCounter < placeSpeed / 2) {
                placeCounter += 2;
            }
        }
        if (keyInput.isUp() && !changed) {
            cycleTiles();
            changed = true;
        } else if (!keyInput.isUp()) {
            changed = false;
        }
        if (keyInput.isLeft()) {
            velX -= 1;
        }
        if (keyInput.isRight()) {
            velX += 1;
        }

        // If the piece doesn't x collide, update movement
        if (xCounter == 0 && !xBoundCollides() && !xCollides()) {
            for (Tile tile : tiles) {
                tile.setVelX(velX);
                tile.tickX();
            }
            startTileX += velX;
            endTileX += velX;
        }

        // Check for y key inputs
        if (keyInput.isSpace() && !gameManager.isSpaced()) {
            gameManager.setScore(gameManager.getScore() + (20 - startTileY) * 7);
            gameManager.setSpaced(true);
            int lowestDy = 20 * 20;
            for (Tile tile : tiles) {
                int lowestY = gameManager.getLowestTileY(tile, tile.getTileX(), tile.getTileY());
                if (lowestDy > lowestY - tile.getTileY() - 1) {
                    lowestDy = lowestY - tile.getTileY() - 1;
                }
            }
            for (Tile tile : tiles) {
                tile.setTileY(tile.getTileY() + lowestDy);
            }
            startTileY += lowestDy;
            endTileY += lowestDy;
            placed = true;
            if (startTileY <= 0 || endTileY <= 0) {
                gameManager.endGame();
            }
        } else {
            if (keyInput.isDown()) {
                downCounter += 10;
                if (downCounter >= downSpeed) {
                    downCounter = 0;
                    if (!yBoundCollides() && !yCollides()) {
                        for (Tile tile : tiles) {
                            tile.setVelY(velY);
                            tile.tickY();
                        }
                        startTileY -= velY;
                        endTileY -= velY;
                        gameManager.setScore(gameManager.getScore() + 5);
                    }
                }
            } else {
                // Set gravity twice every second
                if (gravCounter == 0 && !yBoundCollides() && !yCollides()) {
                    // If it's not y colliding, tick the tileY.
                    if (!yBoundCollides() && !yCollides()) {
                        for (Tile tile : tiles) {
                            tile.setVelY(velY);
                            tile.tickY();
                        }
                        startTileY -= velY;
                        endTileY -= velY;
                    }
                }
            }

            // If the piece y collides, do the placement counter
            if (yCollides()) {
                if (placeCounter < placeSpeed) {
                    placeCounter++;
                } else {
                    placed = true;
                    if (startTileY <= 0 || endTileY <= 0) {
                        gameManager.endGame();
                    }
                }
            } else if (yBoundCollides()) {
                if (placeCounter < placeSpeed) {
                    placeCounter++;
                } else {
                    placed = true;
                }
            } else {
                placeCounter = 0;
            }
        }

        if (!keyInput.isSpace()) {
            gameManager.setSpaced(false);
        }

        // Updating tile positions
        for (Tile tile : tiles) {
            tile.tickPos();
        }

        // Updating X movement counter
        if (xCounter >= xSpeed) {
            xCounter = 0;
        } else {
            xCounter++;
        }

        // Update gravity counter
        if (gravCounter >= gravSpeed) {
            gravCounter = 0;
        } else {
            gravCounter++;
        }
    }

    public void cycleTiles() {
        // Store previous form in case of reverting
        boolean revert = false;
        int previousFormID = currentFormID;
        List<Tile> previousTiles = new ArrayList<>(tiles);

        // Cycle tiles to next form
        cycleForm();
        updateTiles();

        // Check for y boundary collision
        if (yBoundCollides()) {
            for (int i = 1; i <= shapeHeight - 1; i++) {
                startTileY -= 1;
                updateTiles();
                if (!yCollides()) {
                    break;
                }
            }
            if (yCollides()) {
                startTileY += shapeHeight - 1;
                updateTiles();
                revert = true;
            }
        }
        // If new form collides with boundaries, revert back to the previous form.
        if (revert) {
            setForm(previousFormID);
            gameManager.removePieceTiles(this);
            tiles.clear();
            tiles = previousTiles;
            gameManager.addPieceTiles(this);
            return;
        }
        if (yCollides()) {
            for (int i = 1; i <= shapeHeight - 1; i++) {
                startTileY -= 1;
                updateTiles();
                if (!yCollides() && !yBoundCollides()) {
                    break;
                }
            }
            if (yCollides() || yBoundCollides()) {
                startTileY += shapeHeight - 1;
                updateTiles();
                revert = true;
            }
        }
        // If new form collides with other tiles, revert back to the previous form.
        if (revert) {
            setForm(previousFormID);
            gameManager.removePieceTiles(this);
            tiles.clear();
            tiles = previousTiles;
            gameManager.addPieceTiles(this);
            return;
        }
        // Check for x bound collision
        if (xBoundCollides()) {
            for (int i = 1; i <= shapeWidth; i++) {
                startTileX -= 1;
                updateTiles();
                if (!xCollides()) {
                    break;
                }
            }
            if (xCollides()) {
                startTileX += shapeWidth;
                updateTiles();
                revert = true;
            }
        }
        // If new form collides with boundaries, revert back to the previous form.
        if (revert) {
            setForm(previousFormID);
            gameManager.removePieceTiles(this);
            tiles.clear();
            tiles = previousTiles;
            gameManager.addPieceTiles(this);
            return;
        }
        if (xCollides()) {
            for (int i = 1; i <= shapeWidth; i++) {
                startTileX -= 1;
                updateTiles();
                if (!xCollides() && !xBoundCollides()) {
                    break;
                }
            }
            if (xCollides() || xBoundCollides()) {
                startTileX += shapeWidth;
                updateTiles();
                revert = true;
            }
        }
        // If new form collides with other tiles, revert back to the previous form.
        if (revert) {
            setForm(previousFormID);
            gameManager.removePieceTiles(this);
            tiles.clear();
            tiles = previousTiles;
            gameManager.addPieceTiles(this);
        }
        if (xBoundCollides()) {
            for (int i = 1; i <= shapeWidth; i++) {
                startTileX -= 1;
                updateTiles();
                if (!xCollides()) {
                    break;
                }
            }
            if (xCollides()) {
                startTileX += shapeWidth;
                updateTiles();
                revert = true;
            }
        }
        // If new form collides with boundaries, revert back to the previous form.
        if (revert) {
            setForm(previousFormID);
            gameManager.removePieceTiles(this);
            tiles.clear();
            tiles = previousTiles;
            gameManager.addPieceTiles(this);
            return;
        }
    }

    public void updateTiles() {
        // Rotates tiles in a piece
        gameManager.removePieceTiles(this);
        tiles.clear();
        endTileX = startTileX + shapeWidth - 1;
        endTileY = startTileY + shapeHeight - 1;

        // Removes old tiles and creates new ones for a new form.
        int[][] formMap = currentForm.getMap();
        for (int y = 0; y < formMap.length; y++) {
            for (int x = 0; x < formMap[y].length; x++) {
                if (formMap[y][x] == 1) {
                    tiles.add(new Tile(game.getGameStartX() + (startTileX + x) * TILE_SIZE, game.getGameStartY() + (startTileY + y) * TILE_SIZE, velX, velY, startTileX + x, startTileY + y, game, this, Sprites.getTileSprite(shapeID)));
                }
            }
        }
        gameManager.addPieceTiles(this);
    }

    public boolean xBoundCollides() {
        return startTileX + velX < 0 || endTileX + velX > 9;
    }

    public boolean yBoundCollides() {
        return endTileY - velY > 19;
    }

    public boolean xCollides() {
        if (gameManager == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if (gameManager.getTileAt(tile, tile.getTileX() + velX, tile.getTileY()) != null) {
                return true;
            }
        }
        return false;
    }

    public boolean yCollides() {
        if (gameManager == null) {
            return false;
        }
        for (Tile tile : tiles) {
            if (gameManager.getTileAt(tile, tile.getTileX(), tile.getTileY() - velY) != null) {
                return true;
            }
        }
        return false;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public int getShapeID() {
        return shapeID;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public void setKeyInput(KeyInput keyInput) {
        this.keyInput = keyInput;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public int getStartTileX() {
        return startTileX;
    }

    public void setStartTileX(int startTileX) {
        this.startTileX = startTileX;
    }

    public int getStartTileY() {
        return startTileY;
    }

    public void setStartTileY(int startTileY) {
        this.startTileY = startTileY;
    }

    public int getEndTileX() {
        return endTileX;
    }

    public void setEndTileX(int endTileX) {
        this.endTileX = endTileX;
    }

    public int getEndTileY() {
        return endTileY;
    }

    public void setEndTileY(int endTileY) {
        this.endTileY = endTileY;
    }

    public boolean isPlaced() {
        return placed;
    }

    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public int getGravCounter() {
        return gravCounter;
    }

    public void setGravCounter(int gravCounter) {
        this.gravCounter = gravCounter;
    }

    public int getPlaceCounter() {
        return placeCounter;
    }

    public void setPlaceCounter(int placeCounter) {
        this.placeCounter = placeCounter;
    }

}
