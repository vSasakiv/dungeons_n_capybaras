package game_entity.weapons.projectiles;

import game_entity.GameEntity;
import game_entity.Vector;

import java.awt.*;
import java.util.ArrayList;

public abstract class Projectile extends GameEntity {
    Vector direction;
    ProjectileHitbox hitbox;
    boolean collided = false;
    public Projectile(float posX, float posY, int velocity, Vector direction, ProjectileHitbox hitbox) {
        super(posX, posY, velocity);
        this.direction = direction;
        this.hitbox = hitbox;
    }

    public abstract void tick();
    public abstract void draw(Graphics2D g2d, GameEntity entity);

    /**
     * @return boolean indicando se o projétil está fora do mapa e deve deixar de ser atualizado
     */
    public abstract boolean shouldDelete();

    public abstract ArrayList<Projectile> subProjectiles();

    public ProjectileHitbox getHitbox() {
        return hitbox;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }
}
