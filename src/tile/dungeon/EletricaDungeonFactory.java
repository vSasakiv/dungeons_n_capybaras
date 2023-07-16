package tile.dungeon;

import java.util.ArrayList;

public class EletricaDungeonFactory implements DungeonAbstractFactory{
    @Override
    public ArrayList<DungeonTile> createRoom() {
        ArrayList<DungeonTile> rooms = new ArrayList<>();
        rooms.add(new DungeonTile("/src/resources/dungeons/eletrica/EletricaStart.xml"));
        rooms.add(new DungeonTile("/src/resources/dungeons/eletrica/EletricaRoom_1.xml"));
        rooms.add(new DungeonTile("/src/resources/dungeons/eletrica/EletricaRoom_2.xml"));
        rooms.add(new DungeonTile("/src/resources/dungeons/eletrica/EletricaBossRoom.xml"));
        return rooms;
    }

    @Override
    public DungeonTile createHorizontalCorridor() {
        return new DungeonTile("/src/resources/dungeons/eletrica/EletricaHorizontalCorridor.xml");
    }

    @Override
    public DungeonTile createVerticalCorridor() {
        return new DungeonTile("/src/resources/dungeons/eletrica/EletricaVerticalCorridor.xml");
    }

    @Override
    public DungeonTile createVerticalUpEntrance() {
        return new DungeonTile("/src/resources/dungeons/eletrica/EletricaVerticalCorridorUpEntrance.xml");
    }

    @Override
    public DungeonTile createHorizontalRightEntrance() {
        return new DungeonTile("/src/resources/dungeons/eletrica/EletricaHorizontalCorridorRightEntrance.xml");
    }

    @Override
    public DungeonTile createHorizontalLeftEntrance() {
        return new DungeonTile("/src/resources/dungeons/eletrica/EletricaHorizontalCorridorLeftEntrance.xml");
    }

    @Override
    public DungeonTile createVerticalDownEntrance() {
        return new DungeonTile("/src/resources/dungeons/eletrica/EletricaVerticalCorridorDownEntrance.xml");
    }

    @Override
    public int maxLayers() {
        return 7;
    }
}
