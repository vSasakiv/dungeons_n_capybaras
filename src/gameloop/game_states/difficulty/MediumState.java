package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

public class MediumState implements DifficultyState{

    @Override
    public void updateAttributes(Enemy enemy) {
        int currentIncrement = enemy.getAttributes().getIncremented();
        enemy.getAttributes().setMaxHealth(enemy.getAttributes().getMaxHealth() - currentIncrement + 1);
        enemy.getAttributes().setMaxArmor(enemy.getAttributes().getMaxArmor() - currentIncrement + 1);
        enemy.getAttributes().restore();
        enemy.getAttributes().setIncremented(1);
    }
}
