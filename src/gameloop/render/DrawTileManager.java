package gameloop.render;

import game_entity.GameEntity;
import gameloop.Constants;
import tile.Layer;

import java.awt.*;
import java.util.ArrayList;

public class DrawTileManager implements Draw{
    int WorldRolls;
    int WorldColumns;
    GameEntity player;
    public ArrayList<Layer> layers;
    public DrawTileManager ( ArrayList<Layer> layers, int WorldRolls, int WorldColumns, GameEntity player) {
        this.layers = layers;
        this.WorldRolls = WorldRolls;
        this.WorldColumns = WorldColumns;
        this.player = player;
    }

    @Override
    public void draw (Graphics2D g2d) {
        for (int worldRow = 0; worldRow < WorldRolls; worldRow++) {
            for (int worldColumn = 0; worldColumn < WorldColumns; worldColumn++) {
                int worldX = worldColumn * Constants.TILE_SIZE;
                int worldY = worldRow * Constants.TILE_SIZE;
                int screenX = worldX - (int)player.getWorldPosX() + Constants.WIDTH/2;
                int screenY = worldY - (int)player.getWorldPosY() + Constants.HEIGHT/2;

                //Somente desenha na tela se a posição do tile estiver dentro dos limites da tela (mais uma borda de tamanho TILE_SIZE)
                if (worldX + Constants.TILE_SIZE > player.getWorldPosX() - (float) Constants.WIDTH /2 &&
                        worldX - Constants.TILE_SIZE < player.getWorldPosX() + (float) Constants.WIDTH /2 &&
                        worldY + Constants.TILE_SIZE > player.getWorldPosY() - (float) Constants.HEIGHT /2 &&
                        worldY - Constants.TILE_SIZE < player.getWorldPosY() + (float) Constants.HEIGHT /2) {
                    for (int i = 0; i < layers.size() - 1; i++) {
                        if (layers.get(i).getTileMap()[worldRow][worldColumn] != null)
                            g2d.drawImage(
                                    layers.get(i).getTileMap()[worldRow][worldColumn],
                                    screenX, screenY,
                                    Constants.TILE_SIZE,
                                    Constants.TILE_SIZE,
                                    null
                            );
                    }
                    if (layers.get(layers.size() - 1).getTileMap()[worldRow][worldColumn] != null) {
                        layers.get(layers.size() - 1).getCollisionLayer()[worldRow][worldColumn].draw(g2d, player);
                    }

                }
            }
        }
    }

}
