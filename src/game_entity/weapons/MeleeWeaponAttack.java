package game_entity.weapons;

import game_entity.Counter;
import game_entity.Hitbox;
import game_entity.Vector;

/**
 * Classe para representar um ataque corpo a corpo
 */
public class MeleeWeaponAttack extends Hitbox {
    private final int damage; // dano infligido por um ataque
    private final Counter durationCounter; // duração do ataque

    /**
     * @param hitbox uma hitbox parâmetro para o ataque
     * @param damage o dano de um ataque
     * @param durationCounter duração do ataque
     */
    public MeleeWeaponAttack(Hitbox hitbox, int damage, Counter durationCounter) {
        super(hitbox);
        this.damage = damage;
        this.durationCounter = durationCounter;
        this.durationCounter.start();
    }

    /**
     * @param displacement vetor que indica a variação de posição de uma entidade que realizou o ataque
     */
    public void tick(Vector displacement){
        this.durationCounter.tick();
        this.setPosition(Vector.add(this.getPosition(), displacement));
    }
    public int getDamage() {
        return damage;
    }

    public boolean isFinished(){
        return durationCounter.isZero();
    }
}
