package game_entity;

import tile.AnimationSprite;

import java.awt.image.BufferedImage;

public abstract class PlayerSprite {
    public AnimationSprite up, upLeft, upRight, down, downLeft, downRight, right, left;
    public BufferedImage standFront, standBack;

    public PlayerSprite (){
        loadSprites();
    }
    abstract void loadSprites ();

}
