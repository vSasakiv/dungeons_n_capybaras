package geometry.primitives;

public class Triangle {
    private Point A;
    private Point B;
    private Point C;

    /**
     * construtor padrão do triângulo
     * @param A vértice A do triângulo
     * @param B vértice B do triângulo
     * @param C vértice C do triângulo
     */
    public Triangle(Point A, Point B, Point C){
        this.A = A;
        this.B = B;
        this.C = C;
    }

    /**
     * verifica se um ponto está dentro do triângulo
     * @param P ponto P
     * @return true, se P está no triângulo, ou false, caso contrário
     */
    public boolean isInside(Point P) {
        //TODO
        boolean veredict = false;
        return veredict;
    }
}
