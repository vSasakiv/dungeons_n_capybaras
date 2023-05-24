package game_entity;

public class Player extends GameEntity{
    private int cooldown;
    private final int COOLDOWN = 30;
    public Player(int posX, int posY, int velocity) {
        super(posX, posY, velocity);
        this.cooldown = 0;
    }

    @Override
    public void tick(){}

    @Override
    public void tick(Vector direction) {
        this.position = Vector.add(this.position, Vector.scalarMultiply(direction, velocity));
        this.checkWindowBorder();
    }
    public Projectile shoot(int mouseX, int mouseY){
        this.cooldown = 0;
        Vector direction = new Vector(mouseX - this.getPosX(), mouseY - this.getPosY());
        direction = Vector.unitVector(direction);
        return new Projectile(this.getPosX() + 10, this.getPosY() + 10, 10, direction);
    }
    public boolean canShoot() {
        if (this.cooldown < this.COOLDOWN)
            this.cooldown += 1;
        return this.cooldown == this.COOLDOWN;
    }
}
