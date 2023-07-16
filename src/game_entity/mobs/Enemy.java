package game_entity.mobs;

import game_entity.*;
import game_entity.entity_sprites.*;
import game_entity.entity_sprites.mobs.AntSprite;
import game_entity.weapons.AttackResults;
import game_entity.weapons.Weapon;
import gameloop.Constants;
import gameloop.render.DrawMovingEntity;

import java.awt.*;
import java.util.ArrayList;

public class Enemy extends AttackingEntity {
    protected Counter invincibilityCounter; // Contador de frames de invincibility
    public Hitbox hitbox; // Hitbox do inimigo
    private final DrawMovingEntity drawMethod;
    private EnemyStrategy estrategia; // Estratégia que o inimigo segue

    /**
     * Construtor da entidade, numa posição predeterminada
     *
     * @param worldPosX coordenada x inicial, em relação ao mundo
     * @param worldPosY coordenada y inicial, em relação ao mundo
     * @param velocity  velocidade
     * @param estrategia estrategia de combate do inimigo
     */
    public Enemy(float worldPosX, float worldPosY, int velocity, EnemyStrategy estrategia, Weapon weapon, Hitbox hitbox, Attributes atributos) {
        super(worldPosX, worldPosY, velocity);
        this.estrategia = estrategia;
        this.invincibilityCounter = new Counter(30, 1);
        this.setWeapon(weapon);
        this.setHitbox(hitbox);
        this.setAttributes(atributos);
        ArrayList<MovingEntitySprites> sprites = new ArrayList<>();
        sprites.add(new AntSprite());
        this.drawMethod = new DrawMovingEntity(this, sprites);
        this.setSpriteSizeX(Constants.TILE_SIZE);
        this.setSpriteSizeY(Constants.TILE_SIZE);
    }

    /**
     * @param worldPosX posição x do novo inimigo
     * @param worldPosY posição y do novo inimigo
     * @return um clone de um inimigo modelo nas posições worldPosX e worldPosY
     */
    public Enemy clone(float worldPosX, float worldPosY){
        Enemy clone = new Enemy(worldPosX, worldPosY, this.velocity, this.estrategia.clone(), this.getWeapon().clone(), new Hitbox(this.hitbox), new Attributes(this.getAttributes()));
        clone.setWeapon(this.getWeapon().clone());
        clone.hitbox = new Hitbox(this.hitbox);
        clone.setAttributes(new Attributes(this.getAttributes()));
        return clone;
    }

    /**
     * Atualiza o inimigo e todos os seus atributos
     * @param playerPos Posição do player
     */
    public void tick(Vector playerPos){
        this.invincibilityCounter.tick();
        this.setDirection(estrategia.newDirection(this.position, playerPos));
        this.position = Vector.add(this.position, Vector.scalarMultiply(getDirection(), this.velocity));
        this.getWeapon().tick();
        this.updateShoot(playerPos);
        this.tickAttacks(getDirection());
        this.getAttributes().tick();
        this.drawMethod.spriteUpdate(DirectionUpdater.updateDirection(getDirection()));
        this.drawMethod.spriteCounterUpdate();
        this.setScreenX(this.getWorldPosX() - playerPos.x + (float) Constants.WIDTH /2 - (float) this.getSpriteSizeX() / 2);
        this.setScreenY(this.getWorldPosY() - playerPos.y + (float) Constants.HEIGHT /2 - (float) this.getSpriteSizeX() / 2);
        this.hitbox.setPosition(this.position);
    }

    public void draw(Graphics2D g2d) {
        drawMethod.draw(g2d);
    }

    /**
     * @param hitbox Hitbox a ser utilizada no inimigo
     */
    public void setHitbox(Hitbox hitbox) { this.hitbox = hitbox; }

    /**
     * @param estrategia Estrategia a ser utilizada
     */
    public void setEstrategia(EnemyStrategy estrategia) { this.estrategia = estrategia; }

    /**
     * Atualiza a lista de projéteis e de hit-boxes melee do inimigo
     * @param playerPos Posição do player
     */
    private void updateShoot(Vector playerPos) {
        AttackResults results;
        if (this.estrategia.shouldShoot() && this.getWeapon().canShoot()){
            Vector direction = Vector.unitVector(
                    new Vector(
                            playerPos.x - this.getWorldPosX(),
                            playerPos.y - this.getWorldPosY())
            );
            results = this.getWeapon().attack(
                    (int) this.getWorldPosX(),
                    (int) this.getWorldPosY(),
                    direction);
        }
        else results = new AttackResults(new ArrayList<>(), new ArrayList<>());
        this.getMeleeAttacks().addAll(results.getHitboxes());
        this.getRangedAttacks().addAll(results.getProjectiles());
    }

    /**
     * @param damage dano a ser recebido pelo inimigo
     */
    public void gotHit(int damage) {
        if (invincibilityCounter.isZero()){
            this.getAttributes().takeDamage(damage);
            invincibilityCounter.start();
        }
    }

    /**
     * @return true caso o inimigo não tenha mais pontos de vida restantes.
     */
    public boolean isDead() { return this.getAttributes().isDead(); }
}
