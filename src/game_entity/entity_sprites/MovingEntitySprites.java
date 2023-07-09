package game_entity.entity_sprites;

import tile.AnimationSprite;

import java.awt.image.BufferedImage;

public abstract class MovingEntitySprites {
    public AnimationSprite up, down, right, left;
    public BufferedImage standFront, standBack;

    public MovingEntitySprites (){
        loadSprites();
    }
    abstract void loadSprites ();
}
