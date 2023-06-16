package src.tile;

import src.game_entity.GameEntity;
import src.game_entity.Vector;
import src.gameloop.Constants;

import java.util.ArrayList;

public class EstacionamentoStrategy extends ChangeTileStrategy {

    /**
     * Estratégia de mudança de mapas para o mapa "Estacionamento"
     */
    public EstacionamentoStrategy () {
        regions = new ArrayList<>();
        regions.add(new int[]{Constants.TILE_SIZE * 39, Constants.TILE_SIZE * 46, Constants.TILE_SIZE, Constants.TILE_SIZE * 2});
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
            switch (index) {
                case 0 -> {
                    return -1;
                }
                case 1 -> {
                    player.setPosition(new Vector(Constants.TILE_SIZE * 39, Constants.TILE_SIZE * 3));
                    return 1;
                }
        }
        return mapNum;
    }
}
