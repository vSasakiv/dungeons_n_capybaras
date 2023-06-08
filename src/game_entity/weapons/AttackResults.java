package game_entity.weapons;
import game_entity.weapons.projectiles.Projectile;

import java.util.ArrayList;

public class AttackResults {
    ArrayList<Projectile> projectiles;
    ArrayList<MeleeWeaponAttack> hitboxes;

    public AttackResults(ArrayList<Projectile> projectiles, ArrayList<MeleeWeaponAttack> hitboxes) {
        this.projectiles = projectiles;
        this.hitboxes = hitboxes;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<MeleeWeaponAttack> getHitboxes() { return hitboxes; }
}
