package game_entity;

/**
 * Classes utilizada para representar todos os objetos do jogo, possuindo apenas
 * o atributo posição
 */
public class GameObject {
    Vector position;

    /**
     * @param worldPosX coordenadas da posição inicial x do objeto no mundo
     * @param worldPosY coordenadas da posição inicial y do objeto no mundo
     */
    public GameObject(float worldPosX, float worldPosY){
        position = new Vector(worldPosX, worldPosY);
    }
    public float getworldPosX() {
        return position.x;
    }

    public float getworldPosY() {
        return position.y;
    }
}
