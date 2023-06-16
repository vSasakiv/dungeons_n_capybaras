package src.game_entity;

import src.gameloop.Constants;
import src.gameloop.KeyHandler;
import src.tile.AnimationSprite;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class MapPlayer extends GameEntity {
    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;

    private MapPlayerStateEnum currentState;
    //Sprites
    private ArrayList<BufferedImage> standFront, standBack;
    private String spriteDirection; // Indicã a orientação do sprite
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    private int spriteNumber = 0; // Indica qual sprite está sendo renderizado, no caso de um array
    private ArrayList<AnimationSprite> up, upLeft, upRight, down, downLeft, downRight, right, left;
    private final Hitbox hitbox;
    public int playerSelected = 0;


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
    }

    public void tick(KeyHandler keyHandler) {
        updateState(keyHandler);
        this.setDirection(this.updateDirection(keyHandler));
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.getDirection(), velocity));
        this.hitbox.setPosition(this.position);
    }

    /**
     * Atualiza a direção do movimento do player, com base no input do teclado
     *
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

        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = standFront.get(playerSelected);
            case "STAND_BACK" -> playerImage = standBack.get(playerSelected);
            case "UP" -> playerImage = up.get(playerSelected).getSpriteArray()[spriteNumber];
            case "UP_LEFT" -> playerImage = upLeft.get(playerSelected).getSpriteArray()[spriteNumber];
            case "UP_RIGHT" -> playerImage = upRight.get(playerSelected).getSpriteArray()[spriteNumber];
            case "DOWN" -> playerImage = down.get(playerSelected).getSpriteArray()[spriteNumber];
            case "DOWN_LEFT" -> playerImage = downLeft.get(playerSelected).getSpriteArray()[spriteNumber];
            case "DOWN_RIGHT" -> playerImage = downRight.get(playerSelected).getSpriteArray()[spriteNumber];
            case "RIGHT" -> playerImage = right.get(playerSelected).getSpriteArray()[spriteNumber];
            case "LEFT" -> playerImage = left.get(playerSelected).getSpriteArray()[spriteNumber];
        }
        //Desenha o player
        if (playerSelected == 0) {
            g2d.drawImage(
                    playerImage,
                    (int)this.getScreenX(),
                    (int)this.getScreenY(),
                    this.getSpriteSizeX(),
                    this.getSpriteSizeY(),
                    null
            );
        } else {
            g2d.drawImage(
                    playerImage,
                    (int) this.getScreenX()+ Constants.TILE_SIZE/2,
                    (int) this.getScreenY()+ Constants.TILE_SIZE/2,
                    this.getSpriteSizeX()/2,
                    this.getSpriteSizeY()/2,
                    null
            );
        }
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

    private void loadSprites () {
        int width = 32;
        int height = 32;
        up = new ArrayList<>();
        upLeft = new ArrayList<>();
        upRight = new ArrayList<>();
        right = new ArrayList<>();
        left = new ArrayList<>();
        down = new ArrayList<>();
        downLeft = new ArrayList<>();
        downRight = new ArrayList<>();
        standBack = new ArrayList<>();
        standFront =  new ArrayList<>();
        /*String CurrentDirectoryPath = System.getProperty("user.dir");
        File CurrentDirectory = new File(CurrentDirectoryPath);
        String AbsolutePath = CurrentDirectory.getAbsolutePath();
        AbsolutePath = AbsolutePath + "/src/resources/player/Character_Up.png";*/
        this.up.add(new AnimationSprite("/src/resources/player/Character_Up.png", width, height, 0, 0, 4));
        this.up.add(new AnimationSprite("/src/resources/player/ninjaUp.png", width/2, height/2, 0, 0, 4));

        this.upLeft.add(new AnimationSprite("/src/resources/player/Character_UpLeft.png", width, height, 0, 0, 4));
        this.upLeft.add(new AnimationSprite("/src/resources/player/ninjaUp.png", width/2, height/2, 0, 0, 4));

        this.upRight.add(new AnimationSprite("/src/resources/player/Character_UpRight.png", width, height, 0, 0, 4));
        this.upRight.add(new AnimationSprite("/src/resources/player/ninjaUp.png", width/2, height/2, 0, 0, 4));

        this.down.add(new AnimationSprite("/src/resources/player/Character_Down.png", width, height, 0, 0, 4));
        this.down.add(new AnimationSprite("/src/resources/player/ninjaDown.png", width/2, height/2, 0, 0, 4));

        this.downLeft.add(new AnimationSprite("/src/resources/player/Character_DownLeft.png", width, height, 0, 0, 4));
        this.downLeft.add(new AnimationSprite("/src/resources/player/ninjaDown.png", width/2, height/2, 0, 0, 4));

        this.downRight.add(new AnimationSprite("/src/resources/player/Character_DownRight.png", width, height, 0, 0, 4));
        this.downRight.add(new AnimationSprite("/src/resources/player/ninjaDown.png", width/2, height/2, 0, 0, 4));

        this.right.add(new AnimationSprite("/src/resources/player/Character_Right.png", width, height, 0, 0, 4));
        this.right.add(new AnimationSprite("/src/resources/player/ninjaRight.png", width/2, height/2, 0, 0, 4));

        this.left.add(new AnimationSprite("/src/resources/player/Character_Left.png", width, height, 0, 0, 4));
        this.left.add(new AnimationSprite("/src/resources/player/ninjaLeft.png", width/2, height/2, 0, 0, 4));

        standBack.add(up.get(0).getSpriteArray()[0]);
        standBack.add(up.get(1).getSpriteArray()[0]);
        standFront.add(down.get(0).getSpriteArray()[0]);
        standFront.add(down.get(1).getSpriteArray()[0]);
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

    /**
     * @return hitbox do player
     */
    public Hitbox getHitbox() {
        return hitbox;
    }
}
