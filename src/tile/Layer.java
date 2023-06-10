package tile;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;
import java.awt.image.BufferedImage;

public class Layer {
    private Tile[][] tileMap;
    private boolean collision;
    private String name;

    public Layer(String[] data, int width, int height, int fistGrid, Sprite sprite, String name) {
        this.tileMap = loadLayer(data, width, height, fistGrid, sprite);
        this.collision = false;
        this.name = name;
    }

    private Tile[][] loadLayer (String[] data, int width, int height, int fistGrid, Sprite sprite) {
        Tile[][] layer = new Tile[height][width];
        BufferedImage[] tiles = loadTiles(sprite);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Integer.parseInt(data[i * width + j]) != 0)
                    layer[i][j] = new Tile(tiles[Integer.parseInt(data[i * width + j]) - fistGrid]);
            }
        }
        return layer;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }


    /**
     * Carrega vetor de tiles para dado spritesheet
     */
    private BufferedImage[] loadTiles (Sprite sprite) {
        int height = sprite.getSpriteHeight();
        int width = sprite.getSpriteWidth();

        BufferedImage[] tiles = new BufferedImage[height * width];
        //O tile número "x" estará na posição x do vetor
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                tiles[i * width + j] = sprite.getSprite(i, j);
            }
        }
        return tiles;
    }

    public void collisiondetector (GameEntity entity) {
        int tileX = (int) (entity.getWorldPosX() / Constants.TILE_SIZE);
        int tileY = (int) (entity.getWorldPosY() / Constants.TILE_SIZE);
        for (int j = tileX - 1; j <= tileX + 1; j++){
            for (int i = tileY -  1; i <= tileY + 1; i++) {
                checkCollision(i, j, entity);
            }
        }
    }

    private void checkCollision (int tileX, int tileY, GameEntity entity) {
        int difX = (int) Math.abs(tileY * Constants.TILE_SIZE + Constants.TILE_SIZE /2.0 - entity.getWorldPosX());
        int difY = (int) Math.abs(tileX * Constants.TILE_SIZE + Constants.TILE_SIZE /2.0 - entity.getWorldPosY());
        if (tileMap[tileX][tileY] != null) {
            if (difX <= Constants.TILE_SIZE &&  difY <= Constants.TILE_SIZE) {
                if (difX < difY) {
                    if (entity.getWorldPosY() <= tileX * Constants.TILE_SIZE + Constants.TILE_SIZE /2.0) {
                        entity.setPosition(Vector.add(entity.getPosition(), Vector.scalarMultiply(Constants.DIRECTION_UP, entity.getVelocity())));
                    } else {
                        entity.setPosition(Vector.add(entity.getPosition(), Vector.scalarMultiply(Constants.DIRECTION_DOWN, entity.getVelocity())));
                    }
                } else if (difX > difY) {
                    if (entity.getWorldPosX() <= tileY * Constants.TILE_SIZE +  Constants.TILE_SIZE /2.0) {
                        entity.setPosition(Vector.add(entity.getPosition(), Vector.scalarMultiply(Constants.DIRECTION_LEFT, entity.getVelocity())));
                    } else {
                        entity.setPosition(Vector.add(entity.getPosition(), Vector.scalarMultiply(Constants.DIRECTION_RIGHT, entity.getVelocity())));
                    }
                }
            }
        }
    }


    public String getName() {
        return name;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

}
