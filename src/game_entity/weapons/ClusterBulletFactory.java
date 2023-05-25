package game_entity.weapons;

import game_entity.Vector;

public class ClusterBulletFactory implements ProjectileFactory{
    int velocity;
    int timeUntilExplode;
    int numberProjectiles;
    ProjectileFactory subProjectileFactory;

    public ClusterBulletFactory(int velocity, int timeUntilExplode,
                                int numberProjectiles,
                                ProjectileFactory subProjectileFactory) {
        this.velocity = velocity;
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numberProjectiles;
        this.subProjectileFactory = subProjectileFactory;
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
                this.subProjectileFactory);
    }
}
