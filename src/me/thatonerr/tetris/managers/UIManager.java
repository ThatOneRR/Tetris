package me.thatonerr.tetris.managers;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.components.UIButton;
import me.thatonerr.tetris.components.UIControlButton;
import me.thatonerr.tetris.components.UISlider;
import me.thatonerr.tetris.enums.ButtonIDs;
import me.thatonerr.tetris.enums.GameStates;
import me.thatonerr.tetris.enums.Sprites;

import java.awt.*;

public class UIManager {

    private Game game;
    private StateManager stateManager;
    private GameManager gameManager;

    // Main Menu UI
    private UIButton playButton;
    private UIButton settingsButton;
    private UIButton quitButton;

    // Game Over UI
    private UIButton playAgainButton;
    private UIButton mainMenuButton;

    // Settings UI
    private UIButton settingsControlsButton;
    private UISlider settingsMusicSlider;
    private UIButton settingsBackButton;

    // Settings Control UI
    private UIControlButton upControlButton;
    private UIControlButton downControlButton;
    private UIControlButton leftControlButton;
    private UIControlButton rightControlButton;
    private UIControlButton spaceControlButton;
    private UIControlButton holdControlButton;
    private UIControlButton pauseControlButton;
    private UIButton controlBackButton;

    // Pause UI
    private UIButton pauseResumeButton;
    private UIButton pauseRestartButton;
    private UIButton pauseSettingsButton;
    private UIButton pauseMainMenuButton;

    // Pause Settings UI
    private UIButton pauseSettingsControlButton;
    private UISlider pauseSettingsMusicSlider;
    private UIButton pauseSettingsBackButton;

    // Pause Control UI
    private UIButton pauseControlBackButton;

    public UIManager(Game game) {
        this.game = game;
        stateManager = game.getStateManager();
        gameManager = game.getGameManager();

        playButton = new UIButton(game, ButtonIDs.PLAY, game.getGameEndX(), game.getHeight() / 2 - 175, 350, 75);
        settingsButton = new UIButton(game, ButtonIDs.SETTINGS, game.getGameEndX(), game.getHeight() / 2 - 75, 350, 75);
        quitButton = new UIButton(game, ButtonIDs.QUIT, game.getGameEndX(), game.getHeight() / 2 + 25, 350, 75);

        playAgainButton = new UIButton(game, ButtonIDs.PLAY_AGAIN, game.getWidth() / 2 - 175, game.getHeight() / 2 + 25, 350, 75);
        mainMenuButton = new UIButton(game, ButtonIDs.MAIN_MENU, game.getWidth() / 2 - 175, game.getHeight() / 2 + 125, 350, 75);

        settingsControlsButton = new UIButton(game, ButtonIDs.CONTROLS, game.getWidth() / 2 - 172, game.getHeight() / 2 - 85, 350, 75);
        settingsMusicSlider = new UISlider(game, game.getWidth() / 2 - 100, game.getHeight() / 2 + 50, 500, 5, ButtonIDs.SETTINGS_MUSIC_SLIDER);
        settingsBackButton = new UIButton(game, ButtonIDs.SETTINGS_BACK, game.getWidth() / 2 - 172, game.getHeight() / 2 + 225, 350, 75);

        upControlButton = new UIControlButton(game, ButtonIDs.UP_KEY_CONTROL, game.getWidth() / 2 + 100, 120, 200, 50);
        downControlButton = new UIControlButton(game, ButtonIDs.DOWN_KEY_CONTROL, game.getWidth() / 2 + 100, 190, 200, 50);
        leftControlButton = new UIControlButton(game, ButtonIDs.LEFT_KEY_CONTROL, game.getWidth() / 2 + 100, 260, 200, 50);
        rightControlButton = new UIControlButton(game, ButtonIDs.RIGHT_KEY_CONTROL, game.getWidth() / 2 + 100, 330, 200, 50);
        spaceControlButton = new UIControlButton(game, ButtonIDs.SPACE_KEY_CONTROL, game.getWidth() / 2 + 100, 400, 200, 50);
        holdControlButton = new UIControlButton(game, ButtonIDs.HOLD_KEY_CONTROL, game.getWidth() / 2 + 100, 470, 200, 50);
        pauseControlButton = new UIControlButton(game, ButtonIDs.PAUSE_KEY_CONTROL, game.getWidth() / 2 + 100, 540, 200, 50);

        controlBackButton = new UIButton(game, ButtonIDs.CONTROLS_BACK, game.getWidth() / 2 - 150, 800, 350, 75);
        pauseControlBackButton = new UIButton(game, ButtonIDs.PAUSE_CONTROLS_BACK, game.getWidth() / 2 - 150, 800, 350, 75);

        pauseResumeButton = new UIButton(game, ButtonIDs.RESUME, game.getWidth() / 2 - 175, game.getHeight() / 2 - 140, 350, 75);
        pauseRestartButton = new UIButton(game, ButtonIDs.PAUSE_RESTART, game.getWidth() / 2 - 175, game.getHeight() / 2 - 40, 350, 75);
        pauseSettingsButton = new UIButton(game, ButtonIDs.PAUSE_SETTINGS, game.getWidth() / 2 - 175, game.getHeight() / 2 + 60, 350, 75);
        pauseMainMenuButton = new UIButton(game, ButtonIDs.PAUSE_MAIN_MENU, game.getWidth() / 2 - 175, game.getHeight() / 2 + 160, 350, 75);

        pauseSettingsControlButton = new UIButton(game, ButtonIDs.PAUSE_SETTINGS_CONTROL, game.getWidth() / 2 - 172, game.getHeight() / 2 - 85, 350, 75);
        pauseSettingsMusicSlider = new UISlider(game, game.getWidth() / 2 - 100, game.getHeight() / 2 + 50, 500, 5, ButtonIDs.PAUSE_SETTINGS_MUSIC_SLIDER);
        pauseSettingsBackButton = new UIButton(game, ButtonIDs.PAUSE_SETTINGS_BACK, game.getWidth() / 2 - 172, game.getHeight() / 2 + 225, 350, 75);
    }

