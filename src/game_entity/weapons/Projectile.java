package game_entity.weapons;

import game_entity.GameEntity;
import game_entity.Vector;

import java.util.ArrayList;

public abstract class Projectile extends GameEntity {
    Vector direction;
    public Projectile(float posX, float posY, int velocity, Vector direction) {
        super(posX, posY, velocity);
        this.direction = direction;
    }

    @Override
    public abstract void tick();
    @Override
    public abstract void tick(Vector direction);

    /**
     * @return boolean indicando se o projétil está fora do mapa e deve deixar de ser atualizado
     */
    public abstract boolean shouldDelete();

    public abstract ArrayList<Projectile> subProjectiles();
}
