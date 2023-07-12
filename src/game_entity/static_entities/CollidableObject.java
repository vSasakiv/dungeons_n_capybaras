package game_entity.static_entities;

import game_entity.*;

import java.awt.*;

/**
 * Classe abstrata para representar um objeto estático, porém colidível
 */
public abstract class CollidableObject extends GameObject {
    // Hitbox de um objeto colidível
    Hitbox hitbox;

    /**
     * @param worldPosX posição x do objeto
     * @param worldPosY posição y do objeto
     * @param hitbox hitbox do objeto
     */
    public CollidableObject(float worldPosX, float worldPosY, Hitbox hitbox) {
        super(worldPosX, worldPosY);
        this.hitbox = hitbox;
    }

    /**
     * @param worldPosX posição x do objeto
     * @param worldPosY posição y do objeto
     * @param width largura da hitbox do objeto
     * @param height altura da hitbox do objeto
     */
    public CollidableObject(float worldPosX, float worldPosY, int width, int height){
        super(worldPosX, worldPosY);
        this.hitbox = new Hitbox(width, height, this.position);
    }

    public void checkCollision(AttackingEntity entity, Hitbox hitbox){
        if (this.hitbox.isHitting(hitbox)){
            this.handleCollision(entity, hitbox);
        }
    }

    private void handleCollision(AttackingEntity entity, Hitbox hitbox){
        Vector previousPosition = Vector.add(
                hitbox.getPosition(),
                Vector.scalarMultiply(entity.getDirection(), -1 * entity.getVelocity()));
        Vector previousPositionX = Vector.add(
                previousPosition,
                new Vector(entity.getDirection().x * entity.getVelocity(), 0));
        Vector previousPositionY = Vector.add(
               previousPosition,
               new Vector(0, entity.getDirection().y * entity.getVelocity()));
        Hitbox previousX = new Hitbox(hitbox.getWidth(), hitbox.getHeight(), previousPositionX);
        Hitbox previousY = new Hitbox(hitbox.getWidth(), hitbox.getHeight(), previousPositionY);
        if (this.hitbox.isHitting(previousX)){
            entity.setPosition(Vector.add(
                    entity.getPosition(),
                    Vector.scalarMultiply(new Vector(entity.getDirection().x, 0),-1 * entity.getVelocity())));
        }
        if (this.hitbox.isHitting(previousY)){
            entity.setPosition(Vector.add(
                    entity.getPosition(),
                    Vector.scalarMultiply(new Vector(0, entity.getDirection().y),-1 * entity.getVelocity())));
        }
    }

    /**
     * @param g2d Graphics2D java
     * @param player Entidade a qual será desenhado relativo a
     */
    public abstract void draw(Graphics2D g2d, GameEntity player);
}
