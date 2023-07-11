package game_entity.static_entities;

import game_entity.GameEntity;
import game_entity.GameObject;
import game_entity.Hitbox;

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

    /**
     * @param g2d Graphics2D java
     * @param player Entidade a qual será desenhado relativo a
     */
    public abstract void draw(Graphics2D g2d, GameEntity player);
}
