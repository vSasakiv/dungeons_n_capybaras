package game_entity.weapons.projectiles;

import game_entity.GameEntity;
import game_entity.Vector;

import java.awt.*;
import java.util.ArrayList;

/**
 * Classe abstrata para representar um projétil
 */
public abstract class Projectile extends GameEntity {
    Vector direction; // direção que o projétil deve seguir
    ProjectileHitbox hitbox; // hitbox do projétil
    boolean collided = false; // caso colida com algo, deve ser true

    /**
     * @param posX posição x de geração do projétil
     * @param posY posição y de geração do projétil
     * @param velocity velocidade do projétil
     * @param direction direção na qual o projétil deve seguir inicialmente
     * @param hitbox hitbox do projétil
     */
    public Projectile(float posX, float posY, int velocity, Vector direction, ProjectileHitbox hitbox) {
        super(posX, posY, velocity);
        this.direction = direction;
        this.hitbox = hitbox;
    }

    /**
     * Método para atualizar o projétil
     */
    public abstract void tick();

    /**
     * @param g2d Graphics2D do java
     * @param entity entidade relativa a partir da qual desenharemos o projétil
     */
    public abstract void draw(Graphics2D g2d, GameEntity entity);

    /**
     * @return boolean indicando se o projétil está fora do mapa e deve deixar de ser atualizado
     */
    public abstract boolean shouldDelete();

    /**
     * @return ArrayList de todos os projéteis gerados por um projétil
     */
    public abstract ArrayList<Projectile> subProjectiles();

    /**
     * @return hitbox do projétil
     */
    public ProjectileHitbox getHitbox() {
        return hitbox;
    }

    /**
     * @param collided novo boolean para o valor de collided
     */
    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
