package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ClusterBullet extends Projectile{
    BufferedImage image;
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
        getImage();
    }

    @Override
    public void tick() {
        counter += 1;
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
    }

    @Override
    public void tick(Vector direction) {}

    @Override
    public void draw (Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate(this.getWorldPosX() - entity.getWorldPosX() + Constants.WIDTH/2.0 , this.getWorldPosY() - entity.getWorldPosY()+ Constants.HEIGHT/2.0);
        g2d.rotate(Vector.getDegree(this.direction));
        g2d.drawImage(image, 0, 0,  3* 13,   3 * 5, null);
        /* g2d.setColor(Color.RED);
        g2d.fillOval(0,  0, 8, 8);*/
        g2d.setTransform(original);
    }

    private void getImage () {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/weapons/bow/Arrow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }

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
