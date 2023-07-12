package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Vector;

/**
 * Interface que representa uma estratégia de movimentação de um npc
 */
public interface NpcStrategy {
    /**
     * @param npc O próprio npc a realizar o movimento
     * @param playerPos Vetor posição do player
     * @return vetor direção do próximo movimento do npc
     */
    Vector direction(GameEntity npc, Vector playerPos);
}