    public void ticK() {
        if (gameManager == null) {
            gameManager = game.getGameManager();
        }
        switch (stateManager.getCurrentState()) {
            case GameStates.MAIN_MENU:
                playButton.tick();
                settingsButton.tick();
                quitButton.tick();
                break;
            case GameStates.GAME_OVER:
                playAgainButton.tick();
                mainMenuButton.tick();
                break;
            case GameStates.PAUSED:
                pauseResumeButton.tick();
                pauseRestartButton.tick();
                pauseSettingsButton.tick();
                pauseMainMenuButton.tick();
                break;
            case GameStates.PAUSED_SETTINGS:
                pauseSettingsControlButton.tick();
                pauseSettingsMusicSlider.tick();
                pauseSettingsBackButton.tick();
                break;
            case GameStates.PAUSED_CONTROLS:
                upControlButton.tick();
                downControlButton.tick();
                leftControlButton.tick();
                rightControlButton.tick();
                spaceControlButton.tick();
                holdControlButton.tick();
                pauseControlButton.tick();
                pauseControlBackButton.tick();
                break;
            case GameStates.SETTINGS:
                settingsControlsButton.tick();
                settingsMusicSlider.tick();
                settingsBackButton.tick();
                break;
            case GameStates.SETTINGS_CONTROLS:
                upControlButton.tick();
                downControlButton.tick();
                leftControlButton.tick();
                rightControlButton.tick();
                spaceControlButton.tick();
                holdControlButton.tick();
                pauseControlButton.tick();
                controlBackButton.tick();
                break;
        }
    }

