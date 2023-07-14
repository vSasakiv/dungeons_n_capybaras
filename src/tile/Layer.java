package tile;

import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;
import game_entity.static_entities.CollidableTile;
import game_entity.weapons.projectiles.Projectile;
import gameloop.Constants;
import java.awt.image.BufferedImage;

public class Layer {
    //Matriz com as imagens carregadas de uma layer que forma um mapa
    private final BufferedImage[][] tileMap;
    private CollidableTile[][] collisionLayer = null;
    private boolean collision;

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

    public void collisionDetector(GameEntity entity, Hitbox hitbox) {
        if (collision) {
            float oldPosY = entity.getPosition().y;
            float oldPosX = entity.getPosition().x;
            float newPosY = entity.getPosition().y;
            float newPosX = entity.getPosition().x;

            int tileX = (int) (entity.getWorldPosX() / Constants.TILE_SIZE);
            int tileY = (int) (entity.getWorldPosY() / Constants.TILE_SIZE);

            for (int j = tileX - 1; j <= tileX + 1; j++){
                for (int i = tileY -  1; i <= tileY + 1; i++) {
                    if (this.collisionLayer[i][j] != null && this.collisionLayer[i][j].hitbox.isHitting(hitbox)) {
                        entity.setPosition(new Vector(oldPosX, oldPosY));
                        this.collisionLayer[i][j].checkCollision(entity, hitbox);
                        if (entity.getPosition().y != oldPosY) newPosY = entity.getPosition().y;
                        if (entity.getPosition().x != oldPosX) newPosX = entity.getPosition().x;
                    }
                }
            }
            entity.setPosition(new Vector(newPosX, newPosY));
        }
    }

    public void collisionDetectorProjectile(Projectile projectile){
        if (collision) {
            int tileX = (int) (projectile.getWorldPosX() / Constants.TILE_SIZE);
            int tileY = (int) (projectile.getWorldPosY() / Constants.TILE_SIZE);
            for (int j = tileX - 1; j <= tileX + 1; j++) {
                for (int i = tileY - 1; i <= tileY + 1; i++) {
                    if (this.collisionLayer[i][j] != null && projectile.getHitbox().isHitting(this.collisionLayer[i][j].hitbox)) {
                        projectile.setCollided(true);
                    }
                }
            }
        }
    }

    private void changeCollisionLayer () {
        if (collision) {
            collisionLayer = new CollidableTile[tileMap.length][tileMap[0].length];
            for (int i = 0; i < tileMap.length; i++) {
                for (int j = 0; j < tileMap[0].length; j++) {
                    if (tileMap[i][j] != null) {
                        collisionLayer[i][j] = new CollidableTile(
                                j * Constants.TILE_SIZE + (float) Constants.TILE_SIZE /2,
                                i * Constants.TILE_SIZE + (float) Constants.TILE_SIZE /2,
                                Constants.TILE_SIZE,
                                Constants.TILE_SIZE
                                );
                    }
                }
            }
        } else
            collisionLayer = null;
    }

    public BufferedImage[][] getTileMap() {
        return tileMap;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
        changeCollisionLayer();
    }

    public CollidableTile[][] getCollisionLayer() {
        return collisionLayer;
    }
}
