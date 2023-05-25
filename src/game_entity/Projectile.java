package game_entity;

import gameloop.Constants;

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

    /**
     * @return boolean indicando se o projétil está fora do mapa e deve deixar de ser atualizado
     */
    public boolean outOfBounds(){
        return  this.getWorldPosX() < 0 ||
                this.getWorldPosX() > Constants.WORLD_WIDTH ||
                this.getWorldPosY() < 0 ||
                this.getWorldPosY() > Constants.WORLD_HEIGHT;
    }
}
