package game_entity.mobs;

import game_entity.Attributes;
import game_entity.Hitbox;
import game_entity.weapons.Weapon;

public class PassiveEnemyFactory implements EnemyFactory{

    private final Attributes attributes;
    private final Hitbox hitbox;
    private final Weapon weapon;
    private final int velocity;

    public PassiveEnemyFactory(int velocity, Attributes attributes, Hitbox hitbox, Weapon weapon) {
        this.velocity = velocity;
        this.attributes = attributes;
        this.hitbox = hitbox;
        this.weapon = weapon;
    }

    @Override
    public Enemy criaEnemy(float posX, float posY) {
        return new PassiveEnemy(posX, posY, this.velocity, new Hitbox(this.hitbox), new Attributes(this.attributes), this.weapon);
    }

}
