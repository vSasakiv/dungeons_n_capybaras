package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Vector;

/**
 * Estratégia de patrulhamento, onde o npc fica circulando entre dois pontos
 */
public class PatrolStrategy implements NpcStrategy{
    Vector pos1, pos2; // Pontos no qual o npc irá patrulhar entre
    // tolerância da distância entre o npc e um ponto, para evitar pass-through
    static int min_dif = 5;

    /**
     * @param npc Npc que irá utilizar a estratégia
     * @param pos2 posição 2 da patrulha
     */
    public PatrolStrategy(GameEntity npc, Vector pos2) {
        this.pos1 = npc.getPosition();
        this.pos2 = pos2;
    }

    /**
     * @param npc       O próprio npc a realizar o movimento
     * @param playerPos Vetor posição do player
     * @return vetor direção do próximo movimento do npc
     */
    @Override
    public Vector direction(GameEntity npc, Vector playerPos) {
        // obtém a posição do npc
        Vector npcPos = npc.getPosition();
        // caso esteja em um ponto, muda a direção para o ouro ponto
        if (Vector.vectorEqualsTolerance(pos1, npcPos, min_dif)){
            return Vector.unitVector(Vector.add(pos2, Vector.scalarMultiply(pos1, -1)));
        }
        else if (Vector.vectorEqualsTolerance(pos2, npcPos, min_dif)) {
            return Vector.unitVector(Vector.add(pos1, Vector.scalarMultiply(pos2, -1)));
        }
        return npc.getDirection();
    }
}
