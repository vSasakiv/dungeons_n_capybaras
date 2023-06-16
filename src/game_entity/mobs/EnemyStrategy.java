package src.game_entity.mobs;

import src.game_entity.Vector;

/**
 * Interface que contém todos os métodos necessários para termos uma
 * estratégia válida para um inimigo
 */
public interface EnemyStrategy {
    /**
     * @param pos posição atual do inimigo
     * @param playerPos posição do player
     * @return um vetor indicando a direção de movimentação do inimigo
     */
    Vector newDirection(Vector pos, Vector playerPos);

    /**
     * @return true caso o inimigo deva atirar.
     */
    boolean shouldShoot();

    /**
     * @return clone de um objeto estratégia.
     */
    EnemyStrategy clone();
}
