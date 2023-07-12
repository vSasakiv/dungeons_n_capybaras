package tile;

import java.awt.*;

public interface TileManager {
    void draw (Graphics2D g2d);
    Layer getCollisionLayer ();
    ChangeTileStrategy getChangeStrategy();

}
