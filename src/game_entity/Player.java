package game_entity;
public class Player extends GameEntity{

    public Player(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
    }

    @Override
    public void tick(){}

    @Override
    public void tick(Vector direction) {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
        this.checkWindowBorder();
    }
    public Projectile shoot(int mouseX, int mouseY){
        Vector direction = new Vector(mouseX - this.getPosX(), mouseY - this.getPosY());
        direction = Vector.unitVector(direction);
        return new Projectile(this.getPosX(), this.getPosY(), 6, direction);
    }
}
