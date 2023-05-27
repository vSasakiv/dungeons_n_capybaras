package game_entity.weapons;

import game_entity.Vector;

import java.util.ArrayList;

public abstract class Weapon {
    protected int fireRate;
    protected int damage;
    protected float coolDown = 0;
    private final int fixedCoolDown = 300;
    /**
     * @param fireRate velocidade de ataque da arma
     * @param damage dano de cada projétil da arma
     */
    public Weapon(int fireRate, int damage) {
        this.fireRate = fireRate;
        this.damage = damage;
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
    public abstract ArrayList<Projectile> shoot(int posX, int posY, Vector direction);
}