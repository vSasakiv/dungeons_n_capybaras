package tile;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;

import java.util.ArrayList;

public class EstacionamentoStrategy extends ChangeTileStrategy {

    /**
     * Estratégia de mudança de mapas para o mapa "Estacionamento"
     */
    public EstacionamentoStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 3, Constants.TILE_SIZE * 8, Constants.TILE_SIZE * 48, Constants.TILE_SIZE * 49});
    }

    /**
     * Algoritmo para mudança de mapa
     * @param player Entidade que atinge região de mudança
     * @param mapNum Número do mapa atual
     * @return Número do próximo mapa, ou parâmetro para mudar de mapa (se == -1)
     */
    public int changeMap (GameEntity player, int mapNum) {
        int index = changePosition(player.getPosition());
        if (index == 0) {
            player.setPosition(new Vector(Constants.TILE_SIZE * 39, Constants.TILE_SIZE * 3));
            return 1;
        }
        return mapNum;
    }
}
