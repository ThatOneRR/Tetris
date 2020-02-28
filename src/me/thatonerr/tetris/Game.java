package me.thatonerr.tetris;

import me.thatonerr.tetris.enums.Sprites;
import me.thatonerr.tetris.framework.KeyInput;
import me.thatonerr.tetris.framework.MouseInput;
import me.thatonerr.tetris.managers.UIClickManager;
import me.thatonerr.tetris.managers.FormManager;
import me.thatonerr.tetris.managers.GameManager;
import me.thatonerr.tetris.managers.StateManager;
import me.thatonerr.tetris.managers.UIManager;
import me.thatonerr.tetris.utilities.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferStrategy;

import static me.thatonerr.tetris.enums.GameStates.*;
import static me.thatonerr.tetris.enums.Settings.*;

public class Game implements Runnable {

    // Window and Display
    private String title;
    private int width, height;
    private Dimension dimension;
    private JFrame frame;
    private Canvas canvas;
    private BufferStrategy buffer;
    private Graphics graphics;

    // Event Listeners
    private KeyInput keyInput;
    private MouseInput mouseInput;

    // Game loop
    private boolean running;
    private int tps = 0, fps = 0;
    private Thread thread;

    // Game Properties
    private int gameStartX, gameStartY, gameEndX, gameEndY;

