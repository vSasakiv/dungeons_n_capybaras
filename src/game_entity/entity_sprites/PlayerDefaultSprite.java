package game_entity.entity_sprites;

import tile.AnimationSprite;

public class PlayerDefaultSprite extends MovingEntitySprites{
    @Override
    protected void loadSprites() {
        int width = 32;
        int height = 32;
        this.up = new AnimationSprite("/resources/player/Character_Up.png", width, height, 0, 0, 4);
        this.upLeft = new AnimationSprite("/resources/player/Character_UpLeft.png", width, height, 0, 0, 4);
        this.upRight = new AnimationSprite("/resources/player/Character_UpRight.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/player/Character_Down.png", width, height, 0, 0, 4);
        this.downLeft = new AnimationSprite("/resources/player/Character_DownLeft.png", width, height, 0, 0, 4);
        this.downRight = new AnimationSprite("/resources/player/Character_DownRight.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/player/Character_Right.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/player/Character_Left.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = new AnimationSprite("/resources/player/Character_Down.png", width, height, 0, 0, 4);
    }
}
