package game_entity.weapons.projectiles;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;
import gameloop.render.DrawProjectile;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Classe especializada em um tipo de projétil: Bullet: um projétil simples que apenas mantém a direção
 * após atirado, e não possui nenhuma característica especial.
 */
public class Bullet extends Projectile{
    BufferedImage image;
    DrawProjectile drawMethod;

    /**
     * Construtor da classe Bullet
     * @param posX x da posição no mundo
     * @param posY y da posição no mundo
     * @param velocity velocidade do projétil
     * @param direction direção da trajetória
     * @param hitbox hitbox do projétil
     * @param image sprite
     */
    public Bullet(float posX, float posY, int velocity, Vector direction, ProjectileHitbox hitbox, BufferedImage image) {
        super(posX, posY, velocity, direction, hitbox, image);
        this.image = image;
        this.setSpriteSizeX(image.getWidth() * Constants.SCALE);
        this.setSpriteSizeY(image.getHeight() * Constants.SCALE);
        drawMethod = new DrawProjectile(this);
    }

    /**
     * Atualiza posição e a hitbox do projétil
     */
    @Override
    public void tick() {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
        this.hitbox.setPosition(this.position);
    }

    /**
     * @return true caso a bala esteja fora do mapa e deva ser deletada
     */
    @Override
    public boolean shouldDelete() {
        return this.collided;
    }

    /**
     *  Método responsável por desenhar bullet na tela
     * @param g2d Ferramenta gráfica
     * @param entity Entidade que bullet está atrelada
     */
    public void draw(Graphics2D g2d, GameEntity entity) {
        drawMethod.draw(g2d, entity);
        /*AffineTransform original = g2d.getTransform();
        g2d.translate(
                this.getWorldPosX() - entity.getWorldPosX() + entity.getScreenX() + (double) entity.getSpriteSizeX() / 2,
                16 + this.getWorldPosY() - entity.getWorldPosY() + entity.getScreenY() + (double) entity.getSpriteSizeY() / 2
        );
        g2d.rotate(Vector.getDegree(this.direction));
        g2d.drawImage(
                this.image,
                -this.getSpriteSizeX() / 2,
                -this.getSpriteSizeY() / 2,
                this.getSpriteSizeX(),
                this.getSpriteSizeY(),
                null
        );
        g2d.setTransform(original);*/
    }

    /**
     * @return Novo array list contendo sub-projéteis, no caso de uma bala normal, não há sub-projéteis,
     * então é apenas retornado um ArrayList vazio
     */
    @Override
    public ArrayList<Projectile> subProjectiles() {
        return new ArrayList<>();
    }
}
