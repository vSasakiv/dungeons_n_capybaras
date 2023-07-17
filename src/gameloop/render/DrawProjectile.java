package gameloop.render;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.weapons.projectiles.Projectile;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Desenha um projétil na tela
 */
public class DrawProjectile{

    Projectile projectile;

    public DrawProjectile(Projectile projectile) {
        this.projectile = projectile;
    }

    /**
     * Desenha um projétil na tela com base na posição de uma entidade
     * @param g2d Ferramenta para desenho
     * @param entity entidade referência
     */
    public void draw (Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        //Movimenta o centro da tela para posição do projétil
        g2d.translate(
                projectile.getWorldPosX() - entity.getWorldPosX() + entity.getScreenX() + (double) entity.getSpriteSizeX() / 2,
                projectile.getWorldPosY() - entity.getWorldPosY() + entity.getScreenY() + (double) entity.getSpriteSizeY() / 2
        );
        //Rotaciona a tela na direção do projétil
        g2d.rotate(Vector.getDegree(projectile.getDirection()));
        //Desenha o projetil
        g2d.drawImage(
                projectile.getImage(),
                -projectile.getSpriteSizeX() / 2,
                -projectile.getSpriteSizeY() / 2,
                projectile.getSpriteSizeX(),
                projectile.getSpriteSizeY(),
                null
        );
        //Volta a tela para a configuração inicial
        g2d.setTransform(original);
    }
}
