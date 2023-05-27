package game_entity.weapons;

import game_entity.Vector;

import java.util.ArrayList;


public class MultiShotWeapon extends Weapon{
    private final ProjectileFactory projectileFactory;
    int angulo, numeroProjeteis;

    /**
     * @param fireRate velocidade de ataque da arma
     * @param damage dano de cada projétil da arma
     * @param projectileFactory Fábrica de projéteis
     * @param angulo angulo entre cada projétil
     * @param numeroProjeteis numero de projéteis por tiro
     */
    public MultiShotWeapon(int fireRate, int damage, ProjectileFactory projectileFactory, int angulo, int numeroProjeteis) {
        super(fireRate, damage);
        this.projectileFactory = projectileFactory;
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
    public ArrayList<Projectile> shoot(int posX, int posY, Vector direction) {
        int metade = numeroProjeteis/2;
        this.coolDown = 0;
        ArrayList<Projectile> projectiles = new ArrayList<>();
        for (int i = 0; i < numeroProjeteis; i++)
            projectiles.add(projectileFactory.criaProjetil(
                    posX+10,
                    posY+10,
                    Vector.rotateVector(direction, (metade-i)*angulo)
            ));
        return projectiles;
    }
}
