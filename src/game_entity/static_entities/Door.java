package game_entity.static_entities;

import game_entity.GameEntity;

import gameloop.Constants;
import tile.AnimationSprite;
import java.awt.*;

/**
 * Classe para representar uma porta
 */
public class Door extends CollidableObject {
    public AnimationSprite animation;
    int width; // largura da hitbox da porta
    int height; // altura da hitbox da porta
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    private int spriteNumber = 0;

    /**
     * @param worldPosX posição x do centro da porta
     * @param worldPosY posição y do centro da porta
     * @param width largura da porta
     * @param height altura da porta
     */
    public Door(float worldPosX, float worldPosY, int width, int height) {
        super(worldPosX, worldPosY, width, height);
        this.width = width;
        this.height = height;
        loadImage();
    }

    /**
     * Desenha as portas com base na posição de uma entidade
     * @param g2d    Graphics2D java
     * @param player Entidade de referência
     */
    @Override
    public void draw(Graphics2D g2d, GameEntity player){
        spriteCounterUpdate();
        this.hitbox.draw(g2d, player);
        //Portas horizontais
        if (this.width >= this.height) {
            for (int i = 1; i < width/Constants.TILE_SIZE - 1; i++) {
                g2d.drawImage(
                        animation.getSpriteArray()[spriteNumber],
                        (int)((getWorldPosX() - width/2) - player.getWorldPosX() + Constants.WIDTH / 2 + Constants.TILE_SIZE * i),
                        (int)(getWorldPosY() - player.getWorldPosY() + Constants.HEIGHT / 2 - Constants.TILE_SIZE / 2),
                        animation.getSpriteArray()[spriteNumber].getWidth() * 3,
                        animation.getSpriteArray()[spriteNumber].getHeight() * 3,
                        null
                );
            }
        } else { //Portas verticais
            for (int j = 2; j < height / Constants.TILE_SIZE - 1; j++) {
                g2d.drawImage(
                        animation.getSpriteArray()[spriteNumber],
                        (int) (getWorldPosX() - player.getWorldPosX() + Constants.WIDTH / 2  - Constants.TILE_SIZE / 2),
                        (int) ((getWorldPosY() - height / 2) - player.getWorldPosY() + Constants.HEIGHT / 2 + Constants.TILE_SIZE * j),
                        animation.getSpriteArray()[spriteNumber].getWidth() * 3,
                        animation.getSpriteArray()[spriteNumber].getHeight() * 3,
                        null
                );
            }
        }

    }

    /**
     * Carrega sprites das portas
     */
    private void loadImage () {
        try {
            int width = 16;
            int height = 16;
            this.animation = new AnimationSprite("/resources/dungeons/peaks.png", width, height, 0, 0, 4);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Contador para os sprites das portas, para fins de animação
     */
    public void spriteCounterUpdate() {
        /*
         * Quando o contador spriteCounter atinge certo valor, ele atualiza o spriteNumber,
         * alternando entre 0 e 1 (indica dois sprites diferentes)
         */
        spriteCounter++;
        if (spriteCounter >= 20) {
            spriteNumber = (spriteNumber + 1) % 4;
            spriteCounter = 0;
        }
    }

}
