package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Vector;

public interface NpcStrategy {
    Vector direction(GameEntity npc, Vector playerPos);
}
