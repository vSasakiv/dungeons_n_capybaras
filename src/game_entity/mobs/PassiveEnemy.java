package game_entity.mobs;

import game_entity.Attributes;
import game_entity.Counter;
import game_entity.Hitbox;
import game_entity.Vector;
import game_entity.weapons.AttackResults;
import game_entity.weapons.Weapon;

import java.util.ArrayList;

public class PassiveEnemy extends Enemy{

    // contadores de State do inimigo
    private final Counter patrolCounter;
    private final Counter patrolCoolDownCounter;
    private boolean shouldShoot;
    private Vector direction;

    /**
     * @param worldPosX posição em x do inimigo no mundo
     * @param worldPosY posição em y do inimigo no mundo
     * @param velocity velocidade do inimigo
     * @param hitbox hitbox do inimigo
     */
    public PassiveEnemy(float worldPosX, float worldPosY, int velocity, Hitbox hitbox, Attributes attributes, Weapon weapon) {
        super(worldPosX, worldPosY, velocity);
        this.hitbox = hitbox;
        this.attributes = attributes;
        this.weapon = weapon;
        this.invincibilityCounter = new Counter(30, 1);
        this.state = EnemyState.PATROL;
        this.shouldShoot = false;
        this.direction = new Vector(0, 0);
        this.patrolCounter = new Counter(30, 1);
        this.patrolCounter.resetCounter();
        this.patrolCoolDownCounter = new Counter(60, 1);
        this.patrolCoolDownCounter.resetCounter();
    }
    @Override
    public void tick() {}
    @Override
    public void tick(Vector playerPos){
        this.weapon.tick();
        this.invincibilityCounter.tick();
        // obtemos distância do inimigo até o player
        Vector distanceVector = new Vector(
                playerPos.x - this.getWorldPosX(),
                playerPos.y - this.getWorldPosY());
        checkPlayer((int) distanceVector.module());
        switch (this.state){
            case PATROL -> this.patrol();
            case ACTIVE -> this.active(distanceVector);
        }
        this.hitbox.setPosition(this.position);
        this.patrolCounter.tick();
        this.patrolCoolDownCounter.tick();
    }

    /**
     * @param distance distância do inimigo até o player
     */
    private void checkPlayer(int distance){
        int forgetDistance = 500; // distância a partir da qual o inimigo esquece do player, vai para estado PATROL
        int detectDistance = 200; // distância a partir da qual o inimigo detecta o player, vai para estado ACTIVE
        if (distance <= detectDistance) this.state = EnemyState.ACTIVE;
        else if (distance >= forgetDistance) this.state = EnemyState.PATROL;
    }
    private void patrol(){
        this.shouldShoot = false; // não deve atirar no modo patrulha
        if (patrolCoolDownCounter.isZero() && this.patrolCounter.isZero()) {
            this.direction = Vector.randomUnitVector(); // caso devemos patrulhar, geramos uma direção aleatória
            this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
            this.checkWindowBorder();
            this.patrolCounter.start(); // iniciamos o contador da patrulha
            this.patrolCoolDownCounter.start(); // iniciamos o contador do cooldown da patrulha
        }
        else if (patrolCounter.isCounting()) { // caso o contador da patrulha esteja contando
            this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
            // somamos na posição, a direção (gerada anteriormente) multiplicado pela velocidade
            this.checkWindowBorder();
            this.patrolCoolDownCounter.stop();
            // paramos o contador do cooldown da patrulha para diferir de 1, e possamos separar os períodos
        }
        else {patrolCoolDownCounter.start();}
    }

    /**
     * @param distance vetor distância entro o inimigo e o player
     */
    private void active(Vector distance){
        int safeDistance = 150; // O inimigo sempre tenta manter uma distância mínima do player
        if (distance.module() <= safeDistance){
            // Caso o player chegue muito perto, o inimigo tenta fugir para a direção oposta
            this.direction = Vector.rotateVector(Vector.unitVector(distance), 180);
            this.shouldShoot = false;
        }
        else {
            // Caso o player esteja na distância segura, para e tenta atirar no player
            this.direction = new Vector(0, 0);
            this.shouldShoot = true;
        }
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.direction, velocity));
        this.checkWindowBorder();
    }

    @Override
    public AttackResults updateShoot(Vector playerPos) {
        if (shouldShoot && this.weapon.canShoot()){
            Vector direction = Vector.unitVector(
                    new Vector(
                            playerPos.x - this.getWorldPosX(),
                            playerPos.y - this.getWorldPosY())
            );
            return this.weapon.attack(
                    (int) this.getWorldPosX(),
                    (int) this.getWorldPosY(),
                    direction);
        }
        else return new AttackResults(new ArrayList<>(), new ArrayList<>());
    }
}
