package dungeon_gen;

import game_entity.mobs.EnemyTemplates;

import java.util.ArrayList;

/**
 * Classe para representar uma dungeon completa
 */
public class Dungeon {
    private final MonsterRoom[] combatRooms = new MonsterRoom[7]; // todas as salas de combate da dungeon
    private ArrayList<int[][]> dungeonTiles; // lista de matrizes representando os tiles do mapa da dungeon
    private final DungeonGenerator generator = new DungeonGenerator(); // gerador de dungeon

    public Dungeon() {}

    /**
     * @param tipo tipo de dungeon a ser gerado, deve ser "eletrica" ou "bienio"
     * @param size tamanho da dungeon a ser gerada,
     * @param maxWaves número máximo de ondas por sala de combate
     * @param minEnemies número mínimo de inimigos por onda na sala de combate
     * @param maxEnemies número máximo de inimigos por onda na sala de combate
     */
    public void geraDungeon(String tipo, int size, int maxWaves, int minEnemies, int maxEnemies){
        // gera a dungeon utilizando o generator
        this.dungeonTiles = generator.generate(tipo, size);
        DungeonRoom[] rooms = generator.getCombatRooms();
        // gera cada sala de combate
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
                   rooms[i].getValidTileMatrix(),
                   rooms[i].getDoors());
        }
    }

    public MonsterRoom[] getCombatRooms() {
        return combatRooms;
    }

    public ArrayList<int[][]> getDungeonTiles() {
        return dungeonTiles;
    }
}
