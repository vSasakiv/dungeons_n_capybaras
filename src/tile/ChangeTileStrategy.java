package src.tile;


import src.game_entity.GameEntity;
import src.game_entity.Vector;
import java.util.ArrayList;

public abstract class ChangeTileStrategy {

    /**
     * Guarda as regiões em que deve haver uma mudança de mapa
     * As regiões são guardadas em vetores no formato {minX, maxX, minY, maxY}
     * nos quais min e max indicam, respectivamente, as coordenadas mínimas e máximas de X e Y.
     */
    ArrayList<int[]> regions;

    public abstract int changeMap (GameEntity player, int mapNum);

    /**
     * Verifica se foi atingida, por uma entidade, uma região de mudança de mapa
     * @param position Posição da entidade
     * @return Parâmetro que indica qual das regiões foi atingida, ou se nenhuma foi (return -1)
     */
    public int changePosition (Vector position) {
        for (int i = 0; i < regions.size(); i++) {
            if (position.x >= regions.get(i)[0] && position.x <= regions.get(i)[1] && position.y >= regions.get(i)[2] && position.y <= regions.get(i)[3])
                return i;
        }
        return -1;
    }
}


