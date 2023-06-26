package tile.dungeon;

import java.util.ArrayList;

public interface DungeonAbstractFactory{
    ArrayList<DungeonTile> createRoom();
    DungeonTile createHorizontalCorridor();
    DungeonTile createVerticalCorridor();
    DungeonTile createVerticalUpEntrance();
    DungeonTile createHorizontalRightEntrance();
    DungeonTile createHorizontalLeftEntrance();
    DungeonTile createVerticalDownEntrance();
    int maxLayers();
}
