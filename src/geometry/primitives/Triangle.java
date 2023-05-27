package geometry.primitives;

import geometry.*;

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

        Vector AB = new geometry.Vector(A, B);
        Vector AC = new geometry.Vector(A, C);
        Vector AP = new geometry.Vector(A, P);

        
        if(Vector.angleBetween(AC, AB) < Vector.angleBetween(AP, AB) + Vector.angleBetween(AP, AC)){
            // verifica se P pertence à região angular definida por BÂC
            veredict = false;
        } else {
            // verifica se o tamanho de AP não extrapola o triângulo
            Vector projAB = Vector.projection(AP, AB);
            Vector projAC = Vector.projection(AP, AC);

            // AP = alpha*AB + beta*AC (decomposição)
            float alpha, beta;

            alpha = projAB.getModulus()/AB.getModulus();
            beta = projAC.getModulus()/AC.getModulus();

            if(alpha + beta > 1)
                veredict = false;
            else
                veredict = true;
        }
        return veredict;
    }
}
