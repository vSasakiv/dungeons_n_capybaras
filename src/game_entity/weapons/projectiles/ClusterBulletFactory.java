package game_entity.weapons.projectiles;

import game_entity.Vector;
import java.awt.image.BufferedImage;

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
    String projectileType;

    /**
     * @param velocity velocidade do projétil
     * @param timeUntilExplode tempo até a divisão
     * @param numberProjectiles número de sub-projéteis criados
     * @param hitboxRadius raio da hitbox do projétil
     * @param subProjectileFactory fábrica responsável pela geração dos sub-projéteis
     */
    public ClusterBulletFactory(int velocity, int timeUntilExplode,
                                int numberProjectiles, float hitboxRadius,
                                ProjectileFactory subProjectileFactory,
                                String projectileType) {
        this.velocity = velocity;
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numberProjectiles;
        this.hitboxRadius = hitboxRadius;
        this.subProjectileFactory = subProjectileFactory;
        this.projectileType = projectileType;
        this.image = ProjectileSpriteProvider.getProjectileSprite(projectileType);
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
                this.image ,
                this.projectileType);
    }

}
