package game_entity.entity_sprites.mobs;

import game_entity.entity_sprites.MovingEntitySprites;
import tile.AnimationSprite;

/**
 * Carrega sprites do mob imp
 */
public class ImpSprite extends MovingEntitySprites {
    @Override
    protected void loadSprites() {
        int width = 16;
        int height = 16;
        this.up = new AnimationSprite("/resources/mobs/imp/impWalk1.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/mobs/imp/impWalk1.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/mobs/imp/impWalk2.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/mobs/imp/impWalk2.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = new AnimationSprite("/resources/mobs/imp/impStand.png", width, height, 0, 0, 4);
    }
}
