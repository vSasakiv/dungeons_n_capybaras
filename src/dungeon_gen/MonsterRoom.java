package dungeon_gen;

import game_entity.GameEntity;
import game_entity.mobs.Enemy;
import gameloop.Constants;

import java.util.ArrayList;
import java.util.Random;

public class MonsterRoom extends DungeonRoom {

    private final Random random = new Random();
    private int currentWave = 0;
    private boolean hasWaves = true;
    private boolean startWaves = false;
    private final ArrayList<ArrayList<Enemy>> enemyWaves;
    public MonsterRoom(int x, int y, int width, int height, int enemyWaves, int minEnemies, int maxEnemies, ArrayList<Enemy> enemyTemplates, int[][] validTileMatrix) {
        super(x, y, width, height, validTileMatrix);
        this.enemyWaves = new ArrayList<>();
        generateEnemies(minEnemies, maxEnemies, enemyWaves, enemyTemplates);
    }

    private void generateEnemies(int minEnemies, int maxEnemies, int enemyWaves, ArrayList<Enemy> enemyTemplates){
        int numEnemies;
        int maxY = this.getMaxY();
        int minY = this.getMinY();
        int maxX = this.getMaxX();
        int minX = this.getMinX();
        for (int i = 0; i < enemyWaves; i++){
            numEnemies = random.nextInt(minEnemies, maxEnemies+1);
            this.enemyWaves.add(new ArrayList<>());
            for (int j = 0; j < numEnemies;){
                int randomEnemyIndex = random.nextInt(0, enemyTemplates.size());
                int randomEnemyPosX = random.nextInt(0, this.getValidTileMatrix().length);
                int randomEnemyPosY = random.nextInt(0, this.getValidTileMatrix()[0].length);
                if (this.getValidTileMatrix()[randomEnemyPosX][randomEnemyPosY] != 0){
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
    public void killEnemies(){
        if (this.hasWaves)
            this.enemyWaves.get(this.currentWave).removeIf(Enemy::isDead);
    }
}
