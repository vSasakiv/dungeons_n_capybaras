package game_entity;

public class Vector {
    int x, y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector add(Vector a, Vector b){
        return new Vector(a.x + b.x, a.y + b.y);
    }
    public float module(){
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public void scale(int factor){
        this.x = (int) ((this.x * factor)/this.module());
        this.y = (int) ((this.y * factor)/this.module());
    }
    public static Vector scalarMultiply(Vector a, int b){
        return new Vector (a.x * b, a.y * b);
    }

}
