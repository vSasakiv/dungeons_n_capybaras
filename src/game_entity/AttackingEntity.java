package game_entity;

import game_entity.weapons.MeleeWeaponAttack;
import game_entity.weapons.projectiles.Projectile;
import game_entity.weapons.Weapon;

import java.util.ArrayList;

/**
 * Classe para representar uma entidade que realiza ataques, i.e. mobs e o player
 */
public class AttackingEntity extends GameEntity{

    private Weapon weapon; // Arma utilizada pela entidade
    private Attributes attributes; // Atributos da entidade

    // lista de ataques melee e projéteis gerados pela entidade
    private final ArrayList<MeleeWeaponAttack> meleeAttacks = new ArrayList<>();
    private final ArrayList<Projectile> rangedAttacks = new ArrayList<>();

    /**
     * @param worldPosX posição x no mundo
     * @param worldPosY posição y no mundo
     * @param velocity velocidade
     */
    public AttackingEntity(float worldPosX, float worldPosY, int velocity) {
        super(worldPosX, worldPosY, velocity);
    }

    /**
     * @param direction direção na qual a entidade está se movendo
     */
    protected void tickAttacks(Vector direction){
        ArrayList<Projectile> subProjectiles = new ArrayList<>();
        for (Projectile p: this.rangedAttacks){
            // para cada projétil devemos atualizar sua posição, além de verificar seus projéteis gerados
            p.tick();
            if (p.shouldDelete())
                subProjectiles.addAll(p.subProjectiles());
        }
        // adicionamos todos os sub-projéteis à lista principal
        this.rangedAttacks.addAll(subProjectiles);
        // removemos todos os projéteis que não fazem mais parte do jogo
        this.rangedAttacks.removeIf(Projectile::shouldDelete);

        // para cada ataque melee, devemos atualizar sua posição baseado na movimentação da entidade
        for (MeleeWeaponAttack a: this.meleeAttacks)
            a.tick(Vector.scalarMultiply(direction, this.velocity));

        // devemos apagar todos os ataques que já foram feitos
        this.meleeAttacks.removeIf(MeleeWeaponAttack::isFinished);
    }

    /**
     * @return ArrayList com todos os projéteis gerados por esta entidade
     */
    public ArrayList<Projectile> getRangedAttacks() {
        return rangedAttacks;
    }

    /**
     * @return ArrayList com todos os ataque melee geradores por esta entidade
     */
    public ArrayList<MeleeWeaponAttack> getMeleeAttacks() {
        return meleeAttacks;
    }
    /**
     * @return atributos do player
     */
    public Attributes getAttributes() {
        return attributes;
    }

    /**
     * @param weapon nova arma que a entidade irá usar
     */
    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    /**
     * @param attributes Novos atributos que a entidade irá ter
     */
    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    /**
     * @return arma atual utilizada pela entidade
     */
    public Weapon getWeapon() {
        return weapon;
    }
}
