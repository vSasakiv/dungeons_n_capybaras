package game_entity.weapons.projectiles;

import game_entity.Vector;
 public interface ProjectileFactory {
     Projectile criaProjetil (float posX, float posY, Vector direction);
}
