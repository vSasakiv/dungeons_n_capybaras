package tile.dungeon;

import java.util.ArrayList;

/**
 * Interface dos fábricas que criam espaços relacionados às dungeons
 */
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
