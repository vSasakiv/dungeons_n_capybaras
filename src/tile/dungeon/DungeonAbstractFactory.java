package tile.dungeon;

public interface DungeonAbstractFactory{
    DungeonTile createRoom();
    DungeonTile createHorizontalCorridor();
    DungeonTile createVerticalCorridor();
}
