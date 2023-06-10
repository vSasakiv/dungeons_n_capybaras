package game_entity;

/**
 * Classes que representa todas as entidades do jogo, isto é, todos os objetos com
 * um comportamento especial com o Player ou se movimentam.
 */
public abstract class GameEntity extends GameObject{
    protected int velocity;
    private int SpriteSizeX;
    private int SpriteSizeY;

    private Vector direction = null;

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

    public void setSpriteSizeX(int SpriteSizeX) { this.SpriteSizeX = SpriteSizeX; }

    public void setSpriteSizeY(int SpriteSizeY) { this.SpriteSizeY = SpriteSizeY; }

    public int getSpriteSizeX() { return SpriteSizeX; }

    public int getSpriteSizeY() { return SpriteSizeY; }

    public Vector getDirection() {
        return direction;
    }
    public void setDirection(Vector direction) {
        this.direction = direction;
    }

    public int getVelocity() {
        return velocity;
    }
}
