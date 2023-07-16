package tile.dungeon;

import java.util.Objects;

/**
 * Fábrica que retorna fábricas de dungeons.
 */
public class DungeonFactoryProvider {
    public static DungeonAbstractFactory getFactory (String type) {
        if (Objects.equals(type, "bienio")) return new BienioDungeonFactory();
        else if (Objects.equals(type, "eletrica")) return new EletricaDungeonFactory();
        return null;
    }
}
