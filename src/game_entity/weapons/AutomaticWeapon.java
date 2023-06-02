package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Classe para geração de armas automáticas, ou seja, armas que apenas atiram
 * um projétil por tiro.
 */
public class AutomaticWeapon extends Weapon{
    BufferedImage image;
    private final ProjectileFactory projectileFactory;
    public AutomaticWeapon(int fireRate, int damage, ProjectileFactory projectileFactory) {
        super(fireRate, damage);
        this.projectileFactory = projectileFactory;
        getImage();
        this.setSpriteSizeX(13 * 3);
        this.setSpriteSizeY(5 * 3);
    }

    /**
     * @param posX      Posição x de onde o tiro foi feito
     * @param posY      Posição y de onde o tiro foi feito
     * @param direction Direção do projétil
     * @return um ArrayList contendo apenas um projétil gerado pela arma
     */
    @Override
    public AttackResults attack(int posX, int posY, Vector direction) {
        this.coolDownCounter.start();
        ArrayList<Projectile> projectiles = new ArrayList<>();
        projectiles.add(projectileFactory.criaProjetil(posX, posY, direction));
        return new AttackResults(projectiles, new ArrayList<>());
    }

    @Override
    public Weapon clone() {
        return new AutomaticWeapon(
                this.coolDownCounter.getIncrement(),
                this.damage,
                this.projectileFactory
        );
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
                entity.getScreenY() + (float) entity.getSpriteSizeY() / 2 + 16
        );
        g2d.rotate(-Math.toRadians(90) + Vector.getDegree(this.getDirection()));
        g2d.drawImage(
                this.image,
                -this.getSpriteSizeX() / 2,
                -this.getSpriteSizeY() / 2,
                this.getSpriteSizeX(),
                this.getSpriteSizeY(),
                null
        );
        g2d.setTransform(original);
    }

    /**
     * Método que carrega o sprite
     */
    private void getImage () {
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/weapons/bow/Bow.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
