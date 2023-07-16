package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.weapons.projectiles.Projectile;
import game_entity.weapons.projectiles.ProjectileFactory;
import gameloop.Constants;
import gameloop.render.DrawWeapon;
import java.awt.*;
import java.util.ArrayList;

/**
 * Classe para geração de armas automáticas, ou seja, armas que atiram
 * um projétil por tiro.
 */
public class AutomaticWeapon extends Weapon{
    private final ProjectileFactory projectileFactory; // fábrica de projéteis para gerar novos projéteis a cada ataque
    DrawWeapon drawMethod;
    String weaponSprite;

    /**
     * @param fireRate taxa de disparo da arma (quanto maior mais rápida é a arma)
     * @param damage dano infligido por cada projétil
     * @param projectileFactory fábrica a partir da qual os projéteis serão gerados
     */
    public AutomaticWeapon(int fireRate, int damage, ProjectileFactory projectileFactory, String weaponSprite) {
        super(fireRate, damage);
        this.projectileFactory = projectileFactory;
        this.weaponSprite = weaponSprite;
        drawMethod = new DrawWeapon(this);
        this.image = WeaponSpriteProvider.getWeaponSprite(weaponSprite);
        this.setSpriteSizeX(image.getWidth() * (Constants.SCALE - 1));
        this.setSpriteSizeY(image.getHeight() * (Constants.SCALE - 1));

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
                this.projectileFactory,
                this.weaponSprite

        );
    }


    /**
     * Método responsável por desenhar AutomaticWeapon na tela
     * @param g2d Ferramenta gráfica
     * @param entity Entidade que AutomaticWeapon está atrelada
     */
    public void draw (Graphics2D g2d, GameEntity entity) {
        drawMethod.draw(g2d, entity);
    }

}
