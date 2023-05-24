package game_entity;

/**
 * Classes utilizada para representar todos os objetos do jogo, possuindo apenas
 * o atributo posição
 */
public class GameObject {
    Vector position;

    /**
     * @param posX coordenadas da posição inicial x do objeto
     * @param posY coordenadas da posição inicial y do objeto
     */
    public GameObject(float posX, float posY){
        position = new Vector(posX, posY);
    }
    public float getPosX() {
        return position.x;
    }

    public float getPosY() {
        return position.y;
    }
}
