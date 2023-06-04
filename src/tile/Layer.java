package tile;

import game_entity.Hitbox;
import game_entity.Player;
import game_entity.Vector;
import gameloop.Constants;

import java.awt.image.BufferedImage;
import java.util.Objects;

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

    public void activateCollision(boolean collision) {
        this.collision = collision;

        if (collision) {
            for (int i = 0 ; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[0].length; j++) {
                    if (tileMap[i][j] != null)
                        this.tileMap[i][j].setHitbox( new Hitbox(
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE,
                                new Vector(j* Constants.TILE_SIZE + (float) Constants.TILE_SIZE /2, i * Constants.TILE_SIZE + (float) Constants.TILE_SIZE /2))
                    );
                }
            }
        }
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

    public void collisionDetector (Player player) {
        int tileX = (int) (player.getHitbox().getWorldPosX() / Constants.TILE_SIZE);
        int tileY = (int) (player.getHitbox().getWorldPosY() / Constants.TILE_SIZE );
        int minY = (int) (player.getHitbox().minY() / Constants.TILE_SIZE);
        int maxY = (int) (player.getHitbox().maxY() / Constants.TILE_SIZE);
        int minX = (int) (player.getHitbox().minX() / Constants.TILE_SIZE);
        int maxX = (int) (player.getHitbox().maxX() / Constants.TILE_SIZE);
        String direction = player.getSpriteDirection();

        if (Objects.equals(direction, "UP")) {
            if (tileMap[minY][minX] != null) {
                player.setPositionY(tileMap[minY][minX].getHitbox().maxY() + (float) Constants.TILE_SIZE /2 + 1);
            } else if ( tileMap[minY][maxX] != null){
                player.setPositionY(tileMap[minY][maxX].getHitbox().maxY() + (float) Constants.TILE_SIZE /2 + 1);
            }
        }

        if (Objects.equals(direction, "DOWN")) {
            if (tileMap[maxY][minX] != null) {
                player.setPositionY(tileMap[maxY][minX].getHitbox().minY() - (float) Constants.TILE_SIZE /2 - 1);
            }else if (tileMap[maxY][maxX] != null){
                player.setPositionY(tileMap[maxY][maxX].getHitbox().minY() - (float) Constants.TILE_SIZE /2 - 1);
            }
        }

        if (Objects.equals(direction, "LEFT")) {
            if (tileMap[minY][minX] != null) {
                player.setPositionX(tileMap[minY][minX].getHitbox().maxX() + (float) Constants.TILE_SIZE /2 + 1);
            }else if (tileMap[maxY][minX] != null){
                player.setPositionX(tileMap[maxY][minX].getHitbox().maxX() + (float) Constants.TILE_SIZE /2 + 1);
            }
        }

        if (Objects.equals(direction, "RIGHT")) {
            if (tileMap[minY][maxX] != null) {
                player.setPositionX(tileMap[minY][maxX].getHitbox().minX() - (float) Constants.TILE_SIZE /2 - 1);
            }else if (tileMap[maxY][maxX] != null){
                player.setPositionX(tileMap[maxY][maxX].getHitbox().minX() - (float) Constants.TILE_SIZE /2 - 1);
            }
        }

        if (Objects.equals(direction, "DOWN_RIGHT")) {
            if (tileMap[tileY][maxX] != null) {
                player.setPositionX(tileMap[tileY][maxX].getHitbox().minX() - (float) Constants.TILE_SIZE /2 - 1);
            }
            if (tileMap[maxY][tileX] != null){
                player.setPositionY(tileMap[maxY][tileX].getHitbox().minY() - (float) Constants.TILE_SIZE /2 - 1);

            }
        }

        if (Objects.equals(direction, "DOWN_LEFT")) {
            if (tileMap[tileY][minX] != null) {
                player.setPositionX(tileMap[tileY][minX].getHitbox().maxX() + (float) Constants.TILE_SIZE /2 + 1);
            }
            if (tileMap[maxY][tileX] != null) {
                player.setPositionY(tileMap[maxY][tileX].getHitbox().minY() - (float) Constants.TILE_SIZE /2 + 1);
            }
        }

        if (Objects.equals(direction, "UP_RIGHT")) {
            if (tileMap[tileY][maxX] != null) {
                player.setPositionX(tileMap[tileY][maxX].getHitbox().minX() - (float) Constants.TILE_SIZE /2 + 1);
            }
            if (tileMap[minY][tileX] != null) {
                player.setPositionY(tileMap[minY][tileX].getHitbox().maxY() + (float) Constants.TILE_SIZE /2 + 1);
            }
        }

        if (Objects.equals(direction, "UP_LEFT")) {
            if (tileMap[tileY][minX] != null) {
                player.setPositionX(tileMap[tileY][minX].getHitbox().maxX() + (float) Constants.TILE_SIZE /2 + 1);
            }
            if (tileMap[minY][tileX] != null) {
                player.setPositionY(tileMap[minY][tileX].getHitbox().maxY() + (float) Constants.TILE_SIZE /2 + 1);
            }
        }
        System.out.println("Player :" + tileX + ", " + tileY);
    }
    public String getName() {
        return name;
    }

    public Tile[][] getTileMap() {
        return tileMap;
    }

    public boolean isCollision() {
        return collision;
    }
}
