package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

/**
 * Interface para representar um estado para a dificuldade atual do jogo
 */
public interface DifficultyState {
    /**
     * @param enemy Inimigo do qual devemos atualizar os atributos
     */
    void updateAttributes(Enemy enemy);
}
