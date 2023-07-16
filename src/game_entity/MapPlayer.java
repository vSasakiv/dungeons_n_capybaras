package game_entity;

import game_entity.entity_sprites.MovingEntitySprites;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.sound.PlayerSound;
import gameloop.render.DrawMovingEntity;
import gameloop.render.DrawPlayer;
import java.awt.*;
import java.util.ArrayList;

public class MapPlayer extends GameEntity{
    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;

    private MapPlayerStateEnum currentState;
    private final Hitbox hitbox;
    protected ArrayList<MovingEntitySprites> playerSprites;
    DrawMovingEntity drawMethod;
    private final PlayerSound playerSound;

    /**
     * Construtor do player, que o inicializa numa posição pré-determinada
     * @param posX coordenada x de nascimento
     * @param posY coordenada y de nascimento
     * @param velocity velocidade
     */
    public MapPlayer(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
        this.setSpriteSizeX(Constants.TILE_SIZE * 2);
        this.setSpriteSizeY(Constants.TILE_SIZE * 2);
        this.setScreenX(SCREEN_X - (float) this.getSpriteSizeX() / 2);
        this.setScreenY(SCREEN_Y - (float) this.getSpriteSizeY() / 2);
        this.currentState = MapPlayerStateEnum.DEFAULT;
        this.hitbox = new Hitbox((float) Constants.TILE_SIZE * 2 / 3, (float) Constants.TILE_SIZE * 2 / 3, new Vector(this.getWorldPosX(), this.getWorldPosY()));
        this.loadSprites();
        drawMethod = new DrawPlayer(this, playerSprites);
        playerSound = new PlayerSound();
    }

    /**
     * Carrega os sprites usados pelo player
     */
    protected void loadSprites() {
        playerSprites = new ArrayList<>();
        playerSprites.add(PlayerSpriteFactory.create("default"));
        playerSprites.add(PlayerSpriteFactory.create("ninja"));
    }

    public void tick(KeyHandler keyHandler) {
        updateState(keyHandler);
        this.setDirection(DirectionUpdater.updateDirection(keyHandler, drawMethod));
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.getDirection(), velocity));
        this.hitbox.setPosition(this.position);
        if (drawMethod.getSpriteCounter() == 0 && getDirection() != Constants.NULL_VECTOR)
            playSound(0, 0.1F);

    }

    /**
     * Toca efeitos sonoros
     * @param index índice do efeito sonoro na lista
     * @param volume volume
     */
    public void playSound (int index, float volume) {
        playerSound.setSoundFile(index);
        playerSound.setVolume(volume, "SOUND");
        playerSound.playSound();
    }


    /**
     * Desenha um sprite na tela (frame aberto) conforme a direção indicada pelo "spriteDirection"
     * @param g2d ferramenta para renderização
     */
    public void draw(Graphics2D g2d) {
        drawMethod.draw(g2d);
    }

    /**
     * Atualiza estado do player com base na tecla pressionada "n"
     * @param keyHandler Entradas do teclado
     */
    private void updateState(KeyHandler keyHandler){
        if (keyHandler.isKeyN()) {
            keyHandler.setKeyN(false);
            switch (this.currentState) {
                case DEFAULT -> {
                    this.currentState = MapPlayerStateEnum.NINJA;
                    this.drawMethod.setSpritesSelect(1);
                    this.setSpriteSizeX(Constants.TILE_SIZE);
                    this.setSpriteSizeY(Constants.TILE_SIZE);
                }
                case NINJA -> {
                    this.currentState = MapPlayerStateEnum.DEFAULT;
                    this.drawMethod.setSpritesSelect(0);
                    this.setSpriteSizeX(Constants.TILE_SIZE * 2);
                    this.setSpriteSizeY(Constants.TILE_SIZE * 2);
                }
            }
            this.setScreenX(SCREEN_X - (float) this.getSpriteSizeX() / 2);
            this.setScreenY(SCREEN_Y - (float) this.getSpriteSizeY() / 2);
        }
        this.velocity = this.currentState.estadoAtual;
    }

    /**
     * @return hitbox do player
     */
    public Hitbox getHitbox() {
        return hitbox;
    }
}
