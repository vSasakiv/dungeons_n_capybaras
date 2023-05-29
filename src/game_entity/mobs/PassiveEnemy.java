package game_entity.mobs;

import game_entity.Hitbox;
import game_entity.Vector;
import game_entity.weapons.Projectile;

import java.util.ArrayList;

public class PassiveEnemy extends Enemy{

    // contadores de State do inimigo
    private int patrolCoolDownCounter = 0;
    private int patrolCounter = 0;

    private boolean shouldShoot;
    private Vector direction;

    /**
     * @param worldPosX posição em x do inimigo no mundo
     * @param worldPosY posição em y do inimigo no mundo
     * @param velocity velocidade do inimigo
     * @param hitbox hitbox do inimigo
     */
    public PassiveEnemy(float worldPosX, float worldPosY, int velocity, Hitbox hitbox) {
        super(worldPosX, worldPosY, velocity);
        this.hitbox = hitbox;
        this.state = EnemyState.PATROL;
        this.shouldShoot = false;
        this.direction = new Vector(0, 0);
    }
    @Override
    public void tick() {}
    @Override
    public void tick(Vector playerPos){
        this.weapon.tick();
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
        int patrolTime = 30; // tempo que passa se deslocando em uma patrulha
        int patrolCoolDown = 60; // tempo entre patrulhas
        if (patrolCoolDownCounter < patrolCoolDown) patrolCoolDownCounter += 1;
        else if (patrolCounter == 0) {
            this.direction = Vector.randomUnitVector(); // caso devemos patrulhar, geramos uma direção aleatória
            this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
            this.checkWindowBorder();
            this.patrolCounter += 1;
        }
        else if (patrolCounter < patrolTime) {
            this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
            this.checkWindowBorder();
            this.patrolCounter += 1;
        }
        else{
            this.patrolCounter = 0;
            this.patrolCoolDownCounter = 0;
        }
    }

    /**
     * @param distance Vetor distância entre o player e o inimigo
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
    public ArrayList<Projectile> updateShoot(Vector playerPos) {
        if (shouldShoot && this.weapon.canShoot()){
            Vector direction = Vector.unitVector(
                    new Vector(
                            playerPos.x - this.getWorldPosX(),
                            playerPos.y - this.getWorldPosY())
            );
            return this.weapon.shoot(
                    (int) this.getWorldPosX(),
                    (int) this.getWorldPosY(),
                    direction);
        }
        else return new ArrayList<>();
    }
}
