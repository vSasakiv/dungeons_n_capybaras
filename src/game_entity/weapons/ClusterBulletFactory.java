package game_entity.weapons;

import game_entity.Vector;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class ClusterBulletFactory implements ProjectileFactory{
    int velocity;
    int timeUntilExplode;
    int numberProjectiles;
    ProjectileFactory subProjectileFactory;

    BufferedImage image;

    public ClusterBulletFactory(int velocity, int timeUntilExplode,
                                int numberProjectiles,
                                ProjectileFactory subProjectileFactory) {
        this.velocity = velocity;
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numberProjectiles;
        this.subProjectileFactory = subProjectileFactory;
        this.getImage();
    }

    @Override
    public Projectile criaProjetil(float posX, float posY, Vector direction) {
        return new ClusterBullet(
                posX,
                posY,
                this.velocity,
                direction,
                this.timeUntilExplode,
                this.numberProjectiles,
                this.subProjectileFactory,
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
