package me.thatonerr.tetris.components;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.enums.Sprites;
import me.thatonerr.tetris.framework.AudioPlayer;
import me.thatonerr.tetris.framework.KeyInput;
import me.thatonerr.tetris.framework.MouseInput;
import me.thatonerr.tetris.managers.GameManager;
import me.thatonerr.tetris.managers.UIClickManager;

import javax.sound.sampled.FloatControl;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;

import static me.thatonerr.tetris.enums.UIStates.*;

public class UISlider extends UIObject {

    private MouseInput mouseInput;
    private GameManager gameManager;
    private boolean thumbActive = false;
    private int buttonId, thumbX, thumbEndX, thumbY, thumbEndY, thumbWidth, thumbHeight;
    private BufferedImage currentThumbSprite, currentSliderSprite;
    private List<BufferedImage> thumbSprites = new ArrayList<>();
    private List<BufferedImage> sliderSprites = new ArrayList<>();

    public UISlider(Game game, int x, int y, int width, int height, int buttonId) {
        super(game, x, y, width, height);
        this.buttonId = buttonId;
        gameManager = game.getGameManager();
        mouseInput = game.getMouseInput();
        thumbWidth = 25;
        thumbHeight = 25;
        thumbX = x + width / 2 - thumbWidth / 2;
        thumbEndX = thumbX + thumbWidth - 1;
        thumbY = y - (thumbHeight - height) / 2;
        thumbEndY = thumbY + thumbHeight - 1;
        sliderSprites.add(Sprites.SLIDER_LINE);
        sliderSprites.add(Sprites.SLIDER_LINE);
        sliderSprites.add(Sprites.SLIDER_LINE);
        sliderSprites.add(Sprites.SLIDER_LINE);
        thumbSprites.add(Sprites.SLIDER_THUMB);
        thumbSprites.add(Sprites.SLIDER_THUMB_HOVER);
        thumbSprites.add(Sprites.SLIDER_THUMB_ACTIVE);
        thumbSprites.add(Sprites.SLIDER_THUMB_ACTIVE);
    }

    public void onHover() {
        changeState(HOVER);
    }

    public void onClick() {
        changeState(CLICK);
    }

    public void onDrag(int mouseX, int mouseY) {
        changeState(DRAG);
        if (mouseX >= x && mouseX <= endX) {
            thumbX = mouseX - thumbWidth / 2;
            thumbEndX = thumbX + thumbWidth - 1;
        }
        updateVolume();
    }

    public void updateVolume() {
        if (gameManager == null) {
            gameManager = game.getGameManager();
        }
        double percentage = (thumbX + thumbWidth / 2f) / (x + width);
        AudioPlayer bgmMusic = gameManager.getBgmMusic();
        FloatControl control = (FloatControl) bgmMusic.getClip().getControl(FloatControl.Type.MASTER_GAIN);
        control.setValue((float) (percentage * (control.getMaximum() - control.getMinimum()) + control.getMinimum()));
    }

    public void changeState(int uiState) {
        currentThumbSprite = thumbSprites.get(uiState);
        currentSliderSprite = sliderSprites.get(uiState);
    }

    @Override
    public void tick() {
        int mouseX = mouseInput.getMouseX();
        int mouseY = mouseInput.getMouseY();
        if (mouseX >= thumbX && mouseX <= thumbEndX && mouseY >= thumbY && mouseY <= thumbEndY) {
            // If thumb is pressed
            onHover();
            if (mouseInput.isLeftPressed()) {
                thumbActive = true;
            } else {
                thumbActive = false;
            }
        } else if (mouseX >= x && mouseX <= endX && mouseY >= y && mouseY <= endY) {
            thumbActive = true;
            if (mouseInput.isLeftPressed()) {
                onClick();
            }
        }
        if (thumbActive) {
            if (mouseInput.isDragging()) {
                onDrag(mouseX, mouseY);
            } else {
                thumbActive = false;
            }
        } else {
            changeState(IDLE);
            if (mouseX >= thumbX && mouseX <= thumbEndX && mouseY >= thumbY && mouseY <= thumbEndY) {
                // If thumb is pressed
                onHover();
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.drawImage(currentSliderSprite, x, y, width, height, null);
        graphics.drawImage(currentThumbSprite, thumbX, thumbY, thumbWidth, thumbHeight, null);
    }

}
