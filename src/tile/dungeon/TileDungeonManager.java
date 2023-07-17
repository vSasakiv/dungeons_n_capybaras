package tile.dungeon;

import game_entity.GameEntity;
import gameloop.render.Draw;
import gameloop.render.DrawTileManager;
import tile.*;

import java.awt.*;
import java.util.ArrayList;

public class TileDungeonManager implements TileManager {
    Draw drawMethod;
    ArrayList<Layer> layers;
    public ChangeTileStrategy changeStrategy;

    public TileDungeonManager (ArrayList<int[][]> dungeonNumbers, String type, GameEntity player, ChangeTileStrategy changeStrategy) {
        LayerInfo info = LayerInfoProvider.getLayerInfo(type);
        assert info != null;
        int WorldRows = dungeonNumbers.get(0).length;
        int WorldColumns = dungeonNumbers.get(0)[0].length;
        layers = loadLayers(dungeonNumbers, info, WorldRows, WorldColumns);
        drawMethod = new DrawTileManager(layers, WorldRows, WorldColumns, player);
        this.changeStrategy = changeStrategy;
        this.layers.get(this.layers.size() - 1).setCollision(true);
    }

    private ArrayList<Layer> loadLayers (ArrayList<int[][]> dungeonNumbers,  LayerInfo info, int rows, int columns) {
        ArrayList<Sprite> sprites = new ArrayList<>();
        ArrayList<Layer> layers = new ArrayList<>();
        for (int i = 0; i < info.imagePath.length; i++) {
            sprites.add(new Sprite(
                    info.imagePath[i],
                    info.TileWidth,
                    info.TileHeight
                    )
            );
            layers.add(new Layer(
                    dungeonNumbers.get(i),
                    rows,
                    columns,
                    info.fistGid[i],
                    sprites.get(i))
            );
        }
        return layers;
    }

    public void draw (Graphics2D g2d) {
        drawMethod.draw(g2d);
    }

    @Override
    public Layer getCollisionLayer() {
        return this.layers.get(this.layers.size() - 1);
    }

}
