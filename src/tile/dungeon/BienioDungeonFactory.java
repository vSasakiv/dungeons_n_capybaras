package tile.dungeon;

import java.util.ArrayList;

public class BienioDungeonFactory implements DungeonAbstractFactory {
    @Override
    public ArrayList<DungeonTile> createRoom() {
        ArrayList<DungeonTile> rooms = new ArrayList<>();
        rooms.add(new DungeonTile("/src/resources/dungeons/bienio/BienioRoom_1.xml"));
        return rooms;
    }

    @Override
    public DungeonTile createHorizontalCorridor() {
        return new DungeonTile("/src/resources/dungeons/bienio/BienioCorredorHorizontal_1.xml");
    }

    @Override
    public DungeonTile createVerticalCorridor() {
        return new DungeonTile("/src/resources/dungeons/bienio/BienioCorredorVertical_2.xml");
    }

    @Override
    public int maxLayers() {
        return 7;
    }
}
