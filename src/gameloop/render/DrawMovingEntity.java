package gameloop.render;

import game_entity.GameEntity;
import game_entity.entity_sprites.MovingEntitySprites;
import game_entity.Vector;
import gameloop.Constants;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Objects;

public class DrawMovingEntity implements Draw {
    protected String spriteDirection;
    private final GameEntity entity;
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    protected int spriteNumber = 0;
    protected final ArrayList<MovingEntitySprites> entitySprites;
    protected Vector direction;
    protected int spritesSelect;

    public DrawMovingEntity(GameEntity entity,  ArrayList<MovingEntitySprites> movingEntitySprites) {
        this.entity = entity;
        spritesSelect = 0;
        this.entitySprites = movingEntitySprites;
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = getEntityImage();
        //Desenha o player
        g2d.drawImage(
                playerImage,
                (int)entity.getScreenX(),
                (int)entity.getScreenY(),
                entity.getSpriteSizeX(),
                entity.getSpriteSizeY(),
                null
        );
    }

    /**
     * Atualiza a direção do sprite, com base na movimentação do player
     * @param direction Direção do movimento do player
     */
    public void spriteUpdate(Vector direction) {
        if (Vector.vectorEquals(direction, Constants.DIRECTION_UP)) {
            this.spriteDirection = "UP";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_LEFT)) {
            this.spriteDirection = "LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN)) {
            this.spriteDirection = "DOWN";
        }  else if (Vector.vectorEquals(direction, Constants.DIRECTION_RIGHT)) {
            this.spriteDirection = "RIGHT";
        }  else if (Vector.vectorEquals(direction, Constants.NULL_VECTOR)) {
            if (Objects.equals(spriteDirection, "UP") || Objects.equals(spriteDirection, "STAND_BACK"))
                this.spriteDirection = "STAND_BACK";
            else
                this.spriteDirection = "STAND_FRONT";
        }
    }

    protected BufferedImage getEntityImage() {
        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = entitySprites.get(spritesSelect).standFront;
            case "STAND_BACK" -> playerImage = entitySprites.get(spritesSelect).standBack;
            case "UP" -> playerImage = entitySprites.get(spritesSelect).up.getSpriteArray()[spriteNumber];
            case "DOWN" -> playerImage = entitySprites.get(spritesSelect).down.getSpriteArray()[spriteNumber];
            case "RIGHT" -> playerImage = entitySprites.get(spritesSelect).right.getSpriteArray()[spriteNumber];
            case "LEFT" -> playerImage = entitySprites.get(spritesSelect).left.getSpriteArray()[spriteNumber];
        }
        return playerImage;
    }

    /**
     * Alterna os sprites, para fins de animação
     */
    public void spriteCounterUpdate() {
        /*
         * Quando o contador spriteCounter atinge certo valor, ele atualiza o spriteNumber,
         * alternando entre 0 e 1 (indica dois sprites diferentes)
         */
        spriteCounter++;
        if (spriteCounter >= 10) {
            spriteNumber = (spriteNumber + 1) % 4;
            spriteCounter = 0;
        }
    }

    public void setSpritesSelect(int spritesSelect) {
        this.spritesSelect = spritesSelect;
    }
}
