package tile;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;
import java.awt.image.BufferedImage;

public class Layer {
    //Matriz com as imagens carregadas de uma layer que forma um mapa
    private final BufferedImage[][] tileMap;

    /**
     * Layer que forma um mapa
     * @param data Array com números dos sprites em suas posições
     * @param width Comprimento, em quantidade de tiles, da layer
     * @param height Largura, em quantidade de tiles, da layer
     * @param fistGrid Número de identificação, no "data", do primeiro sprite
     * @param sprite Spritesheet contendo imagens de todos os tiles
     */
    public Layer(String[] data, int width, int height, int fistGrid, Sprite sprite) {
        this.tileMap = loadLayer(data, width, height, fistGrid, sprite);
    }
    public Layer(int[][] data, int width, int height, int fistGid, Sprite sprite) {
        this.tileMap = loadLayer(data, width, height, fistGid, sprite);
    }

    /**
     * Método responsável por carregar a matriz de imagens
     * @param data Array com números dos sprites em suas posições
     * @param width Comprimento, em quantidade de tiles, da layer
     * @param height Largura, em quantidade de tiles, da layer
     * @param fistGrid Número de identificação, no "data", do primeiro sprite
     * @param sprite Spritesheet contendo imagens de todos os tiles
     * @return Matriz de imagens da layer
     */
    private BufferedImage[][] loadLayer (String[] data, int width, int height, int fistGrid, Sprite sprite) {
        BufferedImage[][] layer = new BufferedImage[height][width];
        BufferedImage[] tiles = loadTiles(sprite);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (Integer.parseInt(data[i * width + j]) != 0)
                    layer[i][j] = tiles[Integer.parseInt(data[i * width + j]) - fistGrid];
            }
        }
        return layer;
    }

    private BufferedImage[][] loadLayer (int[][] data, int width, int height, int fistGrid, Sprite sprite) {
        BufferedImage[][] layer = new BufferedImage[height][width];
        BufferedImage[] tiles = loadTiles(sprite);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (data[i][j] != 0)
                    layer[i][j] = tiles[data[i][j] - fistGrid];
            }
        }
        return layer;
    }

    /**
     * Carrega um vetor com todos os sprites de um Spritesheet
     * @param sprite Spritesheet
     * @return vetor com cada sprite
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

    /**
     * Para os tiles em volta da entidade, verifica se há colisão
     * @param entity Entidade
     */
    public void collisionDetector(GameEntity entity) {
        int tileX = (int) (entity.getWorldPosX() / Constants.TILE_SIZE);
        int tileY = (int) (entity.getWorldPosY() / Constants.TILE_SIZE);
        for (int j = tileX - 1; j <= tileX + 1; j++){
            for (int i = tileY -  1; i <= tileY + 1; i++) {
                checkCollision(i, j, entity);
            }
        }
    }

    /**
     * Aplica lógica de colisão para corrigir a posição da entidade caso ela colida com um tile sólido
     * @param tileX Posição X do tile
     * @param tileY Posição Y do tile
     * @param entity Entidade
     */
    private void checkCollision (int tileX, int tileY, GameEntity entity) {
        int difX = (int) Math.abs(tileY * Constants.TILE_SIZE + Constants.TILE_SIZE /2.0 - entity.getWorldPosX());
        int difY = (int) Math.abs(tileX * Constants.TILE_SIZE + Constants.TILE_SIZE /2.0 - entity.getWorldPosY());
        System.out.println("TileX: " + tileX + "TileY: " + tileY);
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

    public BufferedImage[][] getTileMap() {
        return tileMap;
    }

}
