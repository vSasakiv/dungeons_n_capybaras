package game_entity.weapons;

import game_entity.weapons.projectiles.BulletFactory;
import game_entity.weapons.projectiles.ProjectileFactory;

public final class WeaponTemplates {
    private WeaponTemplates(){}

    public static final ProjectileFactory WEB = new BulletFactory(5, 10, "WEB");
    public static final ProjectileFactory EYE = new BulletFactory(5, 10, "EYE");
    public static final ProjectileFactory SLIME = new BulletFactory(5, 10, "SLIME");
    public static final ProjectileFactory BRAIN = new BulletFactory(5, 10, "BRAIN");
    public static final ProjectileFactory FLY = new BulletFactory(5, 10, "FLY");
    public static final ProjectileFactory IMP = new BulletFactory(5, 10, "IMP");
    public static final ProjectileFactory TWIG = new BulletFactory(5, 10, "TWIG");
    public static final ProjectileFactory FLAME = new BulletFactory(5, 10, "FLAME");

    public static final Weapon WEB_WEAPON = new AutomaticWeapon(20, 4, WEB, "BOW");
    public static final Weapon EYE_WEAPON = new AutomaticWeapon(20, 4, EYE, "BOW");
    public static final Weapon SLIME_WEAPON = new AutomaticWeapon(20, 4, SLIME, "BOW");
    public static final Weapon BRAIN_WEAPON = new AutomaticWeapon(20, 4, BRAIN, "BOW");
    public static final Weapon FLY_WEAPON = new AutomaticWeapon(20, 4, FLY, "BOW");
    public static final Weapon IMP_WEAPON = new AutomaticWeapon(20, 4, IMP, "BOW");
    public static final Weapon TWIG_WEAPON = new AutomaticWeapon(20, 4, TWIG, "BOW");
    public static final Weapon FLAME_WEAPON = new AutomaticWeapon(20, 4, FLAME, "BOW");

}

