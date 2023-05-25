package game_entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import gameloop.Constants;
import gameloop.KeyHandler;

public class Player extends GameEntity{
    private int cooldown;
    private final int COOLDOWN = 5;

    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;

    //Sprites
    private BufferedImage standFront, standBack;
    private ArrayList<BufferedImage> up, down, right, left;


    private String spriteDirection; //Indica a orientação do sprite
    private int spriteCounter = 0; //Conta quantos sprites foram renderizados
    private int spriteNumber = 0; //Indica qual sprite está sendo renderizado, no caso de um array

    public Player(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
        this.cooldown = 0;
        this.getImage();
    }

    @Override
    public void tick() {}

    @Override
    public void tick (Vector direction) {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
        this.checkWindowBorder();
        this.spriteUpdate(direction);
    }

    public Projectile shoot(int mouseX, int mouseY){
        this.cooldown = 0;
        Vector direction = new Vector(mouseX - SCREEN_X, mouseY - this.SCREEN_Y);
        direction = Vector.unitVector(direction);
        return new Projectile(this.SCREEN_X + 10, this.SCREEN_Y + 10, 10, direction);
    }

    public boolean canShoot() {
        if (this.cooldown < this.COOLDOWN)
            this.cooldown += 1;
        return this.cooldown == this.COOLDOWN;
    }

    /**
     * 
     * @param keyHandler Inputs do teclado
     * @return Devolve um vetor que indica a direção do movimento do player, com base no input do teclado
     */
    public Vector updateDirection (KeyHandler keyHandler) {
        Vector direction;

        if (keyHandler.isKeyW()) {
            direction = Constants.DIRECTION_UP;
        } else if (keyHandler.isKeyA()) {
            direction = Constants.DIRECTION_LEFT;
        } else if (keyHandler.isKeyS()) {
            direction = Constants.DIRECTION_DOWN;
        }  else if (keyHandler.isKeyD()) {
            direction = Constants.DIRECTION_RIGHT;
        }  else {
            direction = Constants.NULL_VECTOR;
        }
        return direction;
    }

    /**
     * @param g2d ferramenta para renderização
     * 
     * Este método é responsável por desenhar na tela (frame aberto) um sprite
     * de acordo com o a direção indicada pelo "spriteDirection"
     */
    public void draw (Graphics2D g2d) {
        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = standFront;
            case "STAND_BACK" -> playerImage = standBack;
            case "UP" -> playerImage = up.get(spriteNumber);
            case "DOWN" -> playerImage = down.get(spriteNumber);
            case "RIGHT" -> playerImage = right.get(spriteNumber);
            case "LEFT" -> playerImage = left.get(spriteNumber);
        }
        g2d.drawImage(playerImage, SCREEN_X - Constants.TILE_SIZE / 2, SCREEN_Y - Constants.TILE_SIZE / 2, Constants.TILE_SIZE, Constants.TILE_SIZE, null);
    }

    /**
     * @param direction Vetor que indica a direção do movimento do player
     * 
     * Este método atualiza a direção do sprite com base no movimento do player
     * Se não houver movimento, opta por uma das opções "stand"
     */
    private void spriteUpdate (Vector direction) {
        spriteCounterUpdate();

        if (direction == Constants.DIRECTION_UP) {
            this.spriteDirection = "UP";
        } else if (direction == Constants.DIRECTION_LEFT) {
            this.spriteDirection = "LEFT";
        } else if (direction == Constants.DIRECTION_DOWN) {
            this.spriteDirection = "DOWN";
        }  else if (direction == Constants.DIRECTION_RIGHT) {
            this.spriteDirection = "RIGHT";
        }  else if (direction == Constants.NULL_VECTOR) {
             if (spriteDirection == "UP" || spriteDirection == "STAND_BACK")
                this.spriteDirection = "STAND_BACK";
            else
                this.spriteDirection = "STAND_FRONT";
        }
    }

    /**
     * Método responsável pela alternação dos sprites, para fins de animação
     * Quando o contador spriteCounter atinge certo valor, ele atualiza o spriteNumber,
     * alternando entre 0 e 1 (indica dois sprites diferentes)
     */
    private void spriteCounterUpdate () {
        spriteCounter++;
        if (spriteCounter >= 10) {
            if (spriteNumber == 0) {
                spriteNumber = 1;
            } else if (spriteNumber == 1)
                spriteNumber = 0;
            spriteCounter = 0;
        }
    }

    /**
     * Método que carrega as imagens dos sprites
     */
    private void getImage () {
        this.up = new ArrayList<>();
        this.down = new ArrayList<>();
        this.right = new ArrayList<>();
        this.left = new ArrayList<>();

        try {
            standFront = ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusStand.png"));
            standBack = ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusBack.png"));
            up.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusUp1.png")));
            up.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusUp2.png")));
            down.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusDown1.png")));
            down.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusDown2.png")));
            right.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusRight1.png")));
            right.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusRight2.png")));
            left.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusLeft1.png")));
            left.add(ImageIO.read(getClass().getResourceAsStream("/resources/player/amongusLeft2.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    } 
}