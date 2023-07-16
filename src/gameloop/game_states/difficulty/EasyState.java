package gameloop.game_states.difficulty;

import game_entity.DungeonPlayer;
import game_entity.mobs.Enemy;
import game_entity.weapons.AutomaticWeapon;
import game_entity.weapons.projectiles.BulletFactory;
import game_entity.weapons.projectiles.ClusterBulletFactory;
import game_entity.weapons.projectiles.ProjectileFactory;

/**
 * Estado de dificuldade fácil: todos os inimigos permanecem como o padrão
 */
public class EasyState implements DifficultyState {
    /**
     * @param enemy Inimigo do qual devemos atualizar os atributos
     */
    @Override
    public void updateAttributes(Enemy enemy) {
        int currentIncrement = enemy.getAttributes().getIncremented();
        enemy.getAttributes().setMaxHealth(enemy.getAttributes().getMaxHealth() - currentIncrement);
        enemy.getAttributes().setMaxArmor(enemy.getAttributes().getMaxArmor() - currentIncrement);
        enemy.getAttributes().restore();
        enemy.getAttributes().setIncremented(0);
    }

    @Override
    public DungeonPlayer getPlayer() {
        DungeonPlayer player = new DungeonPlayer(600, 600, 10);
        ProjectileFactory subSubFactory = new BulletFactory(4, 8, "ENERGY");
        ProjectileFactory subFactory = new ClusterBulletFactory(2, 20, 8, 6, subSubFactory, "ENERGY" );
        ProjectileFactory factory = new ClusterBulletFactory(6, 40, 4, 6, subFactory, "ENERGY");
        player.setWeapon(new AutomaticWeapon(5, 4, factory, "STAFF"));
        return player;
    }

}
