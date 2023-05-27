package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ClusterBullet extends Projectile{
    BufferedImage image;
    int timeUntilExplode;
    int counter = 0;
    int numberProjectiles;
    ProjectileFactory subProjectileFactory;

    public ClusterBullet (float posX, float posY, int velocity, Vector direction,
                         int timeUntilExplode, int numeroProjeteis,
                         ProjectileFactory subProjectileFactory,
                         BufferedImage image) {
        super(posX, posY, velocity, direction);
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numeroProjeteis;
        this.subProjectileFactory = subProjectileFactory;
        this.image = image;
        this.setSpriteSizeX(13 * 3);
        this.setSpriteSizeY(5 * 3);
    }

    @Override
    public void tick() {
        counter += 1;
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
    }

    @Override
    public void tick(Vector direction) {}

    /**
     * Método responsável por desenhar ClusterBullet na tela
     * @param g2d Ferramenta gráfica
     * @param entity  Entidade que ClusterBullet está atrelada
     */
    @Override
    public void draw (Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate (
                this.getWorldPosX() - entity.getWorldPosX() + entity.getScreenX() + (double) entity.getSpriteSizeX() / 2,
                16 + this.getWorldPosY() - entity.getWorldPosY() + entity.getScreenY() + (double) entity.getSpriteSizeX() / 2
        );
        g2d.rotate(Vector.getDegree(this.direction));
        g2d.drawImage (
                this.image,
                -this.getSpriteSizeX() / 2,
                -this.getSpriteSizeY() / 2,
                this.getSpriteSizeX(),
                this.getSpriteSizeY(),
                null
        );
        g2d.setTransform(original);
    }

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
