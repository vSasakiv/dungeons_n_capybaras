package src.game_entity.weapons.projectiles;

import src.game_entity.GameEntity;
import src.game_entity.GameObject;
import src.game_entity.Hitbox;
import src.game_entity.Vector;
import src.gameloop.Constants;

import java.awt.*;

/**
 * Classe para a hitbox de um projétil, sendo ela uma hitbox redonda
 */
public class ProjectileHitbox {
    private Vector position; // posição da hitbox, deve ser a mesma do projétil
    private final float radius; // raio da hitbox

    /**
     * @param position posição da hitbox
     * @param radius raio da hitbox
     */
    public ProjectileHitbox(Vector position, float radius) {
        this.position = position;
        this.radius = radius;
    }

    /**
     * @param hitbox Hitbox padrão para entidades (retangular)
     * @return true caso esteja colidindo
     */
    public boolean isHitting(Hitbox hitbox){
        float testX = position.x;
        float testY = position.y;
        float hitboxX = hitbox.getPosition().x - hitbox.getWidth()/2;
        float hitboxY = hitbox.getPosition().y - hitbox.getHeight()/2;

        if (position.x < hitboxX) testX = hitboxX;
        else if (position.x > hitboxX + hitbox.getWidth()) testX = hitboxX + hitbox.getWidth();

        if (position.y < hitboxY) testY = hitboxY;
        else if (position.y > hitboxY + hitbox.getHeight()) testY = hitboxY + hitbox.getHeight();

        Vector dist = new Vector(position.x - testX, position.y - testY);
        return dist.module() <= radius;
    }

    /**
     * @param position nova posição da hitbox
     */
    public void setPosition(Vector position) {
        this.position = position;
    }

    /**
     * @return obtém posição em x da hitbox no mundo (canto superior esquerdo do retângulo)
     */
    public float getWorldPosX(){ return this.position.x; }
    /**
     * @return obtém posição em y da hitbox no mundo (canto superior esquerdo do retângulo)
     */
    public float getWorldPosY(){ return this.position.y; }

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

    /**
     * Método para testes
     * @param g2d Graphics2D java
     * @param entity entidade a partir da qual será desenhado a hitbox
     */
    public void draw(Graphics2D g2d, GameEntity entity){
        g2d.setColor(Color.RED);
        g2d.drawOval(
                (int) (this.getScreenX(entity) - radius),
                (int) (this.getScreenY(entity) - radius),
                (int) (radius*2),
                (int) (radius*2));
    }
}
