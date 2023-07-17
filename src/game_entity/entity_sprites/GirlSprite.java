package game_entity.entity_sprites;

import tile.AnimationSprite;

public class GirlSprite extends MovingEntitySprites{
    @Override
    protected void loadSprites() {
        int width = 16;
        int height = 16;
        this.up = new AnimationSprite("/resources/npcs/girl/girl_Up.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/npcs/girl/girl_Down.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/npcs/girl/girl_Right.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/npcs/girl/girl_Left.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = new AnimationSprite("/resources/npcs/girl/girl_Down.png", width, height, 0, 0, 1);
    }
}
