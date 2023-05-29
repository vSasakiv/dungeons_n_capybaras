package game_entity;

import java.awt.*;

public class Hitbox {
    // Implementação para uma região retangular

    private Vector position;
    private final float width;
    private final float height;


    /**
     * @param width largura da hitbox
     * @param height altura da hitbox
     * @param position posição da entidade que irá conter a hitbox
     */
    public Hitbox(float width, float height, Vector position) {
        this.position = position;
        this.width = width;
        this.height =  height;
    }

    /**
     * @return obtém menor coordenada x da caixa
     */
    public float minX(){
        return -1 * this.width/2 + this.position.x;
    }

    /**
     * @return obtém maior coordenada x da caixa
     */
    public float maxX(){
        return this.width/2 + this.position.x;
    }

    /**
     * @return obtém menor coordenada y da caixa
     */
    public float minY(){
        return -1 * this.height/2 + this.position.y;
    }

    /**
     * @return obtém maior coordenada y da caixa
     */
    public float maxY(){
        return this.width/2 + this.position.y;
    }
    /**
     * Verifica se a hitbox está colidindo com outra
     * @param hitbox hitbox cuja colisão com a da classe será avaliada
     * */
    public boolean isHitting(Hitbox hitbox) {
        if(hitbox.minX() > this.maxX() || hitbox.maxX() < this.minX()) {
            return  false;
        } else return !(hitbox.minY() > this.maxY()) && !(hitbox.maxY() < this.minY());
    }

    /**
     * @param position atualiza posição da hitbox
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
