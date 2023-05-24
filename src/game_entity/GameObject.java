package game_entity;

public class GameObject {
    Vector position;

    public GameObject(int posX, int posY){
        position = new Vector(posX, posY);
    }
    public int getPosX() {
        return position.x;
    }

    public int getPosY() {
        return position.y;
    }
}
