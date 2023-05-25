package game_entity.weapons;

import game_entity.Vector;

public class BulletFactory implements ProjectileFactory {
    int velocity;

    public BulletFactory(int velocity) {
        this.velocity = velocity;
    }

    @Override
    public Projectile criaProjetil(float posX, float posY, Vector direction) {
        return new Bullet(posX, posY, this.velocity, direction);
    }
}
