package game_entity.entity_sprites;

import tile.AnimationSprite;

import java.awt.image.BufferedImage;

public abstract class MovingEntitySprites {
    public AnimationSprite up, down, right, left, upLeft, upRight, downLeft, downRight, standFront;
    public BufferedImage standBack;

    public MovingEntitySprites (){
        loadSprites();
    }
    protected abstract void loadSprites();
}
