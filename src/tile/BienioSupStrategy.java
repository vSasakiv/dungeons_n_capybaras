package tile;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class BienioSupStrategy extends ChangeTileStrategy{

    /**
     * Estratégia de mudança de mapas para o mapa "BienioSup"
     */
    public BienioSupStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 33, Constants.TILE_SIZE * 42 , Constants.TILE_SIZE , Constants.TILE_SIZE * 2});

    }

    /**
     * Algoritmo para mudança de mapa
     * @param player Entidade que atinge região de mudança
     * @param mapNum Número do mapa atual
     * @return Número do próximo mapa, ou parâmetro para mudar de mapa (se == -1)
     */
    public int changeMap (GameEntity player, int mapNum) {
        int index = changePosition(player.getPosition());
        if (index != -1) {
            player.setPosition(new Vector(6 * Constants.TILE_SIZE, 47 * Constants.TILE_SIZE));
            return 0;
        }
        return mapNum;
    }
}