    public void render(Graphics graphics) {
        if (gameManager == null) {
            gameManager = game.getGameManager();
        }
        switch (stateManager.getCurrentState()) {
            case GameStates.MAIN_MENU:
                graphics.drawImage(Sprites.MAIN_BACKGROUND, 0, 0, game.getWidth(), game.getHeight(), null);
                playButton.render(graphics);
                settingsButton.render(graphics);
                quitButton.render(graphics);
                break;
            case GameStates.GAME_OVER:
                graphics.setColor(new Color(100, 100, 100, 180));
                graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 45));
                int scoreX = game.getWidth() / 2 - 90 - String.valueOf(gameManager.getScore()).length() * 10;
                graphics.drawString("Score: " + gameManager.getScore(), scoreX, game.getHeight() / 2 - 75);
                playAgainButton.render(graphics);
                mainMenuButton.render(graphics);
                break;
            case GameStates.PAUSED:
                graphics.setColor(new Color(100, 100, 100, 180));
                graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
                pauseResumeButton.render(graphics);
                pauseRestartButton.render(graphics);
                pauseSettingsButton.render(graphics);
                pauseMainMenuButton.render(graphics);
                break;
            case GameStates.PAUSED_SETTINGS:
                graphics.setColor(new Color(100, 100, 100, 180));
                graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
                pauseSettingsControlButton.render(graphics);
                pauseSettingsMusicSlider.render(graphics);
                pauseSettingsBackButton.render(graphics);
                graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 40));
                graphics.setColor(Color.WHITE);
                graphics.drawString("Music", pauseSettingsMusicSlider.getX() - 150, pauseSettingsMusicSlider.getY() + 15);
                break;
            case GameStates.PAUSED_CONTROLS:
                graphics.setColor(new Color(100, 100, 100, 180));
                graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
                upControlButton.render(graphics);
                downControlButton.render(graphics);
                leftControlButton.render(graphics);
                rightControlButton.render(graphics);
                spaceControlButton.render(graphics);
                holdControlButton.render(graphics);
                pauseControlButton.render(graphics);
                pauseControlBackButton.render(graphics);
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 35));
                graphics.drawString("Rotate", upControlButton.getX() - 350, upControlButton.getY() + 35);
                graphics.drawString("Move Down", downControlButton.getX() - 350, downControlButton.getY() + 35);
                graphics.drawString("Move Left", leftControlButton.getX() - 350, leftControlButton.getY() + 35);
                graphics.drawString("Move Right", rightControlButton.getX() - 350, rightControlButton.getY() + 35);
                graphics.drawString("Place Down", spaceControlButton.getX() - 350, spaceControlButton.getY() + 35);
                graphics.drawString("Hold/Swap Piece", holdControlButton.getX() - 350, holdControlButton.getY() + 35);
                graphics.drawString("Pause", pauseControlButton.getX() - 350, pauseControlButton.getY() + 35);
                break;
            case GameStates.SETTINGS:
                graphics.drawImage(Sprites.MAIN_BACKGROUND, 0, 0, game.getWidth(), game.getHeight(), null);
                graphics.setColor(new Color(100, 100, 100, 120));
                graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
                settingsControlsButton.render(graphics);
                settingsMusicSlider.render(graphics);
                settingsBackButton.render(graphics);
                graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 40));
                graphics.setColor(Color.WHITE);
                graphics.drawString("Music", settingsMusicSlider.getX() - 150, settingsMusicSlider.getY() + 15);
                break;
            case GameStates.SETTINGS_CONTROLS:
                graphics.drawImage(Sprites.MAIN_BACKGROUND, 0, 0, game.getWidth(), game.getHeight(), null);
                graphics.setColor(new Color(100, 100, 100, 120));
                graphics.fillRect(0, 0, game.getWidth(), game.getHeight());
                upControlButton.render(graphics);
                downControlButton.render(graphics);
                leftControlButton.render(graphics);
                rightControlButton.render(graphics);
                spaceControlButton.render(graphics);
                holdControlButton.render(graphics);
                pauseControlButton.render(graphics);
                controlBackButton.render(graphics);
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font(graphics.getFont().getName(), Font.PLAIN, 35));
                graphics.drawString("Rotate", upControlButton.getX() - 350, upControlButton.getY() + 35);
                graphics.drawString("Move Down", downControlButton.getX() - 350, downControlButton.getY() + 35);
                graphics.drawString("Move Left", leftControlButton.getX() - 350, leftControlButton.getY() + 35);
                graphics.drawString("Move Right", rightControlButton.getX() - 350, rightControlButton.getY() + 35);
                graphics.drawString("Place Down", spaceControlButton.getX() - 350, spaceControlButton.getY() + 35);
                graphics.drawString("Hold/Swap Piece", holdControlButton.getX() - 350, holdControlButton.getY() + 35);
                graphics.drawString("Pause", pauseControlButton.getX() - 350, pauseControlButton.getY() + 35);
                break;
        }
    }

    public UISlider getSettingsMusicSlider() {
        return settingsMusicSlider;
    }

}
