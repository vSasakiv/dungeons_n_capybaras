package game_entity.mobs;

import game_entity.Vector;
import game_entity.weapons.Projectile;

import java.util.ArrayList;

public class PassiveEnemy extends Enemy{

    private int patrolCoolDownCounter = 0;
    private int patrolCounter = 0;

    private boolean shouldShoot;
    private Vector direction;
    public PassiveEnemy(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY, velocity);
        this.state = EnemyState.PATROL;
        this.shouldShoot = false;
        this.direction = new Vector(0, 0);
    }
    @Override
    public void tick() {}
    @Override
    public void tick(Vector playerPos){
        this.weapon.tick();
        Vector distanceVector = new Vector(
                playerPos.x - this.getWorldPosX(),
                playerPos.y - this.getWorldPosY());
        checkPlayer((int) distanceVector.module());
        switch (this.state){
            case PATROL -> this.patrol();
            case ACTIVE -> this.active(distanceVector);
        }
    }

    private void checkPlayer(int distance){
        int forgetDistance = 500;
        int detectDistance = 200;
        if (distance <= detectDistance) this.state = EnemyState.ACTIVE;
        else if (distance >= forgetDistance) this.state = EnemyState.PATROL;
    }
    private void patrol(){
        this.shouldShoot = false;
        int patrolTime = 30;
        int patrolCoolDown = 60;
        if (patrolCoolDownCounter < patrolCoolDown) patrolCoolDownCounter += 1;
        else if (patrolCounter == 0) {
            this.direction = Vector.randomUnitVector();
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
    private void active(Vector distance){
        System.out.println("Fleeing x: " + distance.x + " y: " + distance.y);
        int safeDistance = 150;
        if (distance.module() <= safeDistance){
            this.direction = Vector.rotateVector(Vector.unitVector(distance), 180);
            this.shouldShoot = false;
        }
        else {
            this.direction = new Vector(0, 0);
            this.shouldShoot = true;
        }
        this.position = Vector.add(this.position, Vector.scalarMultiply(this.direction, velocity));
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
