package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;

import java.awt.*;

public interface MovableNpc {
    void tick(Vector playerPos);
    void draw(Graphics2D g2d, GameEntity player);
    void setStrategy(NpcStrategy strategy);

    boolean isColliding(Hitbox hitbox);
}
