package game_entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.MouseHandler;
import game_entity.weapons.Projectile;
import game_entity.weapons.Weapon;

public class Player extends GameEntity{
    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;

    //Sprites
    private BufferedImage standFront, standBack, standRight, standLeft; //OBS: Alguns ainda não implementados
    private ArrayList<BufferedImage> up, up_left, up_right, down, down_left, down_right, right, left;


    private String spriteDirection; // Indica a orientação do sprite
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    private int spriteNumber = 0; // Indica qual sprite está sendo renderizado, no caso de um array

    private Weapon weapon;

    public Player(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
        this.getImage();
    }

    @Override
    public void tick() {}

    @Override
    public void tick (Vector direction) {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
        this.weapon.tick();
        this.checkWindowBorder();

    }

    /**
     * 
     * @param keyHandler Inputs do teclado
     * @return Devolve um vetor que indica a direção do movimento do player, com base no input do teclado
     */
    public Vector updateDirection (KeyHandler keyHandler, MouseHandler mouseHandler) {
        Vector direction = Constants.NULL_VECTOR;

        //SpriteCounter só é atualizado se alguma tecla esta sendo pressionada
        if (keyHandler.isPressed())
            spriteCounterUpdate();

        this.spriteUpdate(mouseHandler);

        if (keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP, direction);
        if (keyHandler.isKeyA())
            direction = Vector.add(Constants.DIRECTION_LEFT, direction);
        if (keyHandler.isKeyS())
            direction = Vector.add(Constants.DIRECTION_DOWN, direction);
        if (keyHandler.isKeyD())
            direction = Vector.add(Constants.DIRECTION_RIGHT, direction);

        return direction;
    }

    /**
     * @param mouseHandler Inputs do mouse
     * @return uma lista contendo todos os projéteis gerados, podendo ser vazia.
     */
    public ArrayList<Projectile> updateShoot(MouseHandler mouseHandler){
        if (mouseHandler.isMousePress() && this.weapon.canShoot()){
            Vector direction = new Vector(mouseHandler.getMouseX() - SCREEN_X, mouseHandler.getMouseY() - SCREEN_Y);
            direction = Vector.unitVector(direction);
            return weapon.shoot(this.SCREEN_X - 10, this.SCREEN_Y - 10, direction);
        }
        else return new ArrayList<>();
    }

