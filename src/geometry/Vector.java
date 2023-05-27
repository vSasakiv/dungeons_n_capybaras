package geometry;

public class Vector {
    private float x;  // coordenada x
    private float y;  // coordenada y
    private float radius;  // coordenada radial
    private float theta;  // coordenada angular, em radianos

    /**
     * construtor vazio do vetor, que cria o vetor nulo
     */
    public Vector() {
        this.x = 0;
        this.y = 0;
        this.radius = 0;
        this.theta = 0;
    }

    /**
     * construtor padrão do vetor, com especificação de coordenadas
     * @param x coordenada/componente x do vetor
     * @param y coordenada/componente y do vetor
     */
    public Vector(float x, float y) {
        this.x = x;
        this.y = y;
        computePolarCoordinates();
    }

    /**
     * método to string
     */
    public String toString() {
        return "(x,y) = (" + this.x + ", " + this.y + ")" + 
        "\n" + "(radius,theta) = (" + this.radius + ", " + this.theta + ")\n";
    }

    /**
     * redefine as duas coordenadas cartesianas (x,y)
     * @param x nova coordenada/componente x
     * @param y nova coordenada/componente y
     */
    public void setCartesianCoordinates(float x, float y) {
        this.x = x;
        this.y = y;
        computePolarCoordinates();
    }

    /**
     * redefine as coordenadas polares (radius,theta)
     * @param radius novo valor de raio
     * @param theta novo valor de ângulo
     */
    public void setPolarCoordinates(float radius, float theta) {
        this.radius = radius;
        this.theta = theta;
        computeCartesianCoordinates();
    }

    /**
     * componente x do vetor
     * @return valor de x
     */
    public float getX() {
        return this.x;
    }

    /**
     * componente y do vetor
     * @return valor de y
     */
    public float getY() {
        return this.y;
    }

    /**
     * componente radial do vetor
     * @return valor de radius
     */
    public float getRadius() {
        return this.radius;
    }

    /**
     * componente angular do vetor
     * @return valor de theta
     */
    public float getTheta() {
        return this.theta;
    }

    /**
     * redefine componente x do vetor
     * @param newX novo valor para x
     */
    public void setX(float newX) {
        this.x = newX;
        computePolarCoordinates();
    }

    /**
     * redefine componente y do vetor
     * @param newY novo valor para y
     */
    public void setY(float newY) {
        this.y = newY;
        computePolarCoordinates();
    }

    /**
     * redefine raio do vetor
     * @param newRadius novo valor para radius
     */
    public void setRadius(float newRadius) {
        this.radius = newRadius;
        computeCartesianCoordinates();
    }

    /**
     * redefine ângulo do vetor
     * @param newTheta novo valor para theta
     */
    public void setTheta(float newTheta) {
        this.theta = newTheta;
        computeCartesianCoordinates();
    }

    /**
     * computa as coordeanas cartesianas a partir das polares
     */
    private void computeCartesianCoordinates() {
        this.x = (float) ((float) this.radius*Math.cos(theta));
        this.y = (float) ((float) this.radius*Math.sin(theta));
    }

    /**
     * computa as coordenadas polares a partir das cartesianas
     */
    private void computePolarCoordinates() {
        this.radius = (float) Math.sqrt((x*x) + (y*y));
        
        if(this.x > 0) {
            this.theta = (float) Math.atan(this.y/this.x);
        } else if(this.x < 0) {
            this.theta = (float) ((2*Math.PI) - Math.atan(this.y/this.x));
        } else {
            this.theta = 0;
        }
    }

    /**
     * normaliza o vetor, deixando seu módulo unitário
     */
    public void normalize() {
        setRadius(1);
    }

    /**
     * multiplica o vetor por um escalar
     * @param factor fator de escala
     */
    public void scale(float factor) {
        this.x *= factor;
        this.y *= factor;
        computePolarCoordinates();
    }

    /**
     * rotaciona o vetor ao redor da origem (0,0)
     * @param angle ângulo de rotação, no sentido anti-horário
     */
    public void rotate(float angle) {
        this.x = (float) (this.x*Math.cos(angle) - this.y*Math.sin(angle));
        this.y = (float) (this.x*Math.sin(angle) + this.y*Math.cos(angle));
        this.computePolarCoordinates();
    }

    /**
     * rotaciona o vetor em torno de um ponto P
     * @param origin vetor P - O, em que P é a origem (0,0)
     * @param angle ângulo de giro, positivo no sentido anti-horário
     */
    public void rotate(Vector origin, float angle) {
        Vector dif = Vector.difference(this, origin);
        Vector newVec = Vector.sum(origin, Vector.rotation(dif, angle));
        this.setCartesianCoordinates(newVec.x, newVec.y);
    }

    /**
     * soma dois vetores a e b
     * @param a primeiro vetor da soma
     * @param b segundo vetor da soma
     * @return soma dos dois
     */
    public static Vector sum(Vector a, Vector b) {
        return new Vector(a.x + b.x, a.y + b.y);
    }

    /**
     * subtrai o segundo vetor (b) do primeiro (a)
     * @param a vetor do qual será feita a subtração
     * @param b vetor que subtrai
     * @return diferença a - b
     */
    public static Vector difference(Vector a, Vector b) {
        return new Vector(a.x - b.x, a.y - b.y);
    }

    /**
     * multiplica o vetor por um escalar
     * @param v vetor original
     * @param factor fator de escala
     * @return vetor após transformação (factor*v)
     */
    public static Vector scale(Vector v, float factor) {
        return new Vector(v.x * factor, v.y * factor);
    }

    /**
     * calcula produto escalar entre dois vetores a e b
     * @param a primeiro vetor do produto escalar
     * @param b segundo vetor do produto escalar
     * @return resultado do porduto escalar <a,b>
     */
    public static float dotProduct(Vector a, Vector b) {
        return (a.x * b.x) + (a.y * b.y);
    }

    /**
     * calcula o menor ângulo entre dois vetores
     * @param a primeiro vetor
     * @param b segundo vetor
     * @return ângulo entre os vetores, em radianos
     */
    public static float angleBetween(Vector a, Vector b) {
        return (float) Math.acos(dotProduct(a, b)/(a.getRadius()*b.getRadius()));
    }

    /**
     * rotaciona o vetor v ao redor da origem (0,0)
     * @param v o vetor a ser rotacionado
     * @param angle ângulo de giro, positivo para sentido anti-horário
     * @return vetor rotacionado
     */
    public static Vector rotation(Vector v, float angle) {
        float newX = v.x = (float) (v.x*Math.cos(angle) - v.y*Math.sin(angle));
        float newY = v.y = (float) (v.x*Math.sin(angle) + v.y*Math.cos(angle));
        return new Vector(newX, newY);
    }
}