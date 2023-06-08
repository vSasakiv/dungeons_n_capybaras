package game_entity;

import gameloop.Constants;

/**
 * Classes que representa todas as entidades do jogo, isto é, todos os objetos com
 * um comportamento especial com o Player ou se movimentam.
 */
public abstract class GameEntity extends GameObject{
    protected int velocity;
    private int SpriteSizeX;
    private int SpriteSizeY;
    
    /**
     * Construtor da entidade, numa posição predeterminada
     * @param worldPosX coordenada x inicial, em relação ao mundo
     * @param worldPosY coordenada y inicial, em relação ao mundo
     * @param velocity velocidade
     */
    public GameEntity(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY);
        this.velocity = velocity;
    }

    /**
     * Impede que entidade ultrapasse borda do mundo
     */
    protected void checkWindowBorder(){
        if (position.x < 0) position.x = 0;
        else if (position.x > Constants.WORLD_WIDTH) position.x = Constants.WORLD_WIDTH;
        if (position.y < 0) position.y = 0;
        else if (position.y > Constants.WORLD_HEIGHT) position.y = Constants.WORLD_HEIGHT;
    }

    public void setSpriteSizeX(int SpriteSizeX) { this.SpriteSizeX = SpriteSizeX; }

    public void setSpriteSizeY(int SpriteSizeY) { this.SpriteSizeY = SpriteSizeY; }

    public int getSpriteSizeX() { return SpriteSizeX; }

    public int getSpriteSizeY() { return SpriteSizeY; }
}
