package me.thatonerr.tetris.managers;

import me.thatonerr.tetris.Game;
import me.thatonerr.tetris.components.UIControlButton;
import me.thatonerr.tetris.enums.ButtonIDs;
import me.thatonerr.tetris.enums.GameStates;

import java.util.ArrayList;
import java.util.List;


public class UIClickManager {

    private Game game;
    private UIControlButton activeControl;
    private List<UIControlButton> controlButtons = new ArrayList<>();
    private StateManager stateManager;

    public UIClickManager(Game game) {
        this.game = game;
        stateManager = game.getStateManager();
    }

    public void onButtonClick(int buttonId) {
        game.getMouseInput().setLeftPressed(false);
        switch (buttonId) {
            case ButtonIDs.RESUME:
                game.getGameManager().setPaused(false);
                stateManager.setCurrentState(GameStates.INGAME);
                game.getGameManager().getBgmMusic().resume();
                break;
            case ButtonIDs.PAUSE_RESTART:
                game.getGameManager().setPaused(false);
            case ButtonIDs.PLAY:
            case ButtonIDs.PLAY_AGAIN:
                game.getGameManager().startGame();
                break;
            case ButtonIDs.QUIT:
                game.stop();
                break;
            case ButtonIDs.PAUSE_SETTINGS_BACK:
                stateManager.setCurrentState(GameStates.PAUSED);
                break;
            case ButtonIDs.PAUSE_SETTINGS_CONTROL:
                stateManager.setCurrentState(GameStates.PAUSED_CONTROLS);
                break;
            case ButtonIDs.SETTINGS_BACK:
            case ButtonIDs.PAUSE_MAIN_MENU:
            case ButtonIDs.MAIN_MENU:
                stateManager.setCurrentState(GameStates.MAIN_MENU);
                break;
            case ButtonIDs.PAUSE_CONTROLS_BACK:
            case ButtonIDs.PAUSE_SETTINGS:
                stateManager.setCurrentState(GameStates.PAUSED_SETTINGS);
                break;
            case ButtonIDs.SETTINGS:
            case ButtonIDs.CONTROLS_BACK:
                stateManager.setCurrentState(GameStates.SETTINGS);
                break;
            case ButtonIDs.CONTROLS:
                stateManager.setCurrentState(GameStates.SETTINGS_CONTROLS);
                game.getMouseInput().setLeftPressed(false);
                break;
        }
    }

    public UIControlButton getActiveControl() {
        return activeControl;
    }

    public void setActiveControl(UIControlButton activeControl) {
        this.activeControl = activeControl;
    }

    public List<UIControlButton> getControlButtons() {
        return controlButtons;
    }

    public void addControlButton(UIControlButton uiControlButton) {
        controlButtons.add(uiControlButton);
    }

}
