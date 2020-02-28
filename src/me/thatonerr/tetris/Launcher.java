package me.thatonerr.tetris;

public class Launcher {

    public static void main(String[] args) {
        Game game = new Game("Tetris", 1500, 900);
        game.start();
    }

}
