package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;
import gameloop.Constants;

import java.awt.*;

public class ConvictusNpc extends GameEntity implements MovableNpc{

    Hitbox hitbox;
    NpcStrategy strategy;
    public ConvictusNpc(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY, velocity);
        this.strategy = new PatrolStrategy(this, new Vector(0, 0));
        this.hitbox = new Hitbox(16, 16, this.position);
    }

    @Override
    public void tick(Vector playerPos) {
        this.setDirection(this.strategy.direction(this, playerPos));
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.getDirection(), velocity));
        this.hitbox.setPosition(this.position);
    }

    public void setStrategy(NpcStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public boolean isColliding(Hitbox hitbox) {
        return (hitbox.isHitting(this.hitbox));
    }

    @Override
    public void draw(Graphics2D g2d, GameEntity player) {
        g2d.fillOval(
                (int) (this.getWorldPosX() - player.getWorldPosX() + Constants.WIDTH / 2.0 - 5),
                (int) (this.getWorldPosY() - player.getWorldPosY() + Constants.HEIGHT / 2.0 - 5),
                10, 10);
        this.hitbox.draw(g2d, player);
    }
}
