package game_entity.mobs;

import game_entity.Attributes;
import game_entity.Hitbox;
import game_entity.Vector;

import java.util.ArrayList;

import static game_entity.weapons.WeaponTemplates.PISTOL;

public final class EnemyTemplates {

    private EnemyTemplates(){}

    public static final Attributes ATTRIBUTOS_BASICO = new Attributes(2, 1, 0);
    public static final Hitbox HITBOX_NORMAL = new Hitbox(16, 16, new Vector(0, 0));
    public static final EnemyStrategy PASSIVE_BASICO = new PassiveStrategy(200, 400, 100, 10, 20);
    public static final Enemy INIMIGO_BASICO = new Enemy(0, 0, 3, PASSIVE_BASICO, PISTOL, HITBOX_NORMAL, ATTRIBUTOS_BASICO);

    public static ArrayList<Enemy> getEnemyTemplates(){
        ArrayList<Enemy> enemyTemplates = new ArrayList<>();
        enemyTemplates.add(INIMIGO_BASICO);
        return enemyTemplates;
    }
}
