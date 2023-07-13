package game_entity.entity_sprites.mobs;

import game_entity.entity_sprites.MovingEntitySprites;
import tile.AnimationSprite;

public class TwigSprite extends MovingEntitySprites {
    @Override
    protected void loadSprites() {
        int width = 16;
        int height = 16;
        this.up = new AnimationSprite("/resources/mobs/twig/twigWalk2.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/mobs/twig/twigWalk2.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/mobs/twig/twigWalk1.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/mobs/twig/twigWalk1.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = new AnimationSprite("/resources/mobs/twig/twigStand.png", width, height, 0, 0, 4);
    }
}
