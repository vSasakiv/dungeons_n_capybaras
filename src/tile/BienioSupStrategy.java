package tile;

import game_entity.Player;
import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class BienioSupStrategy extends ChangeTileStrategy{
    public BienioSupStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 33, Constants.TILE_SIZE * 42 , Constants.TILE_SIZE , Constants.TILE_SIZE * 2});

    }
    public int changeMap (Player player, int mapNum) {
        int index = changePosition(player.getPosition());
        if (index != -1) {
            player.setPosition(new Vector(6 * Constants.TILE_SIZE, 47 * Constants.TILE_SIZE));
            return 0;
        }
        return mapNum;
    }
}
