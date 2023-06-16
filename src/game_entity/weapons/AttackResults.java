package src.game_entity.weapons;
import src.game_entity.weapons.projectiles.Projectile;

import java.util.ArrayList;

/**
 * Classe para guardar todos os objetos gerados quando uma entidade realiza um ataque
 */
public class AttackResults {
    ArrayList<Projectile> projectiles; // lista de projéteis gerados pelo ataque
    ArrayList<MeleeWeaponAttack> hitboxes; // lista de ataques melee gerados pelo ataque

    /**
     * @param projectiles lista de projéteis gerados pelo ataque
     * @param hitboxes lista de ataques melee gerados pelo ataque
     */
    public AttackResults(ArrayList<Projectile> projectiles, ArrayList<MeleeWeaponAttack> hitboxes) {
        this.projectiles = projectiles;
        this.hitboxes = hitboxes;
    }

    /**
     * @return Lista de projéteis gerados pelo ataque
     */
    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    /**
     * @return lista de ataques melee gerados pelo ataque
     */
    public ArrayList<MeleeWeaponAttack> getHitboxes() { return hitboxes; }
}
