package game_entity;

import java.util.ArrayList;

/**
 * Classe para geração de armas automáticas, ou seja, armas que apenas atiram
 * um projétil por tiro.
 */
public class AutomaticWeapon extends Weapon{
    public AutomaticWeapon(int fireRate, int damage, int speed) {
        super(fireRate, damage, speed);
    }

    /**
     * @param posX      Posição x de onde o tiro foi feito
     * @param posY      Posição y de onde o tiro foi feito
     * @param direction Direção do projétil
     * @return um ArrayList contendo apenas um projétil gerado pela arma
     */
    @Override
    ArrayList<Projectile> shoot(int posX, int posY, Vector direction) {
        this.coolDown = 0;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        projectiles.add(new Projectile(posX, posY, this.speed, direction));
        return projectiles;
    }
}
