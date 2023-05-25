package game_entity;

import java.util.ArrayList;


public class Shotgun extends Weapon{
    int angulo, numeroProjeteis;

    /**
     * @param fireRate velocidade de ataque da arma
     * @param damage dano de cada projétil da arma
     * @param speed velocidade de cada projétil da arma
     * @param angulo angulo entre cada projétil
     * @param numeroProjeteis numero de projéteis por tiro
     */
    public Shotgun(int fireRate, int damage, int speed, int angulo, int numeroProjeteis) {
        super(fireRate, damage, speed);
        this.angulo = angulo;
        this.numeroProjeteis = numeroProjeteis;
    }

    /**
     * @param posX      Posição x de onde o tiro foi feito
     * @param posY      Posição y de onde o tiro foi feito
     * @param direction Direção do projétil
     * @return ArrayList de tamanho numeroProjeteis contendo todos os projéteis gerados.
     */
    @Override
    ArrayList<Projectile> shoot(int posX, int posY, Vector direction) {
        int metade = numeroProjeteis/2;
        this.coolDown = 0;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        for (int i = 0; i < numeroProjeteis; i++)
            projectiles.add(
                    new Projectile(
                            posX,
                            posY,
                            this.speed,
                            Vector.rotateVector(direction, (metade-i)*angulo)));
        return projectiles;
    }
}
