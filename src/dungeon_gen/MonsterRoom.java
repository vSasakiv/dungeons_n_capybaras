package dungeon_gen;

import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;
import game_entity.mobs.Enemy;
import game_entity.static_entities.Door;
import gameloop.Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class MonsterRoom extends DungeonRoom {

    private final Random random = new Random();
    private int currentWave = 0;
    private boolean hasWaves = true;
    private boolean startWaves = false;
    private final ArrayList<ArrayList<Enemy>> enemyWaves;
    public MonsterRoom(int x, int y, int width, int height, int enemyWaves, int minEnemies, int maxEnemies, ArrayList<Enemy> enemyTemplates, int[][] validTileMatrix, ArrayList<Door> doors) {
        super(x, y, width, height, validTileMatrix, doors);
        this.enemyWaves = new ArrayList<>();
        generateEnemies(minEnemies, maxEnemies, enemyWaves, enemyTemplates);
    }

    private void generateEnemies(int minEnemies, int maxEnemies, int enemyWaves, ArrayList<Enemy> enemyTemplates){
        int numEnemies;
        int minY = this.getMinY();
        int minX = this.getMinX();
        for (int i = 0; i < enemyWaves; i++){
            numEnemies = random.nextInt(minEnemies, maxEnemies+1);
            this.enemyWaves.add(new ArrayList<>());
            for (int j = 0; j < numEnemies;){
                int randomEnemyIndex = random.nextInt(0, enemyTemplates.size());
                int randomEnemyPosX = random.nextInt(0, this.getValidTileMatrix()[0].length);
                int randomEnemyPosY = random.nextInt(0, this.getValidTileMatrix().length);
                if (this.getValidTileMatrix()[randomEnemyPosY][randomEnemyPosX] != 0){
                    randomEnemyPosX = randomEnemyPosX * Constants.TILE_SIZE + minX;
                    randomEnemyPosY = randomEnemyPosY * Constants.TILE_SIZE + minY;
                    this.enemyWaves.get(i).add(enemyTemplates.get(randomEnemyIndex).clone(randomEnemyPosX, randomEnemyPosY));
                    j++;
                }
            }
        }
    }

    public void nextWave(){
        if (enemyWaves.get(this.currentWave).isEmpty()){
            this.currentWave += 1;
            if (this.enemyWaves.size() == this.currentWave){
                this.hasWaves = false;
            }
        }
    }

    public void startWaves(GameEntity player){
       if (this.isInside(player.getPosition())) {
           this.startWaves = true;
       }
    }

    public ArrayList<Enemy> getCurrentWave(){
        if (this.startWaves && this.hasWaves){
            return enemyWaves.get(this.currentWave);
        }
        else {
            return new ArrayList<>();
        }
    }

    public boolean hasWaves(){
        return this.hasWaves;
    }

    public boolean killEnemies(){
        boolean removed = false;
        if (this.hasWaves)
            removed = this.enemyWaves.get(this.currentWave).removeIf(Enemy::isDead);
        return removed;
    }

    public void drawDoors(Graphics2D g2d, GameEntity player) {

        if (this.getActiveDoors().size() > 0) {
            for (Door door : this.getDoors()) {
                door.draw(g2d, player);
            }
        }
    }

    public void drawSpawnable(Graphics2D g2d, GameEntity player){
        for (int i = 0; i < this.getValidTileMatrix()[0].length; i++) {
            for (int j = 0; j < this.getValidTileMatrix().length; j++) {
                if (this.getValidTileMatrix()[j][i] != 0) {
                    Hitbox temp = new Hitbox(Constants.TILE_SIZE, Constants.TILE_SIZE,
                            new Vector(
                                    (this.getX() + i - (float) this.getValidTileMatrix()[0].length / 2) * Constants.TILE_SIZE + (float) Constants.TILE_SIZE / 2,
                                    (this.getY() + j - (float) this.getValidTileMatrix().length / 2) * Constants.TILE_SIZE + (float) Constants.TILE_SIZE / 2
                            ));
                    temp.draw(g2d, player);
                }
            }
        }
    }

    public ArrayList<ArrayList<Enemy>> getEnemyWaves() {
        return enemyWaves;
    }

    public ArrayList<Door> getActiveDoors(){
        if (this.hasWaves && this.startWaves){
            return this.getDoors();
        }
        return new ArrayList<>();
    }

    public int getCurrentWaveNumber () {
        return this.currentWave;
    }
}
