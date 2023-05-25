package game_entity.weapons;

import game_entity.Vector;

import java.util.ArrayList;

/**
 * Classe para geração de armas automáticas, ou seja, armas que apenas atiram
 * um projétil por tiro.
 */
public class AutomaticWeapon extends Weapon{

    private final ProjectileFactory projectileFactory;

    public AutomaticWeapon(int fireRate, int damage, ProjectileFactory projectileFactory) {
        super(fireRate, damage);
        this.projectileFactory = projectileFactory;
    }

    /**
     * @param posX      Posição x de onde o tiro foi feito
     * @param posY      Posição y de onde o tiro foi feito
     * @param direction Direção do projétil
     * @return um ArrayList contendo apenas um projétil gerado pela arma
     */
    @Override
    public ArrayList<Projectile> shoot(int posX, int posY, Vector direction) {
        this.coolDown = 0;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        projectiles.add(projectileFactory.criaProjetil(posX+10, posY+10, direction));
        return projectiles;
    }

}
