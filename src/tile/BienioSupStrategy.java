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
        regions.add(new int[]{Constants.TILE_SIZE * 33, Constants.TILE_SIZE * 42 , Constants.TILE_SIZE , Constants.TILE_SIZE * 2}); //BienioSup --> Estacionamento
        regions.add(new int[]{Constants.TILE_SIZE * 10, Constants.TILE_SIZE * 19 , Constants.TILE_SIZE * 49, Constants.TILE_SIZE * 50}); //BienioSup --> BienioDungeon
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
            //BienioSup --> Estacionamento
            case 0 -> {
                player.setPosition(new Vector(Constants.TILE_SIZE * 6, Constants.TILE_SIZE * 47));
                return 0;
            }
            //BienioSup --> BienioDungeon
            case 1 -> {
                return -1;
            }
        }
        return mapNum;
    }
}
