package game_entity.weapons;

import game_entity.weapons.projectiles.BulletFactory;
import game_entity.weapons.projectiles.ProjectileFactory;

public final class WeaponTemplates {
    private WeaponTemplates(){}


    public static final ProjectileFactory BASIC_PROJECTILE = new BulletFactory(5, 10);
    public static final Weapon PISTOL = new AutomaticWeapon(5, 2, BASIC_PROJECTILE);
    public static final Weapon RIFLE = new AutomaticWeapon(20, 4, BASIC_PROJECTILE);
    public static final Weapon SHOTGUN = new MultiShotWeapon(4, 1, BASIC_PROJECTILE, 10, 5);
}

