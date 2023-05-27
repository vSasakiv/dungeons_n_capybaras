package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;
import gameloop.Constants;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Bullet extends Projectile{
    BufferedImage image;

    /**
     * Construtor da classe Bullet
     * @param posX x da posição no mundo
     * @param posY y da posição no mundo
     * @param velocity velocidade do projétil
     * @param direction direção da trajetória
     * @param image sprite
     */
    public Bullet(float posX, float posY, int velocity, Vector direction, BufferedImage image) {
        super(posX, posY, velocity, direction);
        this.image = image;
        this.setSpriteSizeX(13 * 3);
        this.setSpriteSizeY(5 * 3);
    }

    @Override
    public void tick() {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
    }
    @Override
    public void tick(Vector direction) {}

    @Override
    public boolean shouldDelete() {
        return  this.getWorldPosX() < 0 ||
                this.getWorldPosX() > Constants.WORLD_WIDTH ||
                this.getWorldPosY() < 0 ||
                this.getWorldPosY() > Constants.WORLD_HEIGHT;
    }

    /**
     *  Método responsável por desenhar bullet na tela
     * @param g2d Ferramenta gráfica
     * @param entity Entidade que bullet está atrelada
     */
    public void draw(Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate(
                this.getWorldPosX() - entity.getWorldPosX() + entity.getScreenX() + (double) entity.getSpriteSizeX() / 2,
                16 + this.getWorldPosY() - entity.getWorldPosY() + entity.getScreenY() + (double) entity.getSpriteSizeX() / 2
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
        g2d.setTransform(original);
    }
    @Override
    public ArrayList<Projectile> subProjectiles() {
        return new ArrayList<>();
    }
}
