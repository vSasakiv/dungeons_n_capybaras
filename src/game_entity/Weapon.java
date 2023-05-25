package game_entity;

import java.util.ArrayList;

public abstract class Weapon {
    protected int fireRate, damage, speed;
    protected float coolDown = 0;
    private final int fixedCoolDown = 300;

    public Weapon(int fireRate, int damage, int speed) {
        this.fireRate = fireRate;
        this.damage = damage;
        this.speed = speed;
    }

    /**
     * A cada tick do jogo, também devemos atualizar o cool down de ataque da arma
     */
    public void tick(){
        if (this.coolDown < fixedCoolDown)
            this.coolDown += fireRate;
    }

    /**
     * @return retorna true caso a arma não esteja em cool down
     */
    public boolean canShoot(){
        return this.coolDown >= fixedCoolDown;
    }

    /**
     * @param posX Posição x de onde o tiro foi feito
     * @param posY Posição y de onde o tiro foi feito
     * @param direction Direção do projétil
     * @return um ArrayList contendo todos os projéteis gerados
     */
    abstract ArrayList<Projectile> shoot(int posX, int posY, Vector direction);
}
