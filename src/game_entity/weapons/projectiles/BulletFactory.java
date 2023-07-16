package game_entity.weapons.projectiles;

import game_entity.Vector;
import java.awt.image.BufferedImage;

/**
 * Classe que implementa uma fábrica abstrata de projéteis, utilizada para criar objetos da classe
 * Projectile, em especial, Bullet.
 */
public class BulletFactory implements ProjectileFactory {
    int velocity; // velocidade dos projéteis criados
    float hitboxRadius; // raio da hitbox dos projéteis
    BufferedImage image; // sprite de cada projétil

    /**
     * @param velocity velocidade de cada projétil
     * @param hitboxRadius raio da hitbox de cada projétil
     */
    public BulletFactory(int velocity, float hitboxRadius, String projectileType) {
        this.velocity = velocity;
        this.hitboxRadius = hitboxRadius;
        this.image = ProjectileSpriteProvider.getProjectileSprite(projectileType);
    }

    /**
     * @param posX posição x da qual a bala foi gerada
     * @param posY posição y da qual a bola foi gerada
     * @param direction direção com que a bala deve seguir
     * @return um novo objeto projétil
     */
    @Override
    public Projectile criaProjetil(float posX, float posY, Vector direction) {
        return new Bullet(
                posX,
                posY,
                this.velocity,
                direction,
                new ProjectileHitbox(new Vector(posX, posY), this.hitboxRadius),
                this.image
        );
    }


}
