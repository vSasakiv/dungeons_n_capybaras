package geometry.primitives;

import java.util.ArrayList;

public class Poligon {
    private ArrayList<Point> vertices; // lista de vértices do polígono

    /**
     * Construtor do Polígono, com lista de vértices bem definida
     * @param vertices
     */
    public Poligon(ArrayList<Point> vertices) {
        this.vertices = vertices;
    }

    /**
     * Verifica se um ponto P é interno à região poligonal convexa
     * @param P ponto P
     * @return true, se P está dentro da região ou na borda, e falso caso contrário
     */
    public boolean isInside(Point P) {
        boolean veredict = false;

        // forma n-2 triângulos para verificar se o ponto P é interno a algum deles
        for(int i = 2; i < vertices.size(); i++) {
            Triangle T = new Triangle(vertices.get(0), vertices.get(1), vertices.get(i));

            if(T.isInside(P)){
                veredict = true;
                break;
            }
        }

        return  veredict;
    }
}