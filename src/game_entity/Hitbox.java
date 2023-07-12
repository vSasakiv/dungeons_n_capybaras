package game_entity;

import gameloop.Constants;

import java.awt.*;

public class Hitbox {
    // Implementação para uma região retangular

    private Vector position;
    private final float width;
    private final float height;


    /**
     * @param width    largura da hitbox
     * @param height   altura da hitbox
     * @param position posição da entidade que irá conter a hitbox
     */
    public Hitbox(float width, float height, Vector position) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * Copy constructor para clonarmos uma hitbox para cada inimigo
     * @param hitbox hitbox a ser clonada
     */
    public Hitbox(Hitbox hitbox) {
        this.position = hitbox.position;
        this.width = hitbox.width;
        this.height = hitbox.height;
    }

    /**
     * @return obtém menor coordenada x da caixa
     */
    public float minX() {
        return -1 * this.width / 2 + this.position.x;
    }

    /**
     * @return obtém maior coordenada x da caixa
     */
    public float maxX() {
        return this.width / 2 + this.position.x;
    }

    /**
     * @return obtém menor coordenada y da caixa
     */
    public float minY() {
        return -1 * this.height / 2 + this.position.y;
    }

    /**
     * @return obtém maior coordenada y da caixa
     */
    public float maxY() {
        return this.height/ 2 + this.position.y;
    }

    /**
     * Verifica se a hitbox está colidindo com outra
     * @param hitbox hitbox cuja colisão com a da classe será avaliada
     */
    public boolean isHitting(Hitbox hitbox) {
        if (hitbox.minX() > this.maxX() || hitbox.maxX() < this.minX()) {
            return false;
        } else return !(hitbox.minY() > this.maxY()) && !(hitbox.maxY() < this.minY());
    }

    /**
     * @param position atualiza posição da hitbox
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * @return posição da hitbox
     */
    public Vector getPosition() { return position;}

    /**
     * @return obtém posição em x da hitbox no mundo (canto superior esquerdo do retângulo)
     */
    public float getWorldPosX() {
        return this.position.x;
    }

    /**
     * @return obtém posição em y da hitbox no mundo (canto superior esquerdo do retângulo)
     */
    public float getWorldPosY() {
        return this.position.y;
    }

    /**
     * @param object entidade
     * @return posição x na tela em relação à entidade
     */
    public float getScreenX(GameObject object) {
        return this.getWorldPosX() - object.getWorldPosX() + (float) Constants.WIDTH / 2;
    }

    /**
     * @param object entidade
     * @return posição y na tela em relação à entidade
     */
    public float getScreenY(GameObject object) {
        return this.getWorldPosY() - object.getWorldPosY() + (float) Constants.HEIGHT / 2;
    }


    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    /**
     * Desenha hitbox como um retângulo vermelho na tela
     * @param g2d Objeto Graphics2D para desenharmos
     * @param entity entidade que possui a hitbox
     */
    public void draw(Graphics2D g2d, GameEntity entity) {
        g2d.setColor(Color.RED);
        g2d.drawRect(
                (int) (this.getScreenX(entity) - this.width / 2),
                (int) (this.getScreenY(entity) - this.height / 2),
                (int) this.width,
                (int) this.height
        );
    }

    public void draw(Graphics2D g2d, int screenX, int screenY, Color color) {
        g2d.setColor(color);
        g2d.drawRect(
                (int) (screenX),
                (int) (screenY),
                (int) Constants.TILE_SIZE,
                (int) Constants.TILE_SIZE
        );
    }

}
