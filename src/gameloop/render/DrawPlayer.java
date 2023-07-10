package gameloop.render;

import game_entity.GameEntity;
import game_entity.Vector;
import game_entity.entity_sprites.MovingEntitySprites;
import gameloop.Constants;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class DrawPlayer extends DrawMovingEntity {


    public DrawPlayer(GameEntity entity, ArrayList<MovingEntitySprites> movingEntitySprites) {
        super(entity, movingEntitySprites);
    }

    @Override
    protected BufferedImage getEntityImage() {
        BufferedImage playerImage = super.getEntityImage();
        switch (spriteDirection) {
            case "UP_LEFT" -> playerImage = entitySprites.get(spritesSelect).upLeft.getSpriteArray()[spriteNumber];
            case "UP_RIGHT" -> playerImage = entitySprites.get(spritesSelect).upRight.getSpriteArray()[spriteNumber];
            case "DOWN_LEFT" -> playerImage = entitySprites.get(spritesSelect).downLeft.getSpriteArray()[spriteNumber];
            case "DOWN_RIGHT" -> playerImage = entitySprites.get(spritesSelect).downRight.getSpriteArray()[spriteNumber];
        }
        return playerImage;
    }

    @Override
    public void spriteUpdate(Vector direction) {
        if (Vector.vectorEquals(direction, Constants.DIRECTION_UP_LEFT)) {
            this.spriteDirection = "UP_LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_UP_RIGHT)) {
            this.spriteDirection = "UP_RIGHT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN_LEFT)) {
            this.spriteDirection = "DOWN_LEFT";
        } else if (Vector.vectorEquals(direction, Constants.DIRECTION_DOWN_RIGHT)) {
            this.spriteDirection = "DOWN_RIGHT";
        }
        super.spriteUpdate(direction);

    }
}
