package me.thatonerr.tetris.components;

public class Form {

    private int[][] map;

    public Form(String mapString) {
        String[] rows = mapString.split(":");
        map = new int[rows.length][rows[0].split(" ").length];
        for (int y = 0; y < rows.length; y++) {
            String[] tiles = rows[y].trim().split(" ");
            for (int x = 0; x < tiles.length; x++) {
                try {
                    map[y][x] = Integer.parseInt(tiles[x]);
                } catch (NumberFormatException exception) {
                    exception.printStackTrace();
                }
            }
        }
    }

    public int[][] getMap() {
        return map;
    }

    public int getTileWidth() {
        return map[0].length;
    }

    public int getTileHeight() {
        return map.length;
    }

}
