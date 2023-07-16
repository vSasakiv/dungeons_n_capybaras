package gameloop;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.npcs.MovableNpc;
import game_entity.static_entities.CollidableObject;
import tile.MapTileManager;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    private MapTileManager tilemap;
    ArrayList<CollidableObject> objects = new ArrayList<>();
    ArrayList<MovableNpc> npcs = new ArrayList<>();

    public Map(MapTileManager tilemap) {
        this.tilemap = tilemap;
    }

    public void addCollidable(CollidableObject object){
        this.objects.add(object);
    }

    public void addNpc(MovableNpc npc){
        this.npcs.add(npc);
    }

    public void tick(Vector playerPos){
        for (MovableNpc npc: this.npcs)
            npc.tick(playerPos);
    }

    public MapTileManager getTilemap() {
        return tilemap;
    }

    public ArrayList<CollidableObject> getObjects() {
        return objects;
    }
    public ArrayList<MovableNpc> getNpcs() {return npcs; }

    public void draw(Graphics2D g2d, GameEntity player){
        tilemap.draw(g2d);
        for (CollidableObject object : objects)
            object.draw(g2d, player);
        for (MovableNpc npc: npcs)
            npc.draw(g2d, player);
    }
}
