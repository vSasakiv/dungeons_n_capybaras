package game_entity;

import gameloop.Constants;
import gameloop.KeyHandler;
import gameloop.render.DrawMovingEntity;

public class DirectionUpdater {

    /**
     * Atualiza a direção do movimento do player, com base no input do teclado
     * @param keyHandler Inputs do teclado
     * @param drawMethod Método de renderização
     * @return Nova direção
     */
    public static Vector updateDirection(KeyHandler keyHandler, DrawMovingEntity drawMethod) {
        Vector direction = Constants.NULL_VECTOR;

        //SpriteCounter só é atualizado se alguma tecla está sendo pressionada
        if (keyHandler.isPressed())
            drawMethod.spriteCounterUpdate();

        if (keyHandler.isKeyA() && keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP_LEFT, direction);
        else if (keyHandler.isKeyD() && keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP_RIGHT, direction);
        else if (keyHandler.isKeyS() && keyHandler.isKeyA())
            direction = Vector.add(Constants.DIRECTION_DOWN_LEFT, direction);
        else if (keyHandler.isKeyS() && keyHandler.isKeyD())
            direction = Vector.add(Constants.DIRECTION_DOWN_RIGHT, direction);
        else if (keyHandler.isKeyW())
            direction = Vector.add(Constants.DIRECTION_UP, direction);
        else if (keyHandler.isKeyA())
            direction = Vector.add(Constants.DIRECTION_LEFT, direction);
        else if (keyHandler.isKeyS())
            direction = Vector.add(Constants.DIRECTION_DOWN, direction);
        else if (keyHandler.isKeyD())
            direction = Vector.add(Constants.DIRECTION_RIGHT, direction);

        drawMethod.spriteUpdate(direction);
        return direction;
    }
}
