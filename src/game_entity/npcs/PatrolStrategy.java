package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Vector;

public class PatrolStrategy implements NpcStrategy{
    Vector pos1, pos2; // patrols between position 1 and 2
    static int min_dif = 5;
    public PatrolStrategy(GameEntity npc, Vector pos2) {
        this.pos1 = npc.getPosition();
        this.pos2 = pos2;
    }

    @Override
    public Vector direction(GameEntity npc, Vector playerPos) {
        Vector npcPos = npc.getPosition();
        if (Vector.vectorEqualsTolerance(pos1, npcPos, min_dif)){
            return Vector.unitVector(Vector.add(pos2, Vector.scalarMultiply(pos1, -1)));
        }
        else if (Vector.vectorEqualsTolerance(pos2, npcPos, min_dif)) {
            return Vector.unitVector(Vector.add(pos1, Vector.scalarMultiply(pos2, -1)));
        }
        return npc.getDirection();
    }
}
