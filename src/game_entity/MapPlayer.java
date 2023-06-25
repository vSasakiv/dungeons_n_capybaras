package game_entity;

import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.render.Draw;
import gameloop.render.DrawMapPlayer;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class MapPlayer extends GameEntity{
    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;

    private MapPlayerStateEnum currentState;
    //Sprites
    private String spriteDirection; // Indicã a orientação do sprite
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    private int spriteNumber = 0; // Indica qual sprite está sendo renderizado, no caso de um array
    private final Hitbox hitbox;
    public int playerSelected = 0;
    protected ArrayList<PlayerSprite> playerSprites;

    Draw drawMethod;

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
        this.hitbox = new Hitbox(Constants.TILE_SIZE, Constants.TILE_SIZE, new Vector(this.getWorldPosX(), this.getWorldPosY()));
        this.loadSprites();
        drawMethod = new DrawMapPlayer(this);
    }

    protected void loadSprites() {
        playerSprites = new ArrayList<>();
        playerSprites.add(PlayerSpriteFactory.create("default"));
        playerSprites.add(PlayerSpriteFactory.create("ninja"));
    }

    public void tick(KeyHandler keyHandler) {
        updateState(keyHandler);
        this.setDirection(this.updateDirection(keyHandler));
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.getDirection(), velocity));
        this.hitbox.setPosition(this.position);
        System.out.println("pos x: " + this.position.x + "pos y: " + this.position.y);
    }

    /**
     * Atualiza a direção do movimento do player, com base no input do teclado
     * @param keyHandler Inputs do teclado
     * @return um vetor correspondente à nova direção
     */
    private Vector updateDirection(KeyHandler keyHandler) {
        Vector direction = Constants.NULL_VECTOR;

        //SpriteCounter só é atualizado se alguma tecla está sendo pressionada
        if (keyHandler.isPressed())
            spriteCounterUpdate();

        if (keyHandler.isKeyA() && keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP_LEFT, direction);
        else if (keyHandler.isKeyD() && keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP_RIGHT, direction);
        else if (keyHandler.isKeyS() && keyHandler.isKeyA())
            direction = Vector.add(Constants.DIRECTION_DOWN_LEFT, direction);
        else if (keyHandler.isKeyS() && keyHandler.isKeyD())
            direction = Vector.add(Constants.DIRECTION_DOWN_RIGHT, direction);

        else if (keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP, direction);
        else if (keyHandler.isKeyA())
            direction = Vector.add(Constants.DIRECTION_LEFT, direction);
        else if (keyHandler.isKeyS())
            direction = Vector.add(Constants.DIRECTION_DOWN, direction);
        else if (keyHandler.isKeyD())
            direction = Vector.add(Constants.DIRECTION_RIGHT, direction);

        this.spriteUpdate(direction);
        return direction;
    }

    /**
     * Desenha um sprite na tela (frame aberto) conforme a direção indicada pelo "spriteDirection"
     * @param g2d ferramenta para renderização
     */
    public void draw(Graphics2D g2d) {
        drawMethod.draw(g2d);
    }

    /**
     * Atualiza a direção do sprite, com base na movimentação do player
     * @param direction Direção do movimento do player
     */
    private void spriteUpdate(Vector direction) {

        //Mudança de sprite pelo teclado
        if (Vector.vectorEquals(direction, Constants.DIRECTION_UP)) {
            this.spriteDirection = "UP";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_UP_LEFT)) {
            this.spriteDirection = "UP_LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_UP_RIGHT)) {
            this.spriteDirection = "UP_RIGHT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_LEFT)) {
            this.spriteDirection = "LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN)) {
            this.spriteDirection = "DOWN";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN_LEFT)) {
            this.spriteDirection = "DOWN_LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN_RIGHT)) {
            this.spriteDirection = "DOWN_RIGHT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_RIGHT)) {
            this.spriteDirection = "RIGHT";
        }  else if (Vector.vectorEquals(direction, Constants.NULL_VECTOR)) {
            if (Objects.equals(spriteDirection, "UP") || Objects.equals(spriteDirection, "STAND_BACK"))
                this.spriteDirection = "STAND_BACK";
            else
                this.spriteDirection = "STAND_FRONT";
        }
    }

    /**
     * Alterna os sprites, para fins de animação
     */
    private void spriteCounterUpdate() {

        /*
         * Quando o contador spriteCounter atinge certo valor, ele atualiza o spriteNumber,
         * alternando entre 0 e 1 (indica dois sprites diferentes)
         */
        spriteCounter++;
        if (spriteCounter >= 10) {
            spriteNumber = (spriteNumber + 1) % 4;
            spriteCounter = 0;
        }
    }

    private void updateState(KeyHandler keyHandler){
        if (keyHandler.isKeyN()) {
            keyHandler.setKeyN(false);
            switch (this.currentState) {
                case DEFAULT -> {
                    this.currentState = MapPlayerStateEnum.NINJA;
                    this.playerSelected = 1;
                }
                case NINJA -> {
                    this.currentState = MapPlayerStateEnum.DEFAULT;
                    this.playerSelected = 0;
                }
            }
        }
        this.velocity = this.currentState.estadoAtual;
    }

    public BufferedImage getPlayerImage(PlayerSprite playerSelected) {
        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = playerSelected.standFront;
            case "STAND_BACK" -> playerImage = playerSelected.standBack;
            case "UP" -> playerImage = playerSelected.up.getSpriteArray()[spriteNumber];
            case "UP_LEFT" -> playerImage = playerSelected.upLeft.getSpriteArray()[spriteNumber];
            case "UP_RIGHT" -> playerImage = playerSelected.upRight.getSpriteArray()[spriteNumber];
            case "DOWN" -> playerImage = playerSelected.down.getSpriteArray()[spriteNumber];
            case "DOWN_LEFT" -> playerImage = playerSelected.downLeft.getSpriteArray()[spriteNumber];
            case "DOWN_RIGHT" -> playerImage = playerSelected.downRight.getSpriteArray()[spriteNumber];
            case "RIGHT" -> playerImage = playerSelected.right.getSpriteArray()[spriteNumber];
            case "LEFT" -> playerImage = playerSelected.left.getSpriteArray()[spriteNumber];
        }
        return playerImage;
    }

    /**
     * @return hitbox do player
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    public ArrayList<PlayerSprite> getPlayerSprites() {
        return playerSprites;
    }
}
