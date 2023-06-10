package gameloop.game_states;

import game_entity.MapPlayer;
import gameloop.Constants;
import gameloop.KeyHandler;
import tile.Layer;
import tile.TileManager;

import java.awt.*;

/**
 * CLasse que cuida do state no mapa geral do jogo
 */
public class MapState implements State{
    private final MapPlayer mapPlayer;
    public final TileManager tileManager;
    private final KeyHandler keyHandler;

    public MapState(KeyHandler keyHandler) {
        this.mapPlayer = new MapPlayer(200, 200, 3);
        this.tileManager = new TileManager("/src/resources/maps/mapaTeste.xml", mapPlayer);
        this.keyHandler = keyHandler;
    }

    @Override
    public void tick() {
        mapPlayer.tick(keyHandler);

        for (Layer l: tileManager.getLayers())
            if (l.getCollision())
                l.collisiondetector(mapPlayer);
    }

    @Override
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        // exemplo
        g2d.setColor(Color.BLACK);

        this.tileManager.draw(g2d);
        this.mapPlayer.draw(g2d);
        g2d.setColor(Color.red);
        this.mapPlayer.getHitbox().draw(g2d, this.mapPlayer);
    }
}
