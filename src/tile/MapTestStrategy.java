package tile;

import game_entity.Player;
import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class MapTestStrategy extends ChangeTileStrategy{

    public MapTestStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 19, Constants.TILE_SIZE * 28, Constants.TILE_SIZE * 48, Constants.TILE_SIZE * 49});

    }

    public int changeMap (Player player, int mapNum) {
        int index = changePosition(player.getPosition());
        if (index != -1) {
            player.setPosition(new Vector (42 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE));
            return 0;
        }
        return mapNum;
    }

}
