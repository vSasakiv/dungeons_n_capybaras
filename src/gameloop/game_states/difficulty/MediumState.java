package gameloop.game_states.difficulty;

import game_entity.DungeonPlayer;
import game_entity.mobs.Enemy;
import game_entity.weapons.AutomaticWeapon;
import game_entity.weapons.projectiles.BulletFactory;
import game_entity.weapons.projectiles.ProjectileFactory;

/**
 * Estado para dificuldade normal, todos os inimigos têm 1 ponto a mais de vida e armadura
 */
public class MediumState implements DifficultyState{

    @Override
    public void updateAttributes(Enemy enemy) {
        int currentIncrement = enemy.getAttributes().getIncremented();
        enemy.getAttributes().setMaxHealth(enemy.getAttributes().getMaxHealth() - currentIncrement + 1);
        enemy.getAttributes().setMaxArmor(enemy.getAttributes().getMaxArmor() - currentIncrement + 1);
        enemy.getAttributes().restore();
        enemy.getAttributes().setIncremented(1);
    }

    @Override
    public DungeonPlayer getPlayer() {
        DungeonPlayer player = new DungeonPlayer(600, 600, 9);
        ProjectileFactory factory = new BulletFactory(15, 5, "ARROW");
        player.setWeapon(new AutomaticWeapon(14, 3, factory, "BOW"));
        return player;
    }
}