    /**
     * @param g2d ferramenta para renderização
     * Este método é responsável por desenhar na tela (frame aberto) um sprite
     * conforme a direção indicada pelo "spriteDirection"
     */
    public void draw (Graphics2D g2d) {
        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = standFront;
            case "STAND_BACK" -> playerImage = standBack;
            case "UP" -> playerImage = up.get(spriteNumber);
            case "UP_LEFT" -> playerImage = up_left.get(spriteNumber);
            case "UP_RIGHT" -> playerImage = up_right.get(spriteNumber);
            case "DOWN" -> playerImage = down.get(spriteNumber);
            case "DOWN_LEFT" -> playerImage = down_left.get(spriteNumber);
            case "DOWN_RIGHT" -> playerImage = down_right.get(spriteNumber);
            case "RIGHT" -> playerImage = right.get(spriteNumber);
            case "LEFT" -> playerImage = left.get(spriteNumber);
        }
        g2d.drawImage(playerImage, SCREEN_X - Constants.TILE_SIZE / 2, SCREEN_Y - Constants.TILE_SIZE / 2, 2 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, null);
    }

    /**
     * @param mouseHandler ferramente utilizada para descobrir a posição do mouse
     * Este método atualiza a direção do sprite com base na posição do mouse
     */
    private void spriteUpdate (MouseHandler mouseHandler) {
        Vector direction = new Vector(mouseHandler.getMouseX() - SCREEN_X, mouseHandler.getMouseY() - SCREEN_Y);
        this.spriteDirection = "UP";
        float dirUp = Vector.innerProduct(direction, Constants.DIRECTION_UP);
        double degree = Vector.getDegree(direction);

        if (degree >= Math.toRadians(0) && degree < Math.toRadians(30)) {
            this.spriteDirection = "RIGHT";
        }
        if (degree >= Math.toRadians(30) && degree < Math.toRadians(60)) {
            if (dirUp > 0) this.spriteDirection = "UP_RIGHT";
            else this.spriteDirection = "DOWN_RIGHT";
        }
        if (degree >= Math.toRadians(60) && degree < Math.toRadians(120)) {
            if (dirUp > 0) this.spriteDirection = "UP";
            else this.spriteDirection = "DOWN";
        }
        if (degree >= Math.toRadians(120) && degree < Math.toRadians(150)) {
            if (dirUp > 0) this.spriteDirection = "UP_LEFT";
            else this.spriteDirection = "DOWN_LEFT";
        }
        if (degree >= Math.toRadians(150) && degree <= Math.toRadians(180)) {
            this.spriteDirection = "LEFT";
        }

        //Mudança de sprite pelo teclado
        /* if (Vector.vectorEquals(direction, Constants.DIRECTION_UP)) {
            this.spriteDirection = "UP";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_LEFT)) {
            this.spriteDirection = "LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN)) {
            this.spriteDirection = "DOWN";
        }  else if (Vector.vectorEquals(direction, Constants.DIRECTION_RIGHT)) {
            this.spriteDirection = "RIGHT";
        }  else if (Vector.vectorEquals(direction, Constants.NULL_VECTOR)) {
             if (spriteDirection == "UP" || spriteDirection == "STAND_BACK")
                this.spriteDirection = "STAND_BACK";
            else
                this.spriteDirection = "STAND_FRONT";
        } */
    }

    /**
     * Método responsável pela alternação dos sprites, para fins de animação
     * Quando o contador spriteCounter atinge certo valor, ele atualiza o spriteNumber,
     * alternando entre 0 e 1 (indica dois sprites diferentes)
     */
    private void spriteCounterUpdate () {
        spriteCounter++;
        if (spriteCounter >= 10) {
            spriteNumber = (spriteNumber + 1) % 3;
            spriteCounter = 0;
        }
    }

    /**
     * Método que carrega as imagens dos sprites
     */
    private void getImage () {
        this.up = new ArrayList<>();
        this.up_left = new ArrayList<>();
        this.up_right = new ArrayList<>();
        this.down = new ArrayList<>();
        this.down_left = new ArrayList<>();
        this.down_right = new ArrayList<>();
        this.right = new ArrayList<>();
        this.left = new ArrayList<>();


        try {
            standBack = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up/Character_Up_01.png")));
            up.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up/Character_Up_02.png"))));
            up.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up/Character_Up_03.png"))));
            up.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up/Character_Up_04.png"))));

            up_left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up_left/Character_UpLeft_02.png"))));
            up_left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up_left/Character_UpLeft_03.png"))));
            up_left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up_left/Character_UpLeft_04.png"))));

            up_right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up_right/Character_UpRight_02.png"))));
            up_right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up_right/Character_UpRight_03.png"))));
            up_right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/up_right/Character_UpRight_04.png"))));

            standFront = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down/Character_Down_01.png")));
            down.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down/Character_Down_02.png"))));
            down.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down/Character_Down_03.png"))));
            down.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down/Character_Down_04.png"))));

            down_left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down_left/Character_DownLeft_02.png"))));
            down_left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down_left/Character_DownLeft_03.png"))));
            down_left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down_left/Character_DownLeft_04.png"))));

            down_right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down_right/Character_DownRight_02.png"))));
            down_right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down_right/Character_DownRight_03.png"))));
            down_right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/down_right/Character_DownRight_04.png"))));

            standRight = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_01.png")));
            right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_02.png"))));
            right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_03.png"))));
            right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_04.png"))));

            standLeft = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_01.png")));
            left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_02.png"))));
            left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_03.png"))));
            left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_04.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}