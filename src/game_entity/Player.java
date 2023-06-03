package game_entity;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;
import game_entity.weapons.AttackResults;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.MouseHandler;
import game_entity.weapons.Weapon;
import tile.AnimationSprite;

public class Player extends GameEntity{
    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;

    //Sprites
    private BufferedImage standFront, standBack;
    //private ArrayList<BufferedImage> up, up_left, up_right, down, down_left, down_right, right, left;
    //private BufferedImage[] UP;
    private String spriteDirection; // Indicã a orientação do sprite
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    private int spriteNumber = 0; // Indicã qual sprite está sendo renderizado, no caso de um array
    private AnimationSprite up, upLeft, upRight, down, downLeft, downRight, right, left;

    private final Counter invincibilityCounter;
    private final Attributes attributes;
    private Weapon weapon;
    private final Hitbox hitbox;

    /**
     * Construtor do player, que o inicializa numa posição pré-determinada
     * @param posX coordenada x de nascimento
     * @param posY coordenada y de nascimento
     * @param velocity velocidade
     */
    public Player(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
        this.setSpriteSizeX(Constants.TILE_SIZE * 2);
        this.setSpriteSizeY(Constants.TILE_SIZE * 2);
        this.setScreenX(SCREEN_X - (float) this.getSpriteSizeX() / 2);
        this.setScreenY(SCREEN_Y - (float) this.getSpriteSizeY() / 2);
        this.hitbox = new Hitbox(50, 50, new Vector(this.getWorldPosX(), this.getWorldPosY()));
        this.invincibilityCounter = new Counter(30, 1);
        this.attributes = new Attributes(10, 10, 200);
        this.loadSprites();
    }

    @Override
    public void tick() {}

    @Override
    public void tick(Vector direction) {}

    public void tick(KeyHandler keyHandler, MouseHandler mouseHandler) {
        Vector direction = this.updateDirection(keyHandler);
        this.invincibilityCounter.tick();
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
        this.weapon.tick();
        this.updateWeapon(mouseHandler);
        this.checkWindowBorder();
        this.hitbox.setPosition(this.position);
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
     * atualiza os projéteis
     * @param mouseHandler Inputs do mouse
     * @return uma lista contendo todos os projéteis gerados, podendo ser vazia.
     */
    public AttackResults updateAttack(MouseHandler mouseHandler){
        if (mouseHandler.isMousePress() && this.weapon.canShoot()) {
            Vector direction = new Vector (
                    mouseHandler.getMouseX() - (this.getScreenX() + (float) this.getSpriteSizeX() / 2),
                    mouseHandler.getMouseY() - (this.getScreenY() + (float) this.getSpriteSizeY() / 2)
            );
            direction = Vector.unitVector(direction);
            return this.weapon.attack(
                    (int) this.getWorldPosX(),
                    (int) this.getWorldPosY(),
                    direction
            );
        } 
        else return new AttackResults(new ArrayList<>(), new ArrayList<>());
    }

    public void gotHit(int damage){
        if (invincibilityCounter.isZero()){
            this.attributes.takeDamage(damage);
            invincibilityCounter.start();
        }
    }

    /**
     * Desenha um sprite na tela (frame aberto) conforme a direção indicada pelo "spriteDirection"
     * @param g2d ferramenta para renderização
     */
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = standFront;
            case "STAND_BACK" -> playerImage = standBack;
            case "UP" -> playerImage = up.getSpriteArray()[spriteNumber];
            case "UP_LEFT" -> playerImage = upLeft.getSpriteArray()[spriteNumber];
            case "UP_RIGHT" -> playerImage = upRight.getSpriteArray()[spriteNumber];
            case "DOWN" -> playerImage = down.getSpriteArray()[spriteNumber];
            case "DOWN_LEFT" -> playerImage = downLeft.getSpriteArray()[spriteNumber];
            case "DOWN_RIGHT" -> playerImage = downRight.getSpriteArray()[spriteNumber];
            case "RIGHT" -> playerImage = right.getSpriteArray()[spriteNumber];
            case "LEFT" -> playerImage = left.getSpriteArray()[spriteNumber];
        }
        //Desenha o player
        g2d.drawImage(
                playerImage,
                (int)this.getScreenX(),
                (int)this.getScreenY(),
                this.getSpriteSizeX(),
                this.getSpriteSizeY(),
                null
        );
        //Desenha a armar do player
        this.weapon.draw(g2d, this);
    }

    /**
     * Atualiza direção da arma conforme a posição do mouse
     * @param mouseHandler input do mouse
     */
    private void updateWeapon(MouseHandler mouseHandler) {
        this.weapon.setDirection (
                new Vector(
                mouseHandler.getMouseX() - (this.getScreenX() + (float) this.getSpriteSizeX() / 2) ,
                mouseHandler.getMouseY() - (this.getScreenY() + (float) this.getSpriteSizeY() / 2))
        );
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

    /**
     * Carrega as imagens dos sprites
     */
    /*private void getImage() {
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

            right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_02.png"))));
            right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_03.png"))));
            right.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/right/Character_Right_04.png"))));

            left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_02.png"))));
            left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_03.png"))));
            left.add(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/resources/player/left/Character_Left_04.png"))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    private void loadSprites () {
        int width = 32;
        int height = 32;
        this.up = new AnimationSprite("/resources/player/Character_Up.png", width, height, 0, 0, 4);
        this.upLeft = new AnimationSprite("/resources/player/Character_UpLeft.png", width, height, 0, 0, 4);
        this.upRight = new AnimationSprite("/resources/player/Character_UpRight.png", width, height, 0, 0, 4);
        this.down = new AnimationSprite("/resources/player/Character_Down.png", width, height, 0, 0, 4);
        this.downLeft = new AnimationSprite("/resources/player/Character_DownLeft.png", width, height, 0, 0, 4);
        this.downRight = new AnimationSprite("/resources/player/Character_DownRight.png", width, height, 0, 0, 4);
        this.right = new AnimationSprite("/resources/player/Character_Right.png", width, height, 0, 0, 4);
        this.left = new AnimationSprite("/resources/player/Character_Left.png", width, height, 0, 0, 4);
        standBack = up.getSpriteArray()[0];
        standFront = down.getSpriteArray()[0];
    }

    /**
     * Define arma do player
     * @param weapon arma
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * @return hitbox do player
     */
    public Hitbox getHitbox() {
        return hitbox;
    }

    /**
     * @return atributos do player
     */
    public Attributes getAttributes() {
        return attributes;
    }
}