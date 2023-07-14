package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

public class EasyState implements DifficultyState {
    @Override
    public void updateAttributes(Enemy enemy) {
        int currentIncrement = enemy.getAttributes().getIncremented();
        enemy.getAttributes().setMaxHealth(enemy.getAttributes().getMaxHealth() - currentIncrement);
        enemy.getAttributes().setMaxArmor(enemy.getAttributes().getMaxArmor() - currentIncrement);
        enemy.getAttributes().restore();
        enemy.getAttributes().setIncremented(0);
    }
}
