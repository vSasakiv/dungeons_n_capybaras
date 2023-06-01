package game_entity.weapons;

import game_entity.Counter;
import game_entity.Hitbox;

public class MeleeWeaponAttack extends Hitbox {
    private final int damage;
    private final Counter durationCounter;

    public MeleeWeaponAttack(Hitbox hitbox, int damage, Counter durationCounter) {
        super(hitbox);
        this.damage = damage;
        this.durationCounter = durationCounter;
        this.durationCounter.start();
    }

    public void tick(){
        this.durationCounter.tick();
    }
    public int getDamage() {
        return damage;
    }

    public boolean isFinished(){
        return durationCounter.isZero();
    }
}
