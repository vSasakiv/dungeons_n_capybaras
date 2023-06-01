package game_entity.weapons;
import java.util.ArrayList;

public class AttackResults {
    ArrayList<Projectile> projectiles;
    MeleeWeaponAttack hitbox;

    public AttackResults(ArrayList<Projectile> projectiles, MeleeWeaponAttack hitbox) {
        this.projectiles = projectiles;
        this.hitbox = hitbox;
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public MeleeWeaponAttack getHitbox() {
        return hitbox;
    }
}
