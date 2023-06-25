package game_entity.static_entities;

import game_entity.GameEntity;
import game_entity.GameObject;
import game_entity.Hitbox;

import java.awt.*;

public abstract class CollidableObject extends GameObject {
    Hitbox hitbox;

    public CollidableObject(float worldPosX, float worldPosY, Hitbox hitbox) {
        super(worldPosX, worldPosY);
        this.hitbox = hitbox;
    }
    public CollidableObject(float worldPosX, float worldPosY, int width, int height){
        super(worldPosX, worldPosY);
        this.hitbox = new Hitbox(width, height, this.position);
    }
    public abstract void draw(Graphics2D g2d, GameEntity player);
}
