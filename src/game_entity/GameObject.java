package game_entity;

import gameloop.Constants;

/**
 * Classes utilizada para representar todos os objetos do jogo, possuindo apenas
 * o atributo posição
 */
public class GameObject {
    protected Vector position;

    private Vector screenPosition;
    /**
     * @param worldPosX coordenadas da posição inicial x do objeto no mundo
     * @param worldPosY coordenadas da posição inicial y do objeto no mundo
     */
    public GameObject(float worldPosX, float worldPosY){
        position = new Vector(worldPosX, worldPosY);
        screenPosition = new Vector (worldPosX ,worldPosY);
    }

    /**
     * Obtém coordenada x do objeto
     * @return x
     */
    public float getWorldPosX() {
        return position.x;
    }

    /**
     * obtém coordenada y do objeto
     * @return y
     */
    public float getWorldPosY() {
        return position.y;
    }

    public void setScreenX(float x)  {this.screenPosition.x = x; }
    public void setScreenY(float y)  {this.screenPosition.y = y; }

    public float getScreenX () { return this.screenPosition.x; }
    public float getScreenY () { return this.screenPosition.y; }

    /**
     * @param object entidade
     * @return posição x na tela em relação à entidade
     */
    public float getScreenX (GameObject object) {
        return this.getWorldPosX() - object.getWorldPosX() + (float) Constants.WIDTH /2;
    }

    /**
     * @param object entidade
     * @return posição y na tela em relação à entidade
     */
    public float getScreenY (GameObject object){
        return this.getWorldPosY() - object.getWorldPosY() + (float) Constants.HEIGHT /2;
    }
}
