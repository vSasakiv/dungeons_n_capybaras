package tile.dungeon;

import tile.MapTileManager;

import java.util.ArrayList;

/**
 * Classe que representa o espaço de uma dungeon
 */
public class DungeonTile {
    private ArrayList<int[][]> mapTileNumbers; //Matriz dos números dos tiles do mapa

    public DungeonTile (String path) {
        getInfo(path);
    }

    /**
     * Recupera a matriz dos números de tiles a partir de um arquivo xml
     * @param path caminho para o arquivo xml
     */
    private void getInfo (String path) {
        MapTileManager tile = new MapTileManager(path);
        mapTileNumbers = tile.getMapTileNumbers();
    }

    public ArrayList<int[][]> getMapTileNumbers() {
        return mapTileNumbers;
    }

}

