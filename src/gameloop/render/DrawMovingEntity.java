package gameloop.render;

import game_entity.GameEntity;
import game_entity.entity_sprites.MovingEntitySprites;
import game_entity.Vector;
import gameloop.Constants;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Objects;

public class DrawMovingEntity implements Draw {

    private String spriteDirection;
    GameEntity entity;
    private int spriteCounter = 0; // Conta quantos sprites foram renderizados
    private int spriteNumber = 0;
    MovingEntitySprites entitySprite;

    public DrawMovingEntity(GameEntity entity, MovingEntitySprites movingEntitySprites) {
        this.entity = entity;
        this.entitySprite = movingEntitySprites;
    }

    @Override
    public void draw(Graphics2D g2d) {
        BufferedImage playerImage = getPlayerImage(entitySprite);
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

    public void spriteUpdate(Vector direction) {
        spriteCounterUpdate();
        //Mudança de sprite pelo teclado
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

    public BufferedImage getPlayerImage(MovingEntitySprites entitySelected) {
        BufferedImage playerImage = null;
        switch (spriteDirection) {
            case "STAND_FRONT" -> playerImage = entitySelected.standFront;
            case "STAND_BACK" -> playerImage = entitySelected.standBack;
            case "UP" -> playerImage = entitySelected.up.getSpriteArray()[spriteNumber];
            case "DOWN" -> playerImage = entitySelected.down.getSpriteArray()[spriteNumber];
            case "RIGHT" -> playerImage = entitySelected.right.getSpriteArray()[spriteNumber];
            case "LEFT" -> playerImage = entitySelected.left.getSpriteArray()[spriteNumber];
        }
        return playerImage;
    }

    private void spriteCounterUpdate() {
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

}
