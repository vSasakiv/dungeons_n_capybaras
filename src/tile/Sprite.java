package tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class Sprite {
    private final BufferedImage Spritesheet;
    public int width;
    public int height;
    private final int SpriteWidth;
    private final int SpriteHeight;

    /**
     * Carrega um Spritesheet
     * @param file Caminho para o arquivo
     * @param width Comprimento dos sprites
     * @param height Largura dos sprites
     */
    public Sprite(String file, int width, int height) {
        this.width = width;
        this.height = height;
        Spritesheet = loadSprite(file);
        SpriteWidth = Spritesheet.getWidth() / width;
        SpriteHeight = Spritesheet.getHeight() / height;
    }

    /**
     * Método responsável por carregar a imagem do Spritesheet
     * @param file caminho para o arquivo
     * @return imagem do Spritesheet
     */
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

    /**
     * Método utilizado para se obter um sprite específico do Spritesheet
     * @param row linha do sprite
     * @param column coluna do sprite
     * @return imagem do sprite
     */
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
