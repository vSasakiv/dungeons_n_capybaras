package tile;

import game_entity.Player;
import game_entity.Vector;
import java.util.ArrayList;

public abstract class ChangeTileStrategy {
    ArrayList<int[]> regions;

    public abstract int changeMap (Player player, int mapNum);

    public int changePosition (Vector position) {
        for (int i = 0; i < regions.size(); i++) {
            if (position.x >= regions.get(i)[0] && position.x <= regions.get(i)[1] && position.y >= regions.get(i)[2] && position.y <= regions.get(i)[3])
                return i;
        }
        return -1;
    }
}


