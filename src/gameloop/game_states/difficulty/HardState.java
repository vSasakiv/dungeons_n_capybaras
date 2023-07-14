package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

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