    // Game Managers
    private StateManager stateManager;
    private UIManager uiManager;
    private UIClickManager UIClickManager;
    private FormManager formManager;
    private GameManager gameManager;

    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        dimension = new Dimension(width, height);
        gameStartX = width / 2 - TILE_SIZE * 5;
        gameStartY = height / 2 - TILE_SIZE * 10;
        gameEndX = gameStartX + TILE_SIZE * 10;
        gameEndY = gameStartY + TILE_SIZE * 20;
        initWindow();
        initManagers();
    }

    private void tick() {
        uiManager.ticK();
        switch (stateManager.getCurrentState()) {
            case MAIN_MENU:
                break;
            case PAUSED:
            case INGAME:
                gameManager.tick();
                break;
        }
    }

    private void render() {
        buffer = canvas.getBufferStrategy();
        if (buffer == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        graphics = buffer.getDrawGraphics();
        graphics.clearRect(0, 0, width, height);

        Graphics2D g2d = (Graphics2D) graphics;
        int scale = Math.min(frame.getWidth() / width, frame.getHeight() / height);
        g2d.scale(scale, scale);

        Font font = new Font(graphics.getFont().getName(), Font.PLAIN, 70);
        switch (stateManager.getCurrentState()) {
            case MAIN_MENU:
                uiManager.render(g2d);
                break;
            case PAUSED:
                // Drawing game screen border
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.GRAY);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameEndX, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY - TILE_SIZE / 2, TILE_SIZE * 11, TILE_SIZE / 2);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameEndY, TILE_SIZE * 11, TILE_SIZE / 2);

                // Drawing game screen background tiles
                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 10; x++) {
                        g2d.drawImage(Sprites.GREY_TILE, gameStartX + x * TILE_SIZE, gameStartY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }
                gameManager.render(g2d);
                uiManager.render(g2d);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Paused", width / 2 - 120, height / 2 - 180);
                break;
            case PAUSED_SETTINGS:
                // Drawing game screen border
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.GRAY);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameEndX, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY - TILE_SIZE / 2, TILE_SIZE * 11, TILE_SIZE / 2);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameEndY, TILE_SIZE * 11, TILE_SIZE / 2);

                // Drawing game screen background tiles
                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 10; x++) {
                        g2d.drawImage(Sprites.GREY_TILE, gameStartX + x * TILE_SIZE, gameStartY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }
                gameManager.render(g2d);
                uiManager.render(g2d);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Settings", width / 2 - 120, height / 2 - 180);
                break;
            case PAUSED_CONTROLS:
                // Drawing game screen border
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.GRAY);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameEndX, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY - TILE_SIZE / 2, TILE_SIZE * 11, TILE_SIZE / 2);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameEndY, TILE_SIZE * 11, TILE_SIZE / 2);

                // Drawing game screen background tiles
                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 10; x++) {
                        g2d.drawImage(Sprites.GREY_TILE, gameStartX + x * TILE_SIZE, gameStartY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }
                gameManager.render(g2d);
                uiManager.render(g2d);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Controls", width / 2 - 120, 80);
                break;
            case SETTINGS:
                uiManager.render(g2d);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Settings", width / 2 - 120, height / 2 - 180);
                break;
            case SETTINGS_CONTROLS:
                uiManager.render(g2d);
                g2d.setFont(font);
                g2d.setColor(Color.WHITE);
                g2d.drawString("Controls", width / 2 - 120, 80);
                break;
            case INGAME:
                // Drawing game screen border
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.GRAY);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameEndX, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY - TILE_SIZE / 2, TILE_SIZE * 11, TILE_SIZE / 2);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameEndY, TILE_SIZE * 11, TILE_SIZE / 2);

                // Drawing game screen background tiles
                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 10; x++) {
                        g2d.drawImage(Sprites.GREY_TILE, gameStartX + x * TILE_SIZE, gameStartY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }

                // Rendering active tiles
                gameManager.render(g2d);
                break;
            case GAME_OVER:
                // Drawing game screen border
                g2d.setColor(Color.DARK_GRAY);
                g2d.fillRect(0, 0, width, height);
                g2d.setColor(Color.GRAY);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameEndX, gameStartY, TILE_SIZE / 2, TILE_SIZE * 20);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameStartY - TILE_SIZE / 2, TILE_SIZE * 11, TILE_SIZE / 2);
                g2d.fillRect(gameStartX - TILE_SIZE / 2, gameEndY, TILE_SIZE * 11, TILE_SIZE / 2);

                // Drawing game screen background tiles
                for (int y = 0; y < 20; y++) {
                    for (int x = 0; x < 10; x++) {
                        g2d.drawImage(Sprites.GREY_TILE, gameStartX + x * TILE_SIZE, gameStartY + y * TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
                    }
                }

                // Rendering active tiles
                gameManager.render(g2d);
                uiManager.render(g2d);
                g2d.setColor(Color.WHITE);
                g2d.setFont(font);
                g2d.drawString("Game Over", width / 2 - 190, height / 2 - 130);
                break;
        }

        g2d.dispose();
        buffer.show();
    }

    @Override
    public void run() {
        double delta = 0, ratio = 1e9 / 60.0;
        long current, previous = System.nanoTime(), timer = System.currentTimeMillis();

        while (running) {
            current = System.nanoTime();
            delta += (current - previous) / ratio;
            previous = current;
            while (delta >= 1) {
                tick();
                tps++;
                delta--;
            }
            render();
            fps++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                frame.setTitle(title + " | Tps: " + tps + " | Fps: " + fps);
                tps = 0;
                fps = 0;
            }
        }

        stop();
    }

    private void initManagers() {
        stateManager = new StateManager(this);
        uiManager = new UIManager(this);
        UIClickManager = new UIClickManager(this);
        formManager = new FormManager();
        gameManager = new GameManager(this);
    }

    private void initWindow() {
        frame = new JFrame(title);
        frame.setSize(dimension);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(dimension);
        canvas.setFocusable(false);

        frame.add(canvas);
        frame.pack();

        keyInput = new KeyInput();
        mouseInput = new MouseInput();
        frame.addKeyListener(keyInput);
        frame.addMouseListener(mouseInput);
        frame.addMouseMotionListener(mouseInput);
        canvas.addMouseListener(mouseInput);
        canvas.addMouseMotionListener(mouseInput);
    }

    public synchronized void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop() {
        if (!running) {
            return;
        }
        running = false;
        Thread.currentThread().interrupt();
        Utils.log("Bye bye!");
        System.exit(0);
    }

    public UIManager getUiManager() {
        return uiManager;
    }

    public StateManager getStateManager() {
        return stateManager;
    }

    public UIClickManager getUIClickManager() {
        return UIClickManager;
    }

    public GameManager getGameManager() {
        return gameManager;
    }

    public KeyInput getKeyInput() {
        return keyInput;
    }

    public MouseInput getMouseInput() {
        return mouseInput;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGameStartX() {
        return gameStartX;
    }

    public int getGameStartY() {
        return gameStartY;
    }

    public int getGameEndX() {
        return gameEndX;
    }

    public int getGameEndY() {
        return gameEndY;
    }

}
