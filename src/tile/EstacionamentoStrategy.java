package tile;

import game_entity.Player;
import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class EstacionamentoStrategy extends ChangeTileStrategy {

    public EstacionamentoStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 39, Constants.TILE_SIZE * 46, Constants.TILE_SIZE, Constants.TILE_SIZE * 2});
        regions.add(new int[]{Constants.TILE_SIZE * 3, Constants.TILE_SIZE * 8, Constants.TILE_SIZE * 48, Constants.TILE_SIZE * 49});

    }


    public int changeMap (Player player, int mapNum) {
        int index = changePosition(player.getPosition());
        if (index != -1) {
            switch (index) {
                case 0 -> {
                    player.setPosition(new Vector(Constants.TILE_SIZE * 23, Constants.TILE_SIZE * 47));
                    return 1;
                }
                case 1 -> {
                    player.setPosition(new Vector(Constants.TILE_SIZE * 39, Constants.TILE_SIZE * 3));
                    return 2;
                }
            }

        }
        return mapNum;
    }
}
