package src.game_entity.weapons.projectiles;

import src.game_entity.Vector;

/**
 * Interface para implementação de uma fábrica de projéteis
 */
 public interface ProjectileFactory {
     Projectile criaProjetil (float posX, float posY, Vector direction);
}
