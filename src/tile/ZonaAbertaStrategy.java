package tile;

import game_entity.GameEntity;
import gameloop.Constants;

import java.util.ArrayList;

public class ZonaAbertaStrategy extends ChangeTileStrategy{

    /**
     * Estratégia de mudança de mapas para o mapa "ZonaAberta"
     */
    public ZonaAbertaStrategy() {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 19, Constants.TILE_SIZE * 28, Constants.TILE_SIZE * 49, Constants.TILE_SIZE * 49});

    }

    /**
     * Algoritmo para mudança de mapa
     * @param player Entidade que atinge região de mudança
     * @param mapNum Número do mapa atual
     * @return Número do próximo mapa, ou parâmetro para mudar de mapa (se == -1)
     */
    public int changeMap (GameEntity player, int mapNum) {
        int index = changePosition(player.getPosition());
        System.out.println(index);
        if (index == 0) {
            return -1;
        }
        return mapNum;
    }

}
