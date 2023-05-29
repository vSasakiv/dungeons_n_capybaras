package game_entity.mobs;

import game_entity.*;
import game_entity.weapons.Projectile;
import game_entity.weapons.Weapon;

import java.util.ArrayList;

public abstract class Enemy extends GameEntity {
    protected Weapon weapon;
    protected EnemyState state;
    protected Attributes attributes;

    protected Counter invincibilityCounter;
    public Hitbox hitbox;

    /**
     * Construtor da entidade, numa posição predeterminada
     *
     * @param worldPosX coordenada x inicial, em relação ao mundo
     * @param worldPosY coordenada y inicial, em relação ao mundo
     * @param velocity  velocidade
     */
    public Enemy(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY, velocity);
    }

    public abstract void tick(Vector playerPos);

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public abstract ArrayList<Projectile> updateShoot(Vector playerPos);

    public Weapon getWeapon() {
        return weapon;
    }

    public Attributes getAttributes() { return attributes; }

    public void gotHit(int damage) {
        if (invincibilityCounter.isZero()){
            this.attributes.takeDamage(damage);
            invincibilityCounter.start();
        }
    };
    public boolean isDead() { return this.attributes.isDead(); }
}
