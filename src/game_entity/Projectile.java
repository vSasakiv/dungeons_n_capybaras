package game_entity;

public class Projectile extends GameEntity{
    Vector direction;
    public Projectile(float posX, float posY, int velocity, Vector direction) {
        super(posX, posY, velocity);
        this.direction = direction;
    }

    @Override
    public void tick() {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
    }

    @Override
    public void tick(Vector direction){}
}
