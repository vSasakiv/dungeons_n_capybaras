package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

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
}
