package tile.dungeon;

import java.util.ArrayList;

public class EletricaDungeonFactory implements DungeonAbstractFactory{
    @Override
    public ArrayList<DungeonTile> createRoom() {
        ArrayList<DungeonTile> rooms = new ArrayList<>();
        rooms.add(new DungeonTile("caminho/pro/mapa.xml"));
        return rooms;
    }

    @Override
    public DungeonTile createHorizontalCorridor() {
        return new DungeonTile("caminho/para/arquivo/mapa.xml");
    }

    @Override
    public DungeonTile createVerticalCorridor() {
        return new DungeonTile("caminho/para/arquivo/mapa.xml");
    }

    @Override
    public int maxLayers() {
        return 7;
    }
}
