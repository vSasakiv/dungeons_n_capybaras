package tile.dungeon;

public class BienioDungeonFactory implements DungeonAbstractFactory {
    @Override
    public DungeonTile createRoom() {
        return new DungeonTile("/src/resources/dungeons/bienio/BienioRoom_1.xml");
    }

    @Override
    public DungeonTile createHorizontalCorridor() {
        return new DungeonTile("/src/resources/dungeons/bienio/BienioCorredorHorizontal_1.xml");
    }

    @Override
    public DungeonTile createVerticalCorridor() {
        return new DungeonTile("/src/resources/dungeons/bienio/BienioCorredorVertical_2.xml");
    }

}
