package game_entity;
import gameloop.Constants;
public class Player extends GameEntity{
    public Player(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
    }

    @Override
    public void tick(Vector direction) {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
        this.checkWindowBorder();
    }
}
