package gameloop.render;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.weapons.Weapon;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class DrawWeapon {
    Weapon weapon;
    public DrawWeapon (Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * Método responsável por desenhar AutomaticWeapon na tela
     * @param g2d Ferramenta gráfica
     * @param entity Entidade que AutomaticWeapon está atrelada
     */
    public void draw (Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate(
                entity.getScreenX() + (float) entity.getSpriteSizeX() / 2,
                entity.getScreenY() + (float) entity.getSpriteSizeY() * 2 / 3
        );
        g2d.rotate(-Math.toRadians(90) + Vector.getDegree(weapon.getDirection()));
        g2d.drawImage(
                weapon.getImage(),
                -weapon.getSpriteSizeX() / 2,
                -weapon.getSpriteSizeY() / 2,
                weapon.getSpriteSizeX(),
                weapon.getSpriteSizeY(),
                null
        );
        g2d.setTransform(original);
    }
}
