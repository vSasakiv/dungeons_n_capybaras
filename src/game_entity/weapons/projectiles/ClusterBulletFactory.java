package src.game_entity.weapons.projectiles;

import src.game_entity.Vector;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

/**
 * Classe que implementa uma fábrica abstrata de projéteis, utilizada para criar objetos da classe
 * Projectile, em especial, ClusterBullet.
 */
public class ClusterBulletFactory implements ProjectileFactory{
    int velocity; // velocidade dos projéteis
    int timeUntilExplode; // tempo até a divisão
    int numberProjectiles; // número de sub-projéteis criados
    ProjectileFactory subProjectileFactory; // fábrica responsável pela geração dos sub-projéteis
    float hitboxRadius; // raio da hitbox do projétil
    BufferedImage image; // sprite do projétil

    /**
     * @param velocity velocidade do projétil
     * @param timeUntilExplode tempo até a divisão
     * @param numberProjectiles número de sub-projéteis criados
     * @param hitboxRadius raio da hitbox do projétil
     * @param subProjectileFactory fábrica responsável pela geração dos sub-projéteis
     */
    public ClusterBulletFactory(int velocity, int timeUntilExplode,
                                int numberProjectiles, float hitboxRadius,
                                ProjectileFactory subProjectileFactory) {
        this.velocity = velocity;
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numberProjectiles;
        this.hitboxRadius = hitboxRadius;
        this.subProjectileFactory = subProjectileFactory;
        this.getImage();
    }

    /**
     * @param posX posição x da qual o projétil foi gerado
     * @param posY posição y da qual o projétil foi gerado
     * @param direction direção na qual o projétil deve seguir
     * @return objeto do tipo Projectile
     */
    @Override
    public Projectile criaProjetil(float posX, float posY, Vector direction) {
        return new ClusterBullet(
                posX,
                posY,
                this.velocity,
                direction,
                new ProjectileHitbox(new Vector(posX, posY), this.hitboxRadius),
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
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/src/resources/weapons/bow/Arrow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
