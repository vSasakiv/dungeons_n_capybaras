package game_entity.entity_sprites.mobs;

import game_entity.entity_sprites.MovingEntitySprites;
import tile.AnimationSprite;

public class FlySprite extends MovingEntitySprites {
    @Override
    protected void loadSprites() {
        int width = 32;
        int height = 32;
        this.up = new AnimationSprite("/resources/mobs/fly/flyWalk1.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/mobs/fly/flyWalk1.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/mobs/fly/flyWalk2.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/mobs/fly/flyWalk2.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = new AnimationSprite("/resources/mobs/fly/flyStand.png", width, height, 0, 0, 4);
    }
}
