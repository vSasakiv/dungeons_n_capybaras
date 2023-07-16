package gameloop.game_states.difficulty;

import game_entity.DungeonPlayer;
import game_entity.mobs.Enemy;
import game_entity.weapons.AutomaticWeapon;
import game_entity.weapons.projectiles.BulletFactory;
import game_entity.weapons.projectiles.ProjectileFactory;

/**
 * Estado para representar dificuldade difícil, todos os inimigos têm 2 pontos de vida e armadura a mais que o normal
 */
public class HardState implements DifficultyState{
    /**
     * @param enemy Inimigo do qual devemos atualizar os atributos
     */
    @Override
    public void updateAttributes(Enemy enemy) {
        int currentIncrement = enemy.getAttributes().getIncremented();
        enemy.getAttributes().setMaxHealth(enemy.getAttributes().getMaxHealth() - currentIncrement + 2);
        enemy.getAttributes().setMaxArmor(enemy.getAttributes().getMaxArmor() - currentIncrement + 2);
        enemy.getAttributes().restore();
        enemy.getAttributes().setIncremented(2);
    }

    /**
     *  Cria player para dada dificuldade
     * @return player da dificuldade selecionada
     */
    @Override
    public DungeonPlayer getPlayer() {
        DungeonPlayer player = new DungeonPlayer(600, 600, 8);
        ProjectileFactory factory = new BulletFactory(12, 5, "SHURIKEN");
        player.setWeapon(new AutomaticWeapon(10, 4, factory, "SHURIKEN"));
        return player;
    }
}
