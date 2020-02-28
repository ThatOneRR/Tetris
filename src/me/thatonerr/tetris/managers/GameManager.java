package me.thatonerr.tetris.managers;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.components.Piece;
import me.thatonerr.tetris.components.Tile;
import me.thatonerr.tetris.enums.GameStates;
import me.thatonerr.tetris.enums.Sprites;
import me.thatonerr.tetris.framework.AudioPlayer;
import me.thatonerr.tetris.framework.KeyInput;

import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static me.thatonerr.tetris.enums.Settings.TILE_SIZE;

public class GameManager {

    private Game game;
    private KeyInput keyInput;
    private StateManager stateManager;
    private boolean spaced = false, gameOver, switched = false, paused = false;
    private Piece piece, heldPiece, firstNext, secondNext, thirdNext;
    private List<Tile> tiles = new ArrayList<>();
    private int clearCounter = 0, clearSpeed = 10, gravSpeed = 60;
    private int score;
    private AudioPlayer bgmMusic;

    public GameManager(Game game) {
        this.game = game;
        keyInput = game.getKeyInput();
        stateManager = game.getStateManager();
        bgmMusic = new AudioPlayer("/audio/tetris_theme.wav");
    }

    public void tick() {
        int state = stateManager.getCurrentState();
        if (keyInput.isPause() && (state == GameStates.INGAME || state == GameStates.PAUSED)) {
            // If game is running / in main pause screen, escape will reverse the pause state.
            paused = !paused;
            keyInput.setPause(false);
            if (state == GameStates.INGAME) {
                stateManager.setCurrentState(GameStates.PAUSED);
                bgmMusic.pause();
            } else {
                // Paused
                stateManager.setCurrentState(GameStates.INGAME);
                bgmMusic.resume();
            }
        }
        // If game is game-over or paused, don't tick any pieces/tiles
        if (gameOver || paused) return;
        // Tick the tiles / pieces in the game.
        if (piece.isPlaced()) {
            clearLines();
            piece = firstNext;
            firstNext = secondNext;
            secondNext = thirdNext;
            thirdNext = createRandomPiece();
            // When piece lands, set switch to false to let the user switch again on the next piece.
            switched = false;
            tiles.addAll(piece.getTiles());
        } else {
            piece.tick();
        }
        int stage = score / 10000;
        gravSpeed = 60 - stage * 10;
        if (gravSpeed <= 1) gravSpeed = 1;
    }

