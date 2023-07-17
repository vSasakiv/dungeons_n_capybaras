package game_entity.static_entities;

import game_entity.GameEntity;

import java.awt.*;

/**
 * Tiles de colisão dos mapas
 */
public class CollidableTile extends CollidableObject {
    public CollidableTile(float worldPosX, float worldPosY, int width, int height) {
        super(worldPosX, worldPosY, width, height);
    }

    @Override
    public void draw(Graphics2D g2d, GameEntity player) {
        this.hitbox.draw(g2d, player);
    }
}
