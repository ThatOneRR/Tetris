package me.thatonerr.tetris.components;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.enums.ButtonIDs;
import me.thatonerr.tetris.framework.MouseInput;
import me.thatonerr.tetris.managers.UIClickManager;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static me.thatonerr.tetris.enums.UIStates.*;
import static me.thatonerr.tetris.enums.Sprites.*;

public class UIButton extends UIObject {

    private int buttonId;
    private UIClickManager UIClickManager;
    private MouseInput mouseInput;
    private BufferedImage currentSprite;
    private List<BufferedImage> sprites = new ArrayList<>();

    public UIButton(Game game, int buttonId, int x, int y, int width, int height) {
        super(game, x, y, width, height);
        this.buttonId = buttonId;
        UIClickManager = game.getUIClickManager();
        mouseInput = game.getMouseInput();
        sprites.add(BUTTON_BACKGROUND);
        sprites.add(BUTTON_BACKGROUND_ACTIVE);
        sprites.add(BUTTON_BACKGROUND_ACTIVE);
        sprites.add(BUTTON_BACKGROUND_ACTIVE);
    }

    @Override
    public void tick() {
        if (UIClickManager == null) {
            UIClickManager = game.getUIClickManager();
        }
        if (mouseInput.isDragging()) {
            return;
        }
        int mouseX = mouseInput.getMouseX();
        int mouseY = mouseInput.getMouseY();
        if (mouseX >= x && mouseX <= endX && mouseY >= y && mouseY <= endY) {
            onHover();
            if (mouseInput.isLeftPressed()) {
                onClick();
            }
        } else {
            changeState(IDLE);
        }
    }

    public void onHover() {
        changeState(HOVER);
    }

    public void onClick() {
        changeState(CLICK);
        UIClickManager.onButtonClick(buttonId);
    }

    public void onDrag() {
        changeState(DRAG);
    }

    public void changeState(int uiState) {
        currentSprite = sprites.get(uiState);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(Color.DARK_GRAY);
        graphics.drawImage(currentSprite, x, y, width, height, null);
        Font font = new Font(graphics.getFont().getName(), Font.PLAIN, 50);
        graphics.setFont(font);
        int x = this.x + width / 2 - 75;
        int y = this.y + height / 2 + 15;
        switch (buttonId) {
            case ButtonIDs.PLAY:
                graphics.drawString("Play", x, y);
                break;
            case ButtonIDs.PAUSE_SETTINGS:
                x -= 10;
            case ButtonIDs.SETTINGS:
                graphics.drawString("Settings", x, y);
                break;
            case ButtonIDs.QUIT:
                graphics.drawString("Quit", x, y);
                break;
            case ButtonIDs.RESUME:
                graphics.drawString("Resume", x - 10, y);
                break;
            case ButtonIDs.PAUSE_MAIN_MENU:
                x -= 10;
            case ButtonIDs.MAIN_MENU:
                graphics.drawString("Main Menu", x - 35, y);
                break;
            case ButtonIDs.PLAY_AGAIN:
                graphics.drawString("Play Again", x - 35, y);
                break;
            case ButtonIDs.PAUSE_SETTINGS_CONTROL:
                x -= 10;
            case ButtonIDs.CONTROLS:
                graphics.drawString("Controls", x - 20, y + 3);
                break;
            case ButtonIDs.SETTINGS_BACK:
            case ButtonIDs.CONTROLS_BACK:
            case ButtonIDs.PAUSE_CONTROLS_BACK:
            case ButtonIDs.PAUSE_SETTINGS_BACK:
                graphics.drawString("Back", x + 10, y);
                break;
            case ButtonIDs.PAUSE_RESTART:
                graphics.drawString("Restart", x - 10, y);
                break;
        }
    }

}