    public void showHold(Graphics graphics) {
        int startX = (int) (game.getGameStartX() - TILE_SIZE / 2 - 120 - TILE_SIZE * 4 * 0.95);
        int startY = game.getGameStartY() - TILE_SIZE / 2 + 45;
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 30));
        graphics.drawString("Hold", startX + 75, startY - 15);
        graphics.setColor(Color.GRAY);
        graphics.fillRect(startX, startY, (int) (TILE_SIZE * 4 * 0.95) + 60, (int) (TILE_SIZE * 4 * 0.95) + 60);
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(startX + 15, startY + 15, (int) (TILE_SIZE * 4 * 0.95) + 30, (int) (TILE_SIZE * 4 * 0.95) + 30);

        if (heldPiece == null) return;
        startX += 30 + (int) ((TILE_SIZE * 4 * 0.95 - heldPiece.getCurrentForm().getTileWidth() * TILE_SIZE * 0.95) / 2);
        startY += 30 + (int) ((TILE_SIZE * 4 * 0.95 - heldPiece.getCurrentForm().getTileHeight() * TILE_SIZE * 0.95) / 2);
        BufferedImage sprite = Sprites.getTileSprite(heldPiece.getShapeID());
        int[][] formMap = heldPiece.getCurrentForm().getMap();
        for (int y = 0; y < formMap.length; y++) {
            for (int x = 0; x < formMap[y].length; x++) {
                if (formMap[y][x] == 1) {
                    graphics.drawImage(sprite, (int) (startX + x * TILE_SIZE * 0.95), (int) (startY + y * TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), null);
                }
            }
        }
    }

    public void showNextUps(Graphics graphics) {
        int startX = (int) (game.getGameStartX() - TILE_SIZE / 2 - 120 - TILE_SIZE * 4 * 0.95);
        int startY = (int) (game.getGameEndY() + TILE_SIZE / 2 - TILE_SIZE * 12 * 0.95 - 70);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 30));
        graphics.drawString("Next Up", startX + 55, startY - 15);
        graphics.setColor(Color.GRAY);
        graphics.fillRect(startX, startY, (int) (TILE_SIZE * 4 * 0.95) + 60, (int) (TILE_SIZE * 12 * 0.95) + 70);
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(startX + 15, startY + 15, (int) (TILE_SIZE * 4 * 0.95) + 30, (int) (TILE_SIZE * 12 * 0.95) + 40);

        int thisX = startX + 30 + (int) ((TILE_SIZE * 4 * 0.95 - firstNext.getCurrentForm().getTileWidth() * TILE_SIZE * 0.95) / 2);
        int thisY = startY + 25 + (int) ((TILE_SIZE * 4 * 0.95 - firstNext.getCurrentForm().getTileHeight() * TILE_SIZE * 0.95) / 2);
        int margin = (int) (TILE_SIZE * 0.95);
        int[][] formMap1 = firstNext.getCurrentForm().getMap();
        BufferedImage sprite1 = Sprites.getTileSprite(firstNext.getShapeID());
        for (int y = 0; y < formMap1.length; y++) {
            for (int x = 0; x < formMap1[y].length; x++) {
                if (formMap1[y][x] == 1) {
                    graphics.drawImage(sprite1, (int) (thisX + x * TILE_SIZE * 0.95), (int) (thisY + y * TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), null);
                }
            }
        }

        thisX = startX + 30 + (int) ((TILE_SIZE * 4 * 0.95 - secondNext.getCurrentForm().getTileWidth() * TILE_SIZE * 0.95) / 2);
        thisY = startY + 35 + (int) ((TILE_SIZE * 4 * 0.95 - secondNext.getCurrentForm().getTileHeight() * TILE_SIZE * 0.95) / 2) + margin  * 4;
        int[][] formMap2 = secondNext.getCurrentForm().getMap();
        BufferedImage sprite2 = Sprites.getTileSprite(secondNext.getShapeID());
        for (int y = 0; y < formMap2.length; y++) {
            for (int x = 0; x < formMap2[y].length; x++) {
                if (formMap2[y][x] == 1) {
                    graphics.drawImage(sprite2, (int) (thisX + x * TILE_SIZE * 0.95), (int) (thisY + y * TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), null);
                }
            }
        }

        thisX = startX + 30 + (int) ((TILE_SIZE * 4 * 0.95 - thirdNext.getCurrentForm().getTileWidth() * TILE_SIZE * 0.95) / 2);
        thisY = startY + 45 + (int) ((TILE_SIZE * 4 * 0.95 - thirdNext.getCurrentForm().getTileHeight() * TILE_SIZE * 0.95) / 2) + margin  * 8;
        int[][] formMap3 = thirdNext.getCurrentForm().getMap();
        BufferedImage sprite3 = Sprites.getTileSprite(thirdNext.getShapeID());
        for (int y = 0; y < formMap3.length; y++) {
            for (int x = 0; x < formMap3[y].length; x++) {
                if (formMap3[y][x] == 1) {
                    graphics.drawImage(sprite3, (int) (thisX + x * TILE_SIZE * 0.95), (int) (thisY + y * TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), (int) (TILE_SIZE * 0.95), null);
                }
            }
        }
    }

    public void showScore(Graphics graphics) {
        int startX = (int) (game.getGameEndX() + TILE_SIZE / 2 + 70);
        int startY = (int) (game.getGameStartY() - TILE_SIZE / 2 + 45);
        graphics.setColor(Color.WHITE);
        graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 30));
        graphics.drawString("Score", startX + 70, startY - 15);
        graphics.setColor(Color.GRAY);
        graphics.fillRect(startX, startY, (int) (TILE_SIZE * 4 * 0.95 + 60), 50);
        graphics.setColor(Color.DARK_GRAY);
        graphics.fillRect(startX + 5, startY + 5, (int) (TILE_SIZE * 4 * 0.95 + 60 - 10), 50 - 10);
        graphics.setColor(Color.WHITE);

        startX = (int) (startX + TILE_SIZE * 2.75 + 10) - (String.valueOf(score).length() * 11);
        graphics.drawString(score + "", startX, startY + 35);
    }

    public void switchPieces() {
        // If the user has switched already, don't run this.
        if (switched) return;

        // Remove current piece and store it in hold.
        tiles.removeAll(piece.getTiles());

        // Change piece's tiles back to top-middle.
        piece.updatePiece(4, -1);

        // Swap out the held piece as the current piece.
        Piece tempPiece = heldPiece;
        heldPiece = piece;
        piece = tempPiece;

        // If there is no held piece, generate a new piece instead.
        if (piece == null) {
            piece = firstNext;
            firstNext = secondNext;
            secondNext = thirdNext;
            thirdNext = createRandomPiece();
        }

        // Add the current swapped piece / new piece to the render/tick methods.
        tiles.addAll(piece.getTiles());

        // Set the switched to true.
        switched = true;
    }

    public void startGame() {
        gameOver = false;
        switched = false;
        paused = false;
        score =     0;
        stateManager.setCurrentState(GameStates.INGAME);
        tiles.clear();
        heldPiece = null;
        piece = createRandomPiece();
        firstNext = createRandomPiece();
        secondNext = createRandomPiece();
        thirdNext = createRandomPiece();
        tiles.addAll(piece.getTiles());
        game.getUiManager().getSettingsMusicSlider().updateVolume();
        bgmMusic.reset();
        bgmMusic.getClip().loop(Clip.LOOP_CONTINUOUSLY);
        bgmMusic.play();
    }

    public void endGame() {
        gameOver = true;
        bgmMusic.stop();
        stateManager.setCurrentState(GameStates.GAME_OVER);
    }

    public void render(Graphics graphics) {
        showHold(graphics);
        showNextUps(graphics);
        showScore(graphics);
        piece.showPrediction(graphics);
        for (Tile tile : tiles) {
            tile.render(graphics);
        }
    }

    public void clearLines() {
        boolean hasNext = true;
        while (hasNext) {
            if (clearCounter < clearSpeed) {
                clearCounter++;
            } else {
                clearCounter = 0;
                hasNext = clearLine();
            }
        }
    }

    public boolean clearLine() {
        boolean isDone = false;
        for (int y = 0; y < 20; y++) {
            List<Tile> line = new ArrayList<>();
            boolean empty = false;
            for (int x = 0; x < 10; x++) {
                // Check if line has 1 or more empty tiles
                if (getTileAt(null, x, y) == null) {
                    empty = true;
                } else {
                    line.add(getTileAt(null, x, y));
                }
            }
            // If a line is complete, it has no empty tiles.
            if (!empty) {
                // If one line is already cleared and there's still another to clear, return true.
                if (isDone) {
                    return true;
                }
                // Remove the line from the game (i.e. remove from list of all tiles).
                tiles.removeAll(line);
                score += 1000;
                // Move all tiles above that cleared line down by 1 (since the line is cleared now)
                for (Tile tile : getAllTilesAbove(y)) {
                    tile.setTileY(tile.getTileY() + 1);
                    tile.tickPos();
                }
                isDone = true;
            }
        }
        return false;
    }

    public List<Tile> getAllTilesAbove(int tileY) {
        List<Tile> above = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile.getTileY() < tileY) {
                above.add(tile);
            }
        }
        return above;
    }


    public void removePieceTiles(Piece piece) {
        List<Tile> removal = new ArrayList<>();
        for (Tile tile : tiles) {
            if (tile.getParent().equals(piece)) {
                removal.add(tile);
            }
        }
        tiles.removeAll(removal);
    }

    public void addPieceTiles(Piece piece) {
        tiles.addAll(piece.getTiles());
    }

    private Piece createRandomPiece() {
        return new Piece(game, 4, -1, (int) Math.floor(Math.random() * 7), gravSpeed);
    }

    public int getLowestTileY(Tile tile, int tileX, int tileY) {
        int lowestY = 20;
        List<Tile> fileTiles = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Tile each = getTileAt(tile, tileX, i);
            if (each != null) {
                fileTiles.add(each);
            }
        }
        if (fileTiles.isEmpty()) {
            return 20;
        }
        for (Tile each : fileTiles) {
            if (each.getTileY() > tileY && each.getTileY() < lowestY) {
                lowestY = each.getTileY();
            }
        }
        if (lowestY == tileY) {
            return 20;
        }
        return lowestY;
    }

    public Tile getTileAt(Tile tile, int tileX, int tileY) {
        for (Tile each : tiles) {
            if (tile != null && (each.equals(tile) || each.getParent().equals(tile.getParent()))) {
                continue;
            }
            if (each.getTileX() == tileX && each.getTileY() == tileY) {
                return each;
            }
        }
        return null;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isSpaced() {
        return spaced;
    }

    public void setSpaced(boolean spaced) {
        this.spaced = spaced;
    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public int getGravSpeed() {
        return gravSpeed;
    }

    public void setGravSpeed(int gravSpeed) {
        this.gravSpeed = gravSpeed;
    }

    public AudioPlayer getBgmMusic() {
        return bgmMusic;
    }

}
