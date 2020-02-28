package me.thatonerr.tetris.components;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.enums.Control;
import me.thatonerr.tetris.framework.MouseInput;
import me.thatonerr.tetris.managers.UIClickManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static me.thatonerr.tetris.enums.Sprites.*;
import static me.thatonerr.tetris.enums.UIStates.*;

public class UIControlButton extends UIObject {

    private boolean arrows;
    private int buttonId;
    private UIClickManager UIClickManager;
    private MouseInput mouseInput;
    private BufferedImage currentSprite;
    private List<BufferedImage> sprites = new ArrayList<>();

    public UIControlButton(Game game, int buttonId, int x, int y, int width, int height) {
        super(game, x, y, width, height);
        this.buttonId = buttonId;
        UIClickManager = game.getUIClickManager();
        mouseInput = game.getMouseInput();
        sprites.add(CONTROLS_BUTTON_BACKGROUND);
        sprites.add(CONTROLS_BUTTON_BACKGROUND_ACTIVE);
        sprites.add(CONTROLS_BUTTON_BACKGROUND_ACTIVE);
    }

    @Override
    public void tick() {
        if (UIClickManager == null) {
            UIClickManager = game.getUIClickManager();
        }
        if (!UIClickManager.getControlButtons().contains(this)) {
            UIClickManager.addControlButton(this);
        }
        if (UIClickManager.getActiveControl() != null) {
            int activeKey = game.getKeyInput().getActiveKey();
            if (activeKey == -1) {
                return;
            }
            List<UIControlButton> controlButtons = UIClickManager.getControlButtons();
            for (int i = 0; i < controlButtons.size(); i++) {
                if (Control.values()[i].getKeyCode() == activeKey) {
                    Control.values()[i].setKeyCode(-1);
                }
            }
            Control.values()[controlButtons.indexOf(UIClickManager.getActiveControl())].setKeyCode(activeKey);
            UIClickManager.getActiveControl().arrows = false;
            UIClickManager.setActiveControl(null);
            return;
            /*switch (buttonManager.getActiveControl().buttonId) {
                case UP_KEY_CONTROL:
                    Control.UP_KEY.setKeyCode(activeKey);
                    break;
                case DOWN_KEY_CONTROL:
                    Control.DOWN_KEY.setKeyCode(activeKey);
                    break;
                case LEFT_KEY_CONTROL:
                    Control.DOWN_KEY.setKeyCode(activeKey);
                    break;
                case RIGHT_KEY_CONTROL:
                    Control.UP_KEY.setKeyCode(activeKey);
                    break;
                case SPACE_KEY_CONTROL:
                    Control.UP_KEY.setKeyCode(activeKey);
                    break;
                case HOLD_KEY_CONTROL:
                    Control.UP_KEY.setKeyCode(activeKey);
                    break;
                case PAUSE_KEY_CONTROL:
                    Control.UP_KEY.setKeyCode(activeKey);
                    break;
            }*/
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
        if (arrows) {
            changeState(CLICK);
        }
    }

    public void onHover() {
        changeState(HOVER);
    }

    public void onClick() {
        if (arrows) {
            return;
        }
        changeState(CLICK);
        UIClickManager.onButtonClick(buttonId);
        arrows = true;
        UIClickManager.setActiveControl(this);
    }

    public void changeState(int uiState) {
        currentSprite = sprites.get(uiState);
    }

    @Override
    public void render(Graphics graphics) {
        if (UIClickManager == null) {
            UIClickManager = game.getUIClickManager();
        }
        if (!UIClickManager.getControlButtons().contains(this)) {
            UIClickManager.addControlButton(this);
        }
        graphics.drawImage(currentSprite, x, y, width, height, null);
        Font font = new Font(graphics.getFont().getName(), Font.PLAIN, 40);
        graphics.setFont(font);
        graphics.setColor(Color.WHITE);
        if (arrows) {
            graphics.drawString(">", x + 10, y + 40);
            graphics.drawString("<", x + width - 35, y + 40);
        }
        font = new Font(graphics.getFont().getName(), Font.PLAIN, 30);
        graphics.setFont(font);
        int x = this.x + width / 2 - 50;
        int y = this.y + height / 2 + 10;
        int keyCode = Control.values()[UIClickManager.getControlButtons().indexOf(this)].getKeyCode();
        if (keyCode == -1) {
            graphics.drawString("None", x, y);
        } else {
            graphics.drawString(KeyEvent.getKeyText(keyCode), x, y);
        }
        /*switch (buttonId) {
            case UP_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.UP_KEY.getKeyCode()), x, y);
                break;
            case DOWN_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.DOWN_KEY.getKeyCode()), x, y);
                break;
            case LEFT_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.LEFT_KEY.getKeyCode()), x, y);
                break;
            case RIGHT_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.RIGHT_KEY.getKeyCode()), x, y);
                break;
            case SPACE_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.SPACE_KEY.getKeyCode()), x, y);
                break;
            case HOLD_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.HOLD_KEY.getKeyCode()), x, y);
                break;
            case PAUSE_KEY_CONTROL:
                graphics.drawString(KeyEvent.getKeyText(Control.PAUSE_KEY.getKeyCode()), x, y);
                break;
        }*/
    }

}
