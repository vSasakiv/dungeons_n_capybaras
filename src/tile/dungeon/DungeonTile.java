package tile.dungeon;

import tile.MapTileManager;

import java.util.ArrayList;

public class DungeonTile {
    private ArrayList<int[][]> mapTileNumbers;

    public DungeonTile (String path) {
        getInfo(path);
    }

    private void getInfo (String path) {
        MapTileManager tile = new MapTileManager(path);
        mapTileNumbers = tile.getMapTileNumbers();
    }

    public ArrayList<int[][]> getMapTileNumbers() {
        return mapTileNumbers;
    }

}

