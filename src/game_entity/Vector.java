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

    /**
     * @param a primeiro vetor
     * @param b segundo vetor
     * @return o produto escalar entre os vetores a e b
     */
    public static float innerProduct (Vector a, Vector b) { return (a.x * b.x) + (a.y * b.y); }

    /**
     * @param a Vetor 
     * @return O ângulo que esse vetor forma com o eixo x
     */
    public static double getDegree (Vector a) {
       double cosine =  a.x / a.module();
       return Math.acos(cosine);
    }
    /**
     * @param a Vetor a ser transformado
     * @return um vetor de mesma direção e sentido, mas de módulo 1
     */
    public static Vector unitVector(Vector a){
        return new Vector (a.x/a.module(), a.y/a.module());
    }

    /**
     * @param a Vetor a ser transformado
     * @param degrees Angulo em graus
     * @return Vetor rotacionado degrees graus na direção anti horária
     */
    public static Vector rotateVector(Vector a, int degrees){
        double angle = degrees * 2 * Math.PI / 360.0;
        return new Vector(
                (float) (Math.cos(angle)*a.x - Math.sin(angle)*a.y),
                (float) (Math.sin(angle)*a.x + Math.cos(angle)*a.y));
    }

    /**
     * @param a Vetor a ser comparado
     * @param b Vetor a ser comparado
     * @return true caso o Vetor a seja igual a b, ou seja, suas componentes sejam as mesmas.
     */
    public static boolean vectorEquals(Vector a, Vector b){
        return (a.x == b.x && a.y == b.y);
    }
}
