package gameloop.render;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.weapons.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawProjectile{

    Projectile projectile;

    public DrawProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    public void draw (Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate(
                projectile.getWorldPosX() - entity.getWorldPosX() + entity.getScreenX() + (double) entity.getSpriteSizeX() / 2,
                projectile.getWorldPosY() - entity.getWorldPosY() + entity.getScreenY() + (double) entity.getSpriteSizeY() / 2
        );
        g2d.rotate(Vector.getDegree(projectile.getDirection()));
        g2d.drawImage(
                projectile.getImage(),
                -projectile.getSpriteSizeX() / 2,
                -projectile.getSpriteSizeY() / 2,
                projectile.getSpriteSizeX(),
                projectile.getSpriteSizeY(),
                null
        );
        g2d.setTransform(original);
    }
}
