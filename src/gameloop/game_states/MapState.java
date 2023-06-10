package gameloop.game_states;

import game_entity.MapPlayer;
import game_entity.MapPlayerStateEnum;
import game_entity.Vector;
import gameloop.Constants;
import gameloop.KeyHandler;
import tile.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * CLasse que cuida do state no mapa geral do jogo
 */
public class MapState implements State{
    private final MapPlayer mapPlayer;
    public ArrayList<TileManager> maps;
    private final KeyHandler keyHandler;
    private int mapNum = 0;
    private MapPlayerStateEnum currentState;

    public MapState(KeyHandler keyHandler) {
        maps = new ArrayList<>();
        this.mapPlayer = new MapPlayer(200, 200, 3);
        maps.add(new TileManager("/src/resources/maps/estacionamento/estacionamento.xml", mapPlayer, new EstacionamentoStrategy()));
        maps.add(new TileManager("/src/resources/maps/BienioSup/BienioSup.xml", mapPlayer, new BienioSupStrategy()));
        this.currentState = MapPlayerStateEnum.DEFAULT;
        this.keyHandler = keyHandler;
    }

    @Override
    public void tick() {
        mapPlayer.tick(keyHandler);
        Layer layer = maps.get(mapNum).getCollisionLayer();
        layer.collisiondetector(mapPlayer);
        mapNum = this.maps.get(mapNum).changeStrategy.changeMap(mapPlayer, mapNum);
        updatePlayerState();
        this.mapPlayer.setVelocity(currentState.estadoAtual);
    }

    void updatePlayerState() {
        switch (currentState) {
            case DEFAULT -> {
                if (this.keyHandler.isKeyN()) {
                    this.currentState = MapPlayerStateEnum.NINJA;
                    this.mapPlayer.playerSelected = 1;
                }
            }
            case NINJA -> {
                if (this.keyHandler.isKeyN()) {
                    this.currentState = MapPlayerStateEnum.DEFAULT;
                    this.mapPlayer.playerSelected = 0;
                }
            }
        }
        keyHandler.setKeyN(false);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.mapNum != -1) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
            // exemplo
            g2d.setColor(Color.BLACK);

            this.maps.get(mapNum).draw(g2d);
            this.mapPlayer.draw(g2d);
            g2d.setColor(Color.red);
            this.mapPlayer.getHitbox().draw(g2d, this.mapPlayer);
        }
    }

    public int getMapNum() {
        return mapNum;
    }

    public void setMapNum(int mapNum) {
        this.mapNum = mapNum;
    }

    public void setDefaultPosition(int x, int y) {
        this.mapPlayer.setPosition(new Vector(x, y));
    }

}
