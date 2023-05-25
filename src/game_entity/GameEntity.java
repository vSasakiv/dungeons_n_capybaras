package game_entity;

import gameloop.Constants;

/**
 * Classes que representa todas as entidades do jogo, isto é, todos os objetos com
 * um comportamento especial com o Player ou se movimentam.
 */
public abstract class GameEntity extends GameObject{
    protected int velocity;
    
    public GameEntity(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY);
        this.velocity = velocity;
    }
    public abstract void tick();
    public abstract void tick(Vector direction);

    protected void checkWindowBorder(){
        if (position.x < 0) position.x = 0;
        else if (position.x > Constants.WORLD_WIDTH) position.x = Constants.WORLD_WIDTH;
        if (position.y < 0) position.y = 0;
        else if (position.y > Constants.WORLD_HEIGHT) position.y = Constants.WORLD_HEIGHT;
    }
}
