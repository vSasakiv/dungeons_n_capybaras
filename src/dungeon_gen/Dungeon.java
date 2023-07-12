package dungeon_gen;

import game_entity.mobs.EnemyTemplates;

import java.util.ArrayList;

public class Dungeon {
    private final MonsterRoom[] combatRooms = new MonsterRoom[7];
    private ArrayList<int[][]> dungeonTiles;
    private final DungeonGenerator generator = new DungeonGenerator();

    public Dungeon() {}

    public void geraDungeon(String tipo, int size, int maxWaves, int minEnemies, int maxEnemies){
       this.dungeonTiles = generator.generate(tipo, size);
       DungeonRoom[] rooms = generator.getCombatRooms();
       for (int i = 0; i < rooms.length; i++){
           this.combatRooms[i] = new MonsterRoom(
                   rooms[i].getX(),
                   rooms[i].getY(),
                   rooms[i].getWidth(),
                   rooms[i].getHeight(),
                   maxWaves,
                   minEnemies,
                   maxEnemies,
                   EnemyTemplates.getEnemyTemplates(),
                   rooms[i].getValidTileMatrix());
       }
    }

    public MonsterRoom[] getCombatRooms() {
        return combatRooms;
    }

    public ArrayList<int[][]> getDungeonTiles() {
        return dungeonTiles;
    }
}
