package gameloop.game_states.difficulty;

import game_entity.mobs.Enemy;

public interface DifficultyState {
    void updateAttributes(Enemy enemy);
}
