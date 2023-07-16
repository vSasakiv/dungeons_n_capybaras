package game_entity;

import java.awt.Graphics2D;
import java.util.ArrayList;
import game_entity.entity_sprites.MovingEntitySprites;
import game_entity.weapons.AttackResults;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.MouseHandler;
import gameloop.render.*;

public class DungeonPlayer extends AttackingEntity{
    //Posições fixas do player: centrado na tela
    public final int SCREEN_X = Constants.WIDTH / 2;
    public final int SCREEN_Y = Constants.HEIGHT / 2;
    private final Counter invincibilityCounter;
    private final Hitbox hitbox;
    DrawMovingEntity drawMethod;
    protected ArrayList<MovingEntitySprites> playerSprites;

    /**
     * Construtor do player, que o inicializa numa posição pré-determinada
     * @param posX coordenada x de nascimento
     * @param posY coordenada y de nascimento
     * @param velocity velocidade
     */
    public DungeonPlayer(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
        this.setSpriteSizeX(Constants.TILE_SIZE * 2);
        this.setSpriteSizeY(Constants.TILE_SIZE * 2);
        this.setScreenX(SCREEN_X - (float) this.getSpriteSizeX() / 2);
        this.setScreenY(SCREEN_Y - (float) this.getSpriteSizeY() / 2);
        this.hitbox = new Hitbox(Constants.TILE_SIZE * 2.0F / 3, Constants.TILE_SIZE * 2.0F / 3, new Vector(this.getWorldPosX(), this.getWorldPosY()));
        this.invincibilityCounter = new Counter(30, 1);
        this.setAttributes(new Attributes(10, 10, 200, 5));
        this.loadSprites();
        drawMethod = new DrawPlayer(this, playerSprites);
    }

    public void tick(KeyHandler keyHandler, MouseHandler mouseHandler) {
        this.setDirection(DirectionUpdater.updateDirection(keyHandler, drawMethod));
        this.invincibilityCounter.tick();
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.getDirection(), velocity));
        this.getWeapon().tick();
        this.getAttributes().tick();
        this.tickAttacks(this.getDirection());
        this.updateAttack(mouseHandler);
        this.updateWeapon(mouseHandler);
        this.hitbox.setPosition(this.position);
    }

    //Carrega os sprites do player
    protected void loadSprites() {
        playerSprites = new ArrayList<>();
        playerSprites.add(PlayerSpriteFactory.create("default"));
    }

    /**
     * Atualiza os projéteis e ataques
     * @param mouseHandler Inputs do mouse
     */
    private void updateAttack(MouseHandler mouseHandler){
        AttackResults results;
        if (mouseHandler.isMousePress() && this.getWeapon().canShoot()) {
            Vector direction = new Vector (
                    mouseHandler.getMouseX() - (this.getScreenX() + (float) this.getSpriteSizeX() / 2),
                    mouseHandler.getMouseY() - (this.getScreenY() + (float) this.getSpriteSizeY() / 2)
            );
            direction = Vector.unitVector(direction);
            results = this.getWeapon().attack(
                    (int) this.getWorldPosX(),
                    (int) (this.getWorldPosY() + this.getSpriteSizeY()/6),
                    direction
            );
        }
        else results = new AttackResults(new ArrayList<>(), new ArrayList<>());
        this.getMeleeAttacks().addAll(results.getHitboxes());
        this.getRangedAttacks().addAll(results.getProjectiles());
    }

    public void gotHit(int damage){
        if (invincibilityCounter.isZero()){
            this.getAttributes().takeDamage(damage);
            invincibilityCounter.start();
        }
    }

    /**
     * Desenha um sprite na tela (frame aberto) conforme a direção indicada pelo "spriteDirection"
     * @param g2d ferramenta para renderização
     */
    public void draw(Graphics2D g2d) {
        //Desenha o player
        drawMethod.draw(g2d);
        //Desenha a armar do player
        this.getWeapon().draw(g2d, this);
    }

    /**
     * Atualiza direção da arma conforme a posição do mouse
     * @param mouseHandler input do mouse
     */
    private void updateWeapon(MouseHandler mouseHandler) {
        this.getWeapon().setDirection (
                new Vector(
                mouseHandler.getMouseX() - (this.getScreenX() + (float) this.getSpriteSizeX() / 2) ,
                mouseHandler.getMouseY() - (this.getScreenY() + (float) this.getSpriteSizeY() / 2))
        );
    }

    /**
     * @return hitbox do player
     */
    public Hitbox getHitbox() {
        return hitbox;
    }
}