package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

/**
 * Estado para representar dificuldade difícil, todos os inimigos têm 2 pontos de vida e armadura a mais que o normal
 */
public class HardState implements DifficultyState{

    @Override
    public void updateAttributes(Enemy enemy) {
        int currentIncrement = enemy.getAttributes().getIncremented();
        enemy.getAttributes().setMaxHealth(enemy.getAttributes().getMaxHealth() - currentIncrement + 2);
        enemy.getAttributes().setMaxArmor(enemy.getAttributes().getMaxArmor() - currentIncrement + 2);
        enemy.getAttributes().restore();
        enemy.getAttributes().setIncremented(2);
    }
}
