package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Sprite {
    private BufferedImage SPRITESHEET;
    public int width;
    public int height;
    private int SpriteWidth;
    private int SpriteHeight;

    public Sprite(String file, int width, int height) {
        this.width = width;
        this.height = height;
        SPRITESHEET = loadSprite(file);
        SpriteWidth = SPRITESHEET.getWidth() / width;
        SpriteHeight = SPRITESHEET.getHeight() / height;
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

    public BufferedImage getSprite (int linha, int coluna) {
        return SPRITESHEET.getSubimage(coluna * width, linha * height , width, height);
    }

    public int getSpriteWidth() {
        return SpriteWidth;
    }
    public int getSpriteHeight() {
        return SpriteHeight;
    }
}
