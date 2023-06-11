package geometry.primitives;

public class Point {
    public float x; //coordenada x do ponto
    public float y; //coordenada y do ponto

    /**
     * construtor padrão, que cria ponto na origem O = (0,0)
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * construtor completo, que cria ponto em P = (x,y)
     * @param x
     * @param y
     */
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * obtém coordenada x do ponto
     * @return x
     */
    public float getX() {
        return this.x;
    }

    /**
     * obtém coordenada y do ponto
     * @return y
     */
    public float getY() {
        return this.y;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
