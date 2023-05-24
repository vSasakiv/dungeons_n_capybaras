package game_entity;

import gameloop.Constants;

/**
 * Classes que representa todas as entidades do jogo, isto é, todos os objetos com
 * um comportamento especial com o Player ou se movimentam.
 */
public abstract class GameEntity extends GameObject{
    int velocity;

    public GameEntity(float posX, float posY, int velocity) {
        super(posX, posY);
        this.velocity = velocity;
    }
    public abstract void tick();
    public abstract void tick(Vector direction);

    protected void checkWindowBorder(){
        if (position.x < 0) position.x = 0;
        else if (position.x > Constants.WIDTH) position.x = Constants.WIDTH;
        if (position.y < 0) position.y = 0;
        else if (position.y > Constants.HEIGHT) position.y = Constants.HEIGHT;
    }
}
