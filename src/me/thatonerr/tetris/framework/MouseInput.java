package me.thatonerr.tetris.framework;

import me.thatonerr.tetris.utilities.Utils;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import static java.awt.event.MouseEvent.*;

public class MouseInput implements MouseListener, MouseMotionListener {

    private boolean leftPressed, rightPressed, dragging;
    private int mouseX, mouseY;

    @Override
    public void mouseClicked(MouseEvent event) {

    }

    @Override
    public void mousePressed(MouseEvent event) {
        switch (event.getButton()) {
            case BUTTON1:
                leftPressed = true;
                break;
            case BUTTON2:
                rightPressed = true;
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        switch (event.getButton()) {
            case BUTTON1:
                leftPressed = false;
                break;
            case BUTTON2:
                rightPressed = false;
                break;
        }
        dragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent event) {

    }

    @Override
    public void mouseExited(MouseEvent event) {

    }

    @Override
    public void mouseDragged(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
        dragging = true;
    }

    @Override
    public void mouseMoved(MouseEvent event) {
        mouseX = event.getX();
        mouseY = event.getY();
    }

    public boolean isDragging() {
        return dragging;
    }

    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

}
