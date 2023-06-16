package src.gameloop.game_states;

import java.awt.*;

/**
 * Interface para representar métodos esperados de um estado
 */
public interface State {
    void tick();
    void draw(Graphics2D g2d);
    int getMapNum();
    void setMapNum(int mapNum);
    void setDefaultPosition(int x, int y);
}
