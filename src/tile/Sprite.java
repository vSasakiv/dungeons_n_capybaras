package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Sprite {
    private BufferedImage Spritesheet;
    public int width;
    public int height;
    private int SpriteWidth;
    private int SpriteHeight;

    public Sprite(String file, int width, int height) {
        this.width = width;
        this.height = height;
        Spritesheet = loadSprite(file);
        SpriteWidth = Spritesheet.getWidth() / width;
        SpriteHeight = Spritesheet.getHeight() / height;
    }

    private BufferedImage loadSprite (String file) {
        BufferedImage sprite = null;
        try {
            sprite = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(file)));

        } catch (Exception e) {
            System.out.println("ERROR: could not load file: " + file);
            e.printStackTrace();
        }
        return sprite;
    }

    public BufferedImage getSprite (int row, int column) {
        return Spritesheet.getSubimage(column * width, row * height , width, height);
    }

    public int getSpriteWidth() {
        return SpriteWidth;
    }
    public int getSpriteHeight() {
        return SpriteHeight;
    }
}
