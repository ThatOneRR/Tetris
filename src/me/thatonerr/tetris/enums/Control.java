package me.thatonerr.tetris.enums;

import static java.awt.event.KeyEvent.*;

public enum Control {

    UP_KEY(VK_UP), DOWN_KEY(VK_DOWN), LEFT_KEY(VK_LEFT), RIGHT_KEY(VK_RIGHT), SPACE_KEY(VK_SPACE), HOLD_KEY(VK_F), PAUSE_KEY(VK_ESCAPE);

    private int keyCode;

    Control(int keyCode) {
        this.keyCode = keyCode;
    }

    public void unassign(int keyCode) {
        for (Control control : values()) {
            if (control.getKeyCode() == keyCode) {
                control.setKeyCode(-1);
            }
        }
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

}
