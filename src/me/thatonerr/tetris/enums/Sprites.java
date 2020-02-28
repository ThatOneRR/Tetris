package me.thatonerr.tetris.enums;

import java.awt.image.BufferedImage;

import static me.thatonerr.tetris.utilities.Utils.getImage;

public class Sprites {

    // Tile Sprites
    public static final BufferedImage RED_TILE = getImage("/sprites/tiles/red_tile.jpg");
    public static final BufferedImage ORANGE_TILE = getImage("/sprites/tiles/orange_tile.jpg");
    public static final BufferedImage YELLOW_TILE = getImage("/sprites/tiles/yellow_tile.jpg");
    public static final BufferedImage GREEN_TILE = getImage("/sprites/tiles/green_tile.jpg");
    public static final BufferedImage AQUA_TILE = getImage("/sprites/tiles/aqua_tile.jpg");
    public static final BufferedImage BLUE_TILE = getImage("/sprites/tiles/blue_tile.jpg");
    public static final BufferedImage PURPLE_TILE = getImage("/sprites/tiles/purple_tile.jpg");
    public static final BufferedImage GREY_TILE = getImage("/sprites/tiles/grey_tile.jpg");
    public static final BufferedImage TRANSPARENT_RED_TILE = getImage("/sprites/tiles/transparent_red_tile.png");
    public static final BufferedImage TRANSPARENT_ORANGE_TILE = getImage("/sprites/tiles/transparent_orange_tile.png");
    public static final BufferedImage TRANSPARENT_YELLOW_TILE = getImage("/sprites/tiles/transparent_yellow_tile.png");
    public static final BufferedImage TRANSPARENT_GREEN_TILE = getImage("/sprites/tiles/transparent_green_tile.png");
    public static final BufferedImage TRANSPARENT_AQUA_TILE = getImage("/sprites/tiles/transparent_aqua_tile.png");
    public static final BufferedImage TRANSPARENT_BLUE_TILE = getImage("/sprites/tiles/transparent_blue_tile.png");
    public static final BufferedImage TRANSPARENT_PURPLE_TILE = getImage("/sprites/tiles/transparent_purple_tile.png");

    // UI Sprites
    public static final BufferedImage MAIN_BACKGROUND = getImage("/sprites/ui/main_menu.jpg");
    public static final BufferedImage BUTTON_BACKGROUND = getImage("/sprites/ui/button_background.jpg");
    public static final BufferedImage BUTTON_BACKGROUND_ACTIVE = getImage("/sprites/ui/button_background_active.jpg");
    public static final BufferedImage CONTROLS_BUTTON_BACKGROUND = getImage("/sprites/ui/controls_button_background.jpg");
    public static final BufferedImage CONTROLS_BUTTON_BACKGROUND_ACTIVE = getImage("/sprites/ui/controls_button_background_active.jpg");

    // UI Slider Sprites
    public static final BufferedImage SLIDER_LINE = getImage("/sprites/ui/slider_line.jpg");
    public static final BufferedImage SLIDER_THUMB = getImage("/sprites/ui/slider_thumb.png");
    public static final BufferedImage SLIDER_THUMB_HOVER = getImage("/sprites/ui/slider_thumb_hover.png");
    public static final BufferedImage SLIDER_THUMB_ACTIVE = getImage("/sprites/ui/slider_thumb_active.png");

    public static BufferedImage getTileSprite(int shapeID) {
        switch (shapeID) {
            case 0:
                return RED_TILE;
            case 1:
                return ORANGE_TILE;
            case 2:
                return YELLOW_TILE;
            case 3:
                return GREEN_TILE;
            case 4:
                return AQUA_TILE;
            case 5:
                return BLUE_TILE;
            case 6:
                return PURPLE_TILE;
            case 7:
                return GREY_TILE;
            default:
                return null;
        }
    }

    public static BufferedImage getTransparentTileSprite(int shapeID) {
        switch (shapeID) {
            case 0:
                return TRANSPARENT_RED_TILE;
            case 1:
                return TRANSPARENT_ORANGE_TILE;
            case 2:
                return TRANSPARENT_YELLOW_TILE;
            case 3:
                return TRANSPARENT_GREEN_TILE;
            case 4:
                return TRANSPARENT_AQUA_TILE;
            case 5:
                return TRANSPARENT_BLUE_TILE;
            case 6:
                return TRANSPARENT_PURPLE_TILE;
            default:
                return null;
        }
    }

}
