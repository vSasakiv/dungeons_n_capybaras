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

public class Bullet extends Projectile{
    BufferedImage image;
    public Bullet(float posX, float posY, int velocity, Vector direction) {
        super(posX, posY, velocity, direction);
        this.getImage();
    }

    @Override
    public void tick() {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
    }
    @Override
    public void tick(Vector direction) {}

    @Override
    public boolean shouldDelete() {
        return  this.getWorldPosX() < 0 ||
                this.getWorldPosX() > Constants.WORLD_WIDTH ||
                this.getWorldPosY() < 0 ||
                this.getWorldPosY() > Constants.WORLD_HEIGHT;
    }

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
    public ArrayList<Projectile> subProjectiles() {
        return new ArrayList<>();
    }
}
