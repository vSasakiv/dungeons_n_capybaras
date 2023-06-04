package tile;

import game_entity.Hitbox;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Cria um tile, o qual tem como atributo uma imagem
 */
public class Tile {
    private BufferedImage image;
    private Hitbox hitbox;

    public Tile (BufferedImage image) {
        this.image = image;
    }
    public BufferedImage getImage() {
        return image;
    }

    public void setHitbox (Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }


}
