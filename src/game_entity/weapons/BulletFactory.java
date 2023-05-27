package game_entity.weapons;

import game_entity.Vector;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BulletFactory implements ProjectileFactory {
    int velocity;
    BufferedImage image;

    public BulletFactory(int velocity) {
        this.velocity = velocity;
        this.getImage();
    }

    @Override
    public Projectile criaProjetil(float posX, float posY, Vector direction) {
        return new Bullet(posX, posY, this.velocity, direction, this.image);
    }

    /**
     * Método responsável por carregar sprites
     */
    private void getImage () {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/weapons/bow/Arrow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
