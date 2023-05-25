package game_entity.weapons;

import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class Bullet extends Projectile{
    public Bullet(float posX, float posY, int velocity, Vector direction) {
        super(posX, posY, velocity, direction);
    }

    @Override
    public void tick() {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
    }
    @Override
    public void tick(Vector direction) {}

    @Override
    public boolean shouldDelete() {
        return  this.getWorldPosX() < 0 ||
                this.getWorldPosX() > Constants.WORLD_WIDTH ||
                this.getWorldPosY() < 0 ||
                this.getWorldPosY() > Constants.WORLD_HEIGHT;
    }

    @Override
    public ArrayList<Projectile> subProjectiles() {
        return new ArrayList<>();
    }
}
