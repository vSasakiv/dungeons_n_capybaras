package tile.dungeon;

import java.util.ArrayList;

public interface DungeonAbstractFactory{
    ArrayList<DungeonTile> createRoom();
    DungeonTile createHorizontalCorridor();
    DungeonTile createVerticalCorridor();

    int maxLayers();
}
