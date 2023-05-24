package game_entity;

import gameloop.Constants;

public abstract class GameEntity extends GameObject{

    int velocity;
    public GameEntity(int posX, int posY, int velocity) {
        super(posX, posY);
        this.velocity = velocity;
    }
    public abstract void tick(Vector direction);

    protected void checkWindowBorder(int width, int height){
        if (position.x < 0) position.x = 0;
        else if (position.x > Constants.WIDTH) position.x = Constants.WIDTH;
        if (position.y < 0) position.y = 0;
        else if (position.y > Constants.WIDTH) position.y = Constants.WIDTH;
    }
}
