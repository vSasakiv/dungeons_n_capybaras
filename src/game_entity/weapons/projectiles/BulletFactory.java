package game_entity.weapons.projectiles;

import game_entity.Vector;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class BulletFactory implements ProjectileFactory {
    int velocity;
    float hitboxRadius;
    BufferedImage image;

    public BulletFactory(int velocity, float hitboxRadius) {
        this.velocity = velocity;
        this.hitboxRadius = hitboxRadius;
        this.getImage();
    }

    @Override
    public Projectile criaProjetil(float posX, float posY, Vector direction) {
        return new Bullet(
                posX,
                posY,
                this.velocity,
                direction,
                new ProjectileHitbox(new Vector(posX, posY), this.hitboxRadius),
                this.image);
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
