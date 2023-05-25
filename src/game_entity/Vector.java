package game_entity;

public class Vector {
    float x, y;

    /**
     * @param x primeira componente do vetor
     * @param y segunda componente do vetor
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param a Vetor a
     * @param b Vetor b
     * @return Vetor resultante da soma a e b
     */
    public static Vector add(Vector a, Vector b){
        return new Vector(a.x + b.x, a.y + b.y);
    }

    /**
     * @return Módulo do vetor utilizando distância euclidiana
     */
    public float module(){
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * @param factor fator de aumento
     *               Modifica módulo de um vetor pelo fator escalar, de modo a
     *               manter direção e sentido iguais.
     */
    public void scale(int factor){
        this.x = (int) ((this.x * factor)/this.module());
        this.y = (int) ((this.y * factor)/this.module());
    }

    /**
     * @param a Vetor a
     * @param b Escalar b
     * @return retorna o produto escalar entre o Vetor a e o Escalar b
     */
    public static Vector scalarMultiply(Vector a, int b){
        return new Vector (a.x * b, a.y * b);
    }
    public static Vector unitVector(Vector a){
        return new Vector (a.x/a.module(), a.y/a.module());
    }

    public static boolean vectorEquals(Vector a, Vector b){
        return (a.x == b.x && a.y == b.y);
    }
}
