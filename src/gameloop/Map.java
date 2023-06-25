package gameloop;

import game_entity.GameEntity;
import game_entity.static_entities.CollidableObject;
import tile.MapTileManager;
import tile.TileManager;

import java.awt.*;
import java.util.ArrayList;

public class Map {
    MapTileManager tilemap;
    ArrayList<CollidableObject> objects;

    public Map(MapTileManager tilemap, ArrayList<CollidableObject> objects) {
        this.tilemap = tilemap;
        this.objects = objects;
    }

    public MapTileManager getTilemap() {
        return tilemap;
    }

    public ArrayList<CollidableObject> getObjects() {
        return objects;
    }

    public void draw(Graphics2D g2d, GameEntity player){
        tilemap.draw(g2d);
        for (CollidableObject object : objects)
            object.draw(g2d, player);
    }
}
