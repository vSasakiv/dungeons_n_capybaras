package game_entity.weapons.projectiles;

import game_entity.GameEntity;
import game_entity.Vector;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Classe especializada em um tipo de projétil: ClusterBullet: Projétil que depois de um certo tempo, se divide
 * em 2 ou mais sub-projéteis
 */
public class ClusterBullet extends Projectile{
    BufferedImage image; // sprite do projétil
    int timeUntilExplode; // tempo (em ticks) até a divisão
    int counter = 0; // contador inicial
    int numberProjectiles; // número de sub-projéteis gerados
    ProjectileFactory subProjectileFactory; // fábrica de projéteis para a criação dos sub-projéteis.

    /**
     * @param posX posição x da qual o projétil foi atirado
     * @param posY posição y da qual o projétil foi atirado
     * @param velocity velocidade do projétil
     * @param direction direção na qual o projétil deve seguir
     * @param hitbox hitbox do projétil
     * @param timeUntilExplode tempo (em ticks) até a divisão do projétil
     * @param numeroProjeteis número de projéteis criados após divisão
     * @param subProjectileFactory fábrica de projéteis para geração dos sub-projéteis
     * @param image sprite do projétil principal.
     */
    ClusterBullet(float posX, float posY, int velocity, Vector direction,
                  ProjectileHitbox hitbox,
                  int timeUntilExplode, int numeroProjeteis,
                  ProjectileFactory subProjectileFactory,
                  BufferedImage image) {
        super(posX, posY, velocity, direction, hitbox);
        this.timeUntilExplode = timeUntilExplode;
        this.numberProjectiles = numeroProjeteis;
        this.subProjectileFactory = subProjectileFactory;
        this.image = image;
        this.setSpriteSizeX(13 * 3);
        this.setSpriteSizeY(5 * 3);
    }

    /**
     * Atualiza o projétil a cada tick, mudando sua posição, hitbox e somando +1 ao contador de tempo
     * de explosão
     */
    @Override
    public void tick() {
        counter += 1;
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, this.velocity));
        this.hitbox.setPosition(this.position);
    }

    /**
     * Método responsável por desenhar ClusterBullet na tela
     * @param g2d Ferramenta gráfica
     * @param entity  Entidade que ClusterBullet está atrelada
     */
    @Override
    public void draw (Graphics2D g2d, GameEntity entity) {
        AffineTransform original = g2d.getTransform();
        g2d.translate (
                this.getWorldPosX() - entity.getWorldPosX() + entity.getScreenX() + (double) entity.getSpriteSizeX() / 2,
                16 + this.getWorldPosY() - entity.getWorldPosY() + entity.getScreenY() + (double) entity.getSpriteSizeX() / 2
        );
        g2d.rotate(Vector.getDegree(this.direction));
        g2d.drawImage (
                this.image,
                -this.getSpriteSizeX() / 2,
                -this.getSpriteSizeY() / 2,
                this.getSpriteSizeX(),
                this.getSpriteSizeY(),
                null
        );
        g2d.setTransform(original);
    }

    /**
     * @return true caso a bala deva ser deletada, isto é, se está fora da tela ou deve explodir
     */
    @Override
    public boolean shouldDelete() {
        return counter > timeUntilExplode || this.collided;
    }

    /**
     * @return ArrayList contendo todos os sub-projéteis criados pela divisão
     */
    @Override
    public ArrayList<Projectile> subProjectiles() {
        int angle = 360 / numberProjectiles;
        ArrayList<Projectile> cluster = new ArrayList<>();
        for (int i = 0; i < numberProjectiles; i++)
            cluster.add(
                    subProjectileFactory.criaProjetil(
                            this.getWorldPosX(),
                            this.getWorldPosY(),
                            Vector.rotateVector(direction, angle*i)));
        return cluster;
    }
}
