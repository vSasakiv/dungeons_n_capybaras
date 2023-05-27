package game_entity.weapons;

import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class ClusterBullet extends Projectile{
    int timeUntilExplode;
    int counter = 0;
    int numberProjectiles;
    ProjectileFactory subProjectileFactory;
    public ClusterBullet(float posX, float posY, int velocity, Vector direction,
                         int timeUntilExplode, int numeroProjeteis,
                         ProjectileFactory subProjectileFactory) {
        super(posX, posY, velocity, direction);
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numeroProjeteis;
        this.subProjectileFactory = subProjectileFactory;
    }

    @Override
    public void tick() {
        counter += 1;
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
    }

    @Override
    public void tick(Vector direction) {}

    @Override
    public boolean shouldDelete() {
        return  this.getWorldPosX() < 0 ||
                this.getWorldPosX() > Constants.WORLD_WIDTH ||
                this.getWorldPosY() < 0 ||
                this.getWorldPosY() > Constants.WORLD_HEIGHT ||
                counter > timeUntilExplode;
    }

    @Override
    public ArrayList<Projectile> subProjectiles() {
        int angle = 360 / numberProjectiles;
        ArrayList<Projectile> cluster = new ArrayList<>();
        for (int i = 0; i < numberProjectiles; i++)
            cluster.add(
                    subProjectileFactory.criaProjetil(
                            this.getWorldPosX(),
                            this.getWorldPosY(),
                            Vector.rotateVector(direction, angle*i)));
        return cluster;
    }
}
