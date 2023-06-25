package tile.dungeon;

public class EletricaDungeonFactory implements DungeonAbstractFactory{
    @Override
    public DungeonTile createRoom() {
        return new DungeonTile("caminho/para/arquivo/mapa.xml");
    }

    @Override
    public DungeonTile createHorizontalCorridor() {
        return new DungeonTile("caminho/para/arquivo/mapa.xml");
    }

    @Override
    public DungeonTile createVerticalCorridor() {
        return new DungeonTile("caminho/para/arquivo/mapa.xml");
    }


}
