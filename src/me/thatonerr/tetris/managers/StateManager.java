package me.thatonerr.tetris.managers;

import me.thatonerr.tetris.Game;

import static me.thatonerr.tetris.enums.GameStates.*;

public class StateManager {

    private Game game;
    private int currentState;

    public StateManager(Game game) {
        this.game = game;
        currentState = MAIN_MENU;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

}
