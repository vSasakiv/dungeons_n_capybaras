package tile;

import java.awt.*;

/**
 * Interface para os gerenciadores de mapas
 */
public interface TileManager {
    void draw (Graphics2D g2d);
    Layer getCollisionLayer ();

}
