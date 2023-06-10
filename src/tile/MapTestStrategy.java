package tile;

import game_entity.GameEntity;
import gameloop.Constants;

import java.util.ArrayList;

public class MapTestStrategy extends ChangeTileStrategy{

    public MapTestStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 19, Constants.TILE_SIZE * 28, Constants.TILE_SIZE * 48, Constants.TILE_SIZE * 49});

    }

    public int changeMap (GameEntity player, int mapNum) {
        int index = changePosition(player.getPosition());
        switch (index) {
            case 0 -> {
                return -1;
            }
            case -1 -> {
                return 0;
            }
        }
        return mapNum;
    }

}
