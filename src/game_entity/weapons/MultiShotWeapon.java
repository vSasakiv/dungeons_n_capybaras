package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.weapons.projectiles.Projectile;
import game_entity.weapons.projectiles.ProjectileFactory;
import gameloop.Constants;
import gameloop.render.DrawWeapon;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Classe para implementar uma arma de tiro múltiplo, isto é, a cada ataque, podem ser gerados mais de 1 projétil
 */
public class MultiShotWeapon extends Weapon{
    private final ProjectileFactory projectileFactory; // fábrica de projéteis
    BufferedImage image; // sprite da arma
    int angulo; // ângulo a partir da direção de cada novo projétil
    int numeroProjeteis; // número de projéteis por ataque
    DrawWeapon drawMethod;
    String weaponSprite;

    /**
     *
     * @param fireRate velocidade de ataque da arma
     * @param damage dano de cada projétil da arma
     * @param projectileFactory Fábrica de projéteis
     * @param angulo angulo entre cada projétil
     * @param numeroProjeteis numero de projéteis por tiro
     * @param weaponSprite Tipo de sprite da arma
     */
    public MultiShotWeapon(int fireRate, int damage, ProjectileFactory projectileFactory, int angulo, int numeroProjeteis, String weaponSprite) {
        super(fireRate, damage);
        this.projectileFactory = projectileFactory;
        this.angulo = angulo;
        this.numeroProjeteis = numeroProjeteis;
        this.weaponSprite = weaponSprite;
        drawMethod = new DrawWeapon(this);
        this.image = WeaponSpriteProvider.getWeaponSprite(weaponSprite);
        this.setSpriteSizeX(image.getWidth() * Constants.SCALE);
        this.setSpriteSizeY(image.getHeight() * Constants.SCALE);
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

    @Override
    public Weapon clone() {
        return new MultiShotWeapon(
                this.coolDownCounter.getIncrement(),
                this.damage,
                this.projectileFactory,
                this.angulo,
                this.numeroProjeteis,
                this.weaponSprite
        );
    }

    /**
     * Método responsável por desenhar Shotgun na tela
     * @param g2d Ferramenta gráfica
     * @param entity Entidade que Shotgun está atrelada
     */
    @Override
    public void draw(Graphics2D g2d, GameEntity entity) {
        drawMethod.draw(g2d, entity);
    }

}
