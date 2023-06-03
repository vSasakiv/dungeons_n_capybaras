package game_entity.weapons;

import game_entity.Counter;
import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;
import java.awt.*;
import java.util.ArrayList;

public class MeleeWeapon extends Weapon{

    private final float hitboxWidth;
    private final float hitboxHeight;
    private final int attack_duration;

    public MeleeWeapon(int fireRate, int damage, float hitboxWidth, float hitboxHeight, int attack_duration) {
        super(fireRate, damage);
        this.hitboxWidth = hitboxWidth;
        this.hitboxHeight = hitboxHeight;
        this.attack_duration = attack_duration;
    }

    @Override
    public AttackResults attack(int posX, int posY, Vector direction) {
        ArrayList<MeleeWeaponAttack> attacks = new ArrayList<>();
        this.coolDownCounter.start();
        MeleeWeaponAttack attack = new MeleeWeaponAttack(
                new Hitbox(
                        this.hitboxWidth,
                        this.hitboxHeight,
                        new Vector(posX + this.hitboxWidth * direction.x, posY + this.hitboxHeight * direction.y)),
                this.damage,
                new Counter(this.attack_duration, 1));
        attacks.add(attack);
        return new AttackResults(new ArrayList<>(), attacks);
    }

    @Override
    public Weapon clone() {
        return new MeleeWeapon(
                this.coolDownCounter.getIncrement(),
                this.damage,
                this.hitboxWidth,
                this.hitboxHeight,
                this.attack_duration
        );
    }

    @Override
    public void draw(Graphics2D g2d, GameEntity entity) {}
}