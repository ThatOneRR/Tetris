package me.thatonerr.tetris.utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Utils {

    public static void log(String string) {
        System.out.println(string);
    }

    public static BufferedImage getImage(String path) {
        try {
            return ImageIO.read(Utils.class.getResource(path));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }

}
