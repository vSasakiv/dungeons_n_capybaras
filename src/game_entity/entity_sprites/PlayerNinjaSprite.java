package game_entity.entity_sprites;

import tile.AnimationSprite;

public class PlayerNinjaSprite extends MovingEntitySprites{
    @Override
    protected void loadSprites() {
        int width = 16;
        int height = 16;
        this.up = new AnimationSprite("/resources/player/ninjaUp.png", width, height, 0, 0, 4);
        this.upLeft = new AnimationSprite("/resources/player/ninjaUp.png", width, height, 0, 0, 4);
        this.upRight = new AnimationSprite("/resources/player/ninjaUp.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/player/ninjaDown.png", width, height, 0, 0, 4);
        this.downLeft = new AnimationSprite("/resources/player/ninjaDown.png", width, height, 0, 0, 4);
        this.downRight = new AnimationSprite("/resources/player/ninjaDown.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/player/ninjaRight.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/player/ninjaLeft.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = down.getSpriteArray()[0];
    }
}
