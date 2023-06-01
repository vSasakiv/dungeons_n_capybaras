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


public class MultiShotWeapon extends Weapon{
    private final ProjectileFactory projectileFactory;
    BufferedImage image;
    int angulo, numeroProjeteis;

    /**
     * @param fireRate velocidade de ataque da arma
     * @param damage dano de cada projétil da arma
     * @param projectileFactory Fábrica de projéteis
     * @param angulo angulo entre cada projétil
     * @param numeroProjeteis numero de projéteis por tiro
     */
    public MultiShotWeapon(int fireRate, int damage, ProjectileFactory projectileFactory, int angulo, int numeroProjeteis) {
        super(fireRate, damage);
        this.projectileFactory = projectileFactory;
        this.angulo = angulo;
        this.numeroProjeteis = numeroProjeteis;
        getImage();
        this.setSpriteSizeX(13 * 3);
        this.setSpriteSizeY(5 * 3);
    }

    /**
     * @param posX      Posição x de onde o tiro foi feito
     * @param posY      Posição y de onde o tiro foi feito
     * @param direction Direção do projétil
     * @return ArrayList de tamanho numeroProjeteis contendo todos os projéteis gerados.
     */
    @Override
    public AttackResults attack(int posX, int posY, Vector direction) {
        int metade = numeroProjeteis/2;
        this.coolDownCounter.start();
        ArrayList<Projectile> projectiles = new ArrayList<>();
        for (int i = 0; i < numeroProjeteis; i++)
            projectiles.add(projectileFactory.criaProjetil(
                    posX,
                    posY,
                    Vector.rotateVector(direction, (metade - i) * angulo)
            ));
        return new AttackResults(projectiles, new ArrayList<>());
    }

    /**
     * Método responsável por desenhar Shotgun na tela
     * @param g2d Ferramenta gráfica
     * @param entity Entidade que Shotgun está atrelada
     */
    @Override
    public void draw(Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate(
                entity.getScreenX() + (float) entity.getSpriteSizeX() / 2,
                entity.getScreenY() + (float) entity.getSpriteSizeY() / 2 + 16
        );
        g2d.rotate(-Math.toRadians(90) + Vector.getDegree(this.getDirection()) );
        g2d.drawImage(
                this.image,
                - this.getSpriteSizeX() / 2,
                - this.getSpriteSizeY() / 2,
                this.getSpriteSizeX(),
                this.getSpriteSizeY(),
                null);
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
