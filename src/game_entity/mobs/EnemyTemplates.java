package game_entity.mobs;

import game_entity.Attributes;
import game_entity.Hitbox;
import game_entity.Vector;
import java.util.ArrayList;
import java.util.Objects;

import static game_entity.weapons.WeaponTemplates.*;

public final class EnemyTemplates {

    private EnemyTemplates(){}

    public static final Attributes ATRIBUTOS_BASICO = new Attributes(2, 1, 0, 3);
    public static final Attributes ATRIBUTOS_TANQUE = new Attributes(5, 2, 0, 2);
    public static final Attributes ATRIBUTOS_FRACO = new Attributes(1, 1, 0, 1);
    public static final Hitbox HITBOX_NORMAL = new Hitbox(16, 16, new Vector(0, 0));
    public static final Hitbox HITBOX_GRANDE = new Hitbox(32, 32, new Vector(0, 0));
    public static final EnemyStrategy PASSIVE_BASICO = new PassiveStrategy(200, 400, 100, 10, 20);
    public static final EnemyStrategy AGGRESIVE_BASICO = new AggresiveStrategy(200, 300, 10, 20);
    public static final Enemy ANT = new Enemy(0, 0, 2, AGGRESIVE_BASICO, WEB_WEAPON, HITBOX_NORMAL, ATRIBUTOS_FRACO, "ANT");
    public static final Enemy BRAIN = new Enemy(0, 0, 4, PASSIVE_BASICO, BRAIN_WEAPON, HITBOX_NORMAL, ATRIBUTOS_FRACO, "BRAIN");
    public static final Enemy EYE = new Enemy(0, 0, 5, PASSIVE_BASICO, EYE_WEAPON, HITBOX_GRANDE, ATRIBUTOS_TANQUE, "EYE");
    public static final Enemy FLAME = new Enemy(0, 0, 3, AGGRESIVE_BASICO, FLAME_WEAPON, HITBOX_GRANDE, ATRIBUTOS_TANQUE, "FLAME");
    public static final Enemy FLY = new Enemy(0, 0, 2, AGGRESIVE_BASICO, FLY_WEAPON, HITBOX_NORMAL, ATRIBUTOS_FRACO, "FLY");
    public static final Enemy IMP = new Enemy(0, 0, 3, PASSIVE_BASICO, IMP_WEAPON, HITBOX_NORMAL, ATRIBUTOS_BASICO, "IMP");
    public static final Enemy LARVA = new Enemy(0, 0, 2, AGGRESIVE_BASICO, EYE_WEAPON, HITBOX_GRANDE, ATRIBUTOS_TANQUE, "LARVA");
    public static final Enemy SLIME = new Enemy(0, 0, 2, AGGRESIVE_BASICO, SLIME_WEAPON, HITBOX_NORMAL, ATRIBUTOS_BASICO, "SLIME");
    public static final Enemy TWIG = new Enemy(0, 0, 2, PASSIVE_BASICO, TWIG_WEAPON, HITBOX_NORMAL, ATRIBUTOS_BASICO, "TWIG");


    public static ArrayList<Enemy> getEnemyTemplates(String type){
        ArrayList<Enemy> enemyTemplatesEletrica = new ArrayList<>();
        ArrayList<Enemy> enemyTemplatesBienio = new ArrayList<>();

        enemyTemplatesEletrica.add(ANT);
        enemyTemplatesEletrica.add(FLY);
        enemyTemplatesEletrica.add(LARVA);
        enemyTemplatesEletrica.add(SLIME);

        enemyTemplatesBienio.add(BRAIN);
        enemyTemplatesBienio.add(IMP);
        enemyTemplatesBienio.add(TWIG);
        enemyTemplatesBienio.add(FLAME);
        enemyTemplatesBienio.add(EYE);

        if (Objects.equals(type, "eletrica")) return enemyTemplatesEletrica;
        else if (Objects.equals(type, "bienio")) return enemyTemplatesBienio;

        return enemyTemplatesEletrica;
    }
}
