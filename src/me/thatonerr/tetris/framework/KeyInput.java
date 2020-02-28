package me.thatonerr.tetris.framework;

import me.thatonerr.tetris.enums.Control;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {

    private int activeKey = -1;
    private boolean up, down, left, right, space, hold, pause;

    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();
        activeKey = keyCode;
        if (keyCode == Control.UP_KEY.getKeyCode()) {
            up = true;
        } else if (keyCode == Control.DOWN_KEY.getKeyCode()) {
            down = true;
        } else if (keyCode == Control.LEFT_KEY.getKeyCode()) {
            left = true;
        } else if (keyCode == Control.RIGHT_KEY.getKeyCode()) {
            right = true;
        } else if (keyCode == Control.SPACE_KEY.getKeyCode()) {
            space = true;
        } else if (keyCode == Control.HOLD_KEY.getKeyCode()) {
            hold = true;
        } else if (keyCode == Control.PAUSE_KEY.getKeyCode()) {
            pause = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        activeKey = -1;
        int keyCode = event.getKeyCode();
        if (keyCode == Control.UP_KEY.getKeyCode()) {
            up = false;
        } else if (keyCode == Control.DOWN_KEY.getKeyCode()) {
            down = false;
        } else if (keyCode == Control.LEFT_KEY.getKeyCode()) {
            left = false;
        } else if (keyCode == Control.RIGHT_KEY.getKeyCode()) {
            right = false;
        } else if (keyCode == Control.SPACE_KEY.getKeyCode()) {
            space = false;
        } else if (keyCode == Control.HOLD_KEY.getKeyCode()) {
            hold = false;
        } else if (keyCode == Control.PAUSE_KEY.getKeyCode()) {
            pause = false;
        }
    }


    @Override
    public void keyTyped(KeyEvent event) {

    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public boolean isHold() {
        return hold;
    }

    public void setHold(boolean hold) {
        this.hold = hold;
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public int getActiveKey() {
        return activeKey;
    }

    public void setActiveKey(int activeKey) {
        this.activeKey = activeKey;
    }

}
