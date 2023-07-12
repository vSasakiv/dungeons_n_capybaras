package game_entity.npcs;

import game_entity.GameEntity;
import game_entity.Hitbox;
import game_entity.Vector;

import java.awt.*;

/**
 * Interface que possuí os comportamentos de um npc que se movimenta
 */
public interface MovableNpc {
    /**
     * @param playerPos vetor posição do player
     */
    void tick(Vector playerPos);

    /**
     * @param g2d Graphics2D java
     * @param player Entidade na qual será desenhado relativo a
     */
    void draw(Graphics2D g2d, GameEntity player);

    /**
     * @param strategy modifica a strategy do npc
     */
    void setStrategy(NpcStrategy strategy);

    /**
     * @param hitbox Hitbox de outra entidade
     * @return true caso o npc esteja colidindo com esta entidade
     */
    boolean isColliding(Hitbox hitbox);

    /**
     * @return array de String contendo diálogo do npc
     */
    String[] getDialogues();
}
