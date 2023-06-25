package gameloop.game_states;

import game_entity.MapPlayer;
import game_entity.MapPlayerStateEnum;
import game_entity.Vector;
import game_entity.static_entities.CollidableObject;
import game_entity.static_entities.Door;
import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.Map;
import tile.*;

import java.awt.*;
import java.util.ArrayList;

/**
 * CLasse que cuida do state no mapa geral do jogo
 */
public class MapState implements State{
    private final MapPlayer mapPlayer;
    public ArrayList<Map> maps;
    private final KeyHandler keyHandler;
    private int mapNum = 0;
    private final MapPlayerStateEnum currentState;

    public MapState(KeyHandler keyHandler) {
        maps = new ArrayList<>();
        this.mapPlayer = new MapPlayer(200, 200, 3);
        MapTileManager estacionamentoMap = new MapTileManager(
                "/src/resources/maps/estacionamento/estacionamento.xml",
                mapPlayer,
                new EstacionamentoStrategy());
        ArrayList<CollidableObject> estacionamentoObjects = new ArrayList<>();
        CollidableObject randomDoor = new Door(275, 566, 50 ,50);
        estacionamentoObjects.add(randomDoor);
        maps.add(new Map(estacionamentoMap, estacionamentoObjects));

        MapTileManager bienioSupMap = new MapTileManager(
                "/src/resources/maps/BienioSup/BienioSup.xml",
                mapPlayer,
                new BienioSupStrategy());
        ArrayList<CollidableObject> bienioSupObjects = new ArrayList<>();
        CollidableObject randomDoor2 = new Door(1361, 1243, 50 ,50);

        bienioSupObjects.add(randomDoor2);
        maps.add(new Map(bienioSupMap, bienioSupObjects));
        this.currentState = MapPlayerStateEnum.DEFAULT;
        this.keyHandler = keyHandler;
    }

    @Override
    public void tick() {
        mapPlayer.tick(keyHandler);
        Layer layer = maps.get(mapNum).getTilemap().getCollisionLayer();
        layer.collisionDetector(mapPlayer);
        mapNum = this.maps.get(mapNum).getTilemap().changeStrategy.changeMap(mapPlayer, mapNum);
        this.mapPlayer.setVelocity(currentState.estadoAtual);
    }

    @Override
    public void draw(Graphics2D g2d) {
        if (this.mapNum != -1) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
            // exemplo
            g2d.setColor(Color.BLACK);

            this.maps.get(mapNum).draw(g2d, this.mapPlayer);
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
